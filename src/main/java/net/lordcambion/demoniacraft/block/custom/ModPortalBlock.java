package net.lordcambion.demoniacraft.block.custom;

import net.lordcambion.demoniacraft.block.ModBlocks;
import net.lordcambion.demoniacraft.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import javax.annotation.Nullable;
import java.util.Optional;

public class ModPortalBlock extends Block implements Portal {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

    // Hitbox ridotte per il portale (4 pixel di spessore invece di 16)
    private static final VoxelShape X_AXIS_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    private static final VoxelShape Z_AXIS_SHAPE = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);

    public ModPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(AXIS) == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty(); // Nessuna collisione fisica, si può attraversare
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        // Particelle attorno al portale
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    SoundEvents.PORTAL_AMBIENT,
                    SoundSource.BLOCKS,
                    0.5F,
                    random.nextFloat() * 0.4F + 0.8F,
                    false
            );
        }

        // Genera particelle
        for (int i = 0; i < 4; i++) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();

            double velocityX = (random.nextDouble() - 0.5) * 0.5;
            double velocityY = (random.nextDouble() - 0.5) * 0.5;
            double velocityZ = (random.nextDouble() - 0.5) * 0.5;

            int multiplier = random.nextInt(2) * 2 - 1;

            if (state.getValue(AXIS) == Direction.Axis.X) {
                x = pos.getX() + 0.5 + 0.25 * multiplier;
                velocityX = random.nextFloat() * 2.0F * multiplier;
                velocityZ = (random.nextFloat() - 0.5) * 0.5;
            } else {
                z = pos.getZ() + 0.5 + 0.25 * multiplier;
                velocityZ = random.nextFloat() * 2.0F * multiplier;
                velocityX = (random.nextFloat() - 0.5) * 0.5;
            }

            level.addParticle(ParticleTypes.PORTAL, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        if (!level.isClientSide() && entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }

        // Applica l'effetto confusione visivo come il portale del Nether
        if (level.isClientSide() && entity instanceof net.minecraft.client.player.LocalPlayer) {
            net.minecraft.client.Minecraft.getInstance().gameRenderer.displayItemActivation(net.minecraft.world.item.ItemStack.EMPTY);
        }

        // Applica l'effetto nausea sul server per la distorsione visiva
        if (!level.isClientSide() && entity instanceof Player player) {
            player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    MobEffects.NAUSEA,
                    100, // Durata in tick (5 secondi)
                    0, // Amplificatore
                    false, // Ambient
                    false, // Visible particles
                    false  // Show icon

            ));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Nullable
    @Override
    public TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos pos) {
        MinecraftServer minecraftServer = currentLevel.getServer();
        ResourceKey<Level> currentDim = currentLevel.dimension();
        ResourceKey<Level> targetDim = currentDim == ModDimensions.DEMONVM_LEVEL_KEY
                ? Level.OVERWORLD
                : ModDimensions.DEMONVM_LEVEL_KEY;

        ServerLevel destinationLevel = minecraftServer.getLevel(targetDim);

        if (destinationLevel == null) {
            return null;
        }

        // Calcola la posizione di destinazione scalata
        Vec3 scaledPos = calculateScaledPosition(entity, currentDim, targetDim);
        BlockPos searchPos = new BlockPos((int)scaledPos.x, (int)scaledPos.y, (int)scaledPos.z);

        // Cerca un portale esistente o creane uno nuovo
        Optional<BlockPos> portalPos = findOrCreatePortal(destinationLevel, searchPos, entity.getDirection());

        if (portalPos.isEmpty()) {
            return null;
        }

        // Crea la transizione di teletrasporto verso il portale trovato/creato
        Vec3 destinationPos = new Vec3(portalPos.get().getX() + 0.5, portalPos.get().getY(), portalPos.get().getZ() + 0.5);

        return new TeleportTransition(
                destinationLevel,
                destinationPos,
                Vec3.ZERO,
                entity.getYRot(),
                entity.getXRot(),
                TeleportTransition.PLAY_PORTAL_SOUND.then(TeleportTransition.PLACE_PORTAL_TICKET)
        );
    }

    private Vec3 calculateScaledPosition(Entity entity, ResourceKey<Level> fromDim, ResourceKey<Level> toDim) {
        double scale = getDimensionScale(fromDim, toDim);
        double x = entity.getX() * scale;
        double z = entity.getZ() * scale;
        double y = Math.max(entity.getY(), toDim == Level.OVERWORLD ? 60 : 30);

        return new Vec3(x, y, z);
    }

    private double getDimensionScale(ResourceKey<Level> fromDim, ResourceKey<Level> toDim) {
        if (fromDim == ModDimensions.DEMONVM_LEVEL_KEY && toDim == Level.OVERWORLD) {
            return 8.0;
        } else if (fromDim == Level.OVERWORLD && toDim == ModDimensions.DEMONVM_LEVEL_KEY) {
            return 0.125;
        }
        return 1.0;
    }

    private Optional<BlockPos> findOrCreatePortal(ServerLevel world, BlockPos searchCenter, Direction facing) {
        // PRIMA cerca un portale esistente in un'area 128x128x128
        Optional<BlockPos> existingPortal = findExistingPortal(world, searchCenter);
        if (existingPortal.isPresent()) {
            return existingPortal;
        }

        // SE non trova portali esistenti, creane uno nuovo con struttura completa
        return createNewPortalStructure(world, searchCenter, facing);
    }

    private Optional<BlockPos> findExistingPortal(ServerLevel world, BlockPos center) {
        int searchRadius = 64;
        int minY = world.getMinY() + 10;
        int maxY = world.getMaxY() - 10;

        for (int x = -searchRadius; x <= searchRadius; x++) {
            for (int z = -searchRadius; z <= searchRadius; z++) {
                for (int y = minY; y <= maxY; y++) {
                    BlockPos checkPos = center.offset(x, y - center.getY(), z);
                    if (world.getBlockState(checkPos).getBlock() instanceof ModPortalBlock) {
                        return Optional.of(checkPos);
                    }
                }
            }
        }
        return Optional.empty();
    }

    private Optional<BlockPos> createNewPortalStructure(ServerLevel world, BlockPos desiredPos, Direction facing) {
        // Trova una posizione sicura per il portale
        BlockPos safePos = findSafeSpawnPosition(world, desiredPos);
        if (safePos == null) {
            return Optional.empty();
        }

        // Determina la direzione del portale basata sul facing dell'entità
        Direction portalFacing = getPortalFacing(facing);

        // Crea la piattaforma 4x5 sotto il portale
        createPlatform(world, safePos.below(), portalFacing);

        // Crea la struttura completa del portale (cornice + portale)
        createCompletePortalStructure(world, safePos, portalFacing);

        return Optional.of(safePos);
    }

    private Direction getPortalFacing(Direction entityFacing) {
        // Usa la direzione dell'entità, preferendo Nord/Sud o Est/Ovest
        return switch (entityFacing) {
            case NORTH, SOUTH -> Direction.NORTH;
            case EAST, WEST -> Direction.EAST;
            default -> Direction.NORTH;
        };
    }

    private void createCompletePortalStructure(ServerLevel world, BlockPos bottomLeft, Direction facing) {
        Block frameBlock = ModBlocks.ARKADIUM_BLOCK.get();
        Block portalBlock = ModBlocks.DEMONVM_PORTAL_BLOCK.get();
        int width = 2;
        int height = 3;

        // Determina l'asse del portale
        Direction.Axis axis = facing.getAxis();

        // Crea la cornice completa (inclusi gli angoli)
        for (int x = -1; x <= width; x++) {
            for (int y = -1; y <= height; y++) {
                BlockPos framePos = getRelativePos(bottomLeft, x, y, facing);

                // Crea i bordi (base, top, lati e angoli)
                if (x == -1 || x == width || y == -1 || y == height) {
                    world.setBlock(framePos, frameBlock.defaultBlockState(), 3);
                }
            }
        }

        // Crea i blocchi del portale all'interno
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                BlockPos portalPos = getRelativePos(bottomLeft, x, y, facing);
                world.setBlock(portalPos, portalBlock.defaultBlockState()
                        .setValue(BlockStateProperties.HORIZONTAL_AXIS, axis), 3);
            }
        }
    }

    private BlockPos getRelativePos(BlockPos start, int x, int y, Direction facing) {
        return switch (facing) {
            case NORTH -> start.offset(x, y, 0);
            case SOUTH -> start.offset(-x, y, 0);
            case WEST -> start.offset(0, y, x);
            case EAST -> start.offset(0, y, -x);
            default -> start.offset(x, y, 0);
        };
    }

    private BlockPos findSafeSpawnPosition(ServerLevel world, BlockPos startPos) {
        // Cerca verso l'alto una posizione con spazio sufficiente (5 blocchi altezza)
        for (int y = startPos.getY(); y < world.getMaxY() - 5; y++) {
            BlockPos checkPos = new BlockPos(startPos.getX(), y, startPos.getZ());
            if (isSafeForPortalStructure(world, checkPos)) {
                return checkPos;
            }
        }

        // Se non trova verso l'alto, prova verso il basso
        for (int y = startPos.getY(); y > world.getMinY() + 5; y--) {
            BlockPos checkPos = new BlockPos(startPos.getX(), y, startPos.getZ());
            if (isSafeForPortalStructure(world, checkPos)) {
                return checkPos;
            }
        }

        return null;
    }

    private boolean isSafeForPortalStructure(ServerLevel world, BlockPos pos) {
        // Verifica che ci sia spazio per l'intera struttura (4x5 blocchi + base)
        for (int x = -1; x <= 2; x++) {
            for (int y = 0; y <= 4; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos checkPos = pos.offset(x, y, z);
                    if (y == -1) {
                        // Sotto deve esserci un blocco solido
                        if (world.getBlockState(checkPos).isAir()) {
                            return false;
                        }
                    } else {
                        // Sopra deve essere aria o sostituibile
                        BlockState state = world.getBlockState(checkPos);
                        if (!state.isAir() && !state.canBeReplaced()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private void createPlatform(ServerLevel world, BlockPos portalBase, Direction facing) {
        Block platformBlock = Blocks.OBSIDIAN;

        // Dimensioni desiderate: lunghezza 4 (along portal axis), larghezza 5 (perpendicolare)
        final int platformLength = 4;
        final int platformWidth = 5;

        // Cerchiamo i blocchi del portale attorno al portalBase per capire effettiva posizione/asse
        int scanRadius = 4;
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;

        for (int dx = -scanRadius; dx <= scanRadius; dx++) {
            for (int dz = -scanRadius; dz <= scanRadius; dz++) {
                for (int dy = -2; dy <= 4; dy++) {
                    BlockPos p = portalBase.offset(dx, dy, dz);
                    if (world.getBlockState(p).getBlock() instanceof ModPortalBlock) {
                        minX = Math.min(minX, p.getX());
                        maxX = Math.max(maxX, p.getX());
                        minZ = Math.min(minZ, p.getZ());
                        maxZ = Math.max(maxZ, p.getZ());
                        minY = Math.min(minY, p.getY());
                    }
                }
            }
        }

        // Se non troviamo blocchi portal (fallback): usa portalBase come centro
        int centerX, centerZ, platformY;
        boolean axisIsX;
        if (minX == Integer.MAX_VALUE) {
            centerX = portalBase.getX();
            centerZ = portalBase.getZ();
            platformY = portalBase.getY() - 1;
            axisIsX = (facing == Direction.NORTH || facing == Direction.SOUTH);
        } else {
            centerX = (minX + maxX) / 2;
            centerZ = (minZ + maxZ) / 2;
            platformY = minY - 1;
            axisIsX = (maxX - minX) >= (maxZ - minZ); // se l'estensione maggiore è in X => asse X
        }

        // Calcola intervalli di offset centrati
        // Per lunghezza pari (4): offsets = -2, -1, 0, 1  -> start = -platformLength/2
        int lengthStart = -platformLength / 2; // -2
        int lengthEnd = lengthStart + platformLength - 1; // 1

        // Per larghezza dispari (5): offsets = -2, -1, 0, 1, 2 -> start = -(width/2)
        int widthStart = -(platformWidth / 2); // -2
        int widthEnd = widthStart + platformWidth - 1; // 2

        for (int lx = lengthStart; lx <= lengthEnd; lx++) {
            for (int wx = widthStart; wx <= widthEnd; wx++) {
                int x, z;
                if (axisIsX) {
                    // lunghezza lungo X, larghezza lungo Z
                    x = centerX + lx;
                    z = centerZ + wx;
                } else {
                    // lunghezza lungo Z, larghezza lungo X (ruota 90°)
                    x = centerX + wx;
                    z = centerZ + lx;
                }

                BlockPos platformPos = new BlockPos(x, platformY, z);
                BlockState current = world.getBlockState(platformPos);
                if (current.isAir() || current.canBeReplaced()) {
                    world.setBlock(platformPos, platformBlock.defaultBlockState(), 3);
                }
            }
        }
    }

    @Override
    public Portal.Transition getLocalTransition() {
        return Portal.Transition.CONFUSION;
    }

    @Override
    public int getPortalTransitionTime(ServerLevel level, Entity entity) {
        if (entity instanceof Player player) {
            return Math.max(1, level.getGameRules().getInt(
                    player.getAbilities().invulnerable
                            ? GameRules.RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY
                            : GameRules.RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY
            ));
        }
        return 80;
    }
}