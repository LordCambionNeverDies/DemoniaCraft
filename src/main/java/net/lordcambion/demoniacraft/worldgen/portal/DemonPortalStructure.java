package net.lordcambion.demoniacraft.worldgen.portal;

import net.lordcambion.demoniacraft.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class DemonPortalStructure {

    public static boolean tryCreatePortal(Level level, BlockPos pos, Direction facing) {
        // Determina la direzione corretta in base al facing
        BlockPos startPos = findValidPortalPosition(level, pos, facing);
        if (startPos == null) return false;

        // Controlla se possiamo generare un portale in questa posizione
        if (canCreatePortal(level, startPos, facing)) {
            createPortal(level, startPos, facing);
            return true;
        }
        return false;
    }

    private static BlockPos findValidPortalPosition(Level level, BlockPos pos, Direction facing) {
        // Cerca una posizione valida per il portale partendo dal blocco cliccato
        // Prova diverse posizioni in altezza
        for (int y = -1; y <= 1; y++) {
            BlockPos checkPos = pos.above(y);
            if (canCreatePortal(level, checkPos, facing)) {
                return checkPos;
            }
        }

        // Prova anche spostando lateralmente
        Direction[] perpendiculars = getPerpendicularDirections(facing);
        for (Direction perpDir : perpendiculars) {
            BlockPos offsetPos = pos.relative(perpDir);
            for (int y = -1; y <= 1; y++) {
                BlockPos checkPos = offsetPos.above(y);
                if (canCreatePortal(level, checkPos, facing)) {
                    return checkPos;
                }
            }
        }
        return null;
    }

    private static boolean canCreatePortal(Level level, BlockPos bottomLeft, Direction facing) {
        Block frameBlock = ModBlocks.ARKADIUM_BLOCK.get();
        int width = 2;  // Larghezza portale (interno)
        int height = 3; // Altezza portale (interno)

        // Base (y = -1)
        for (int x = 0; x < width; x++) {
            BlockPos checkPos = getRelativePos(bottomLeft, x, -1, facing);
            if (!level.getBlockState(checkPos).is(frameBlock)) {
                return false;
            }
        }

        // Top (y = height)
        for (int x = 0; x < width; x++) {
            BlockPos checkPos = getRelativePos(bottomLeft, x, height, facing);
            if (!level.getBlockState(checkPos).is(frameBlock)) {
                return false;
            }
        }

        // Lati sinistro e destro
        for (int y = 0; y < height; y++) {
            // Lato sinistro (x = -1)
            BlockPos leftPos = getRelativePos(bottomLeft, -1, y, facing);
            if (!level.getBlockState(leftPos).is(frameBlock)) {
                return false;
            }

            // Lato destro (x = width)
            BlockPos rightPos = getRelativePos(bottomLeft, width, y, facing);
            if (!level.getBlockState(rightPos).is(frameBlock)) {
                return false;
            }
        }

        // Controlla l'interno (deve essere aria)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                BlockPos checkPos = getRelativePos(bottomLeft, x, y, facing);
                BlockState state = level.getBlockState(checkPos);
                if (!state.isAir() && !state.canBeReplaced()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void createPortal(Level level, BlockPos bottomLeft, Direction facing) {
        Block portalBlock = ModBlocks.DEMONVM_PORTAL_BLOCK.get();
        int width = 2;
        int height = 3;

        // Determina l'asse corretto: il portale deve essere PERPENDICOLARE alla direzione di facing
        Direction.Axis axis = facing.getAxis();

        // Crea i blocchi del portale
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                BlockPos portalPos = getRelativePos(bottomLeft, x, y, facing);

                level.setBlock(portalPos, portalBlock.defaultBlockState()
                        .setValue(BlockStateProperties.HORIZONTAL_AXIS, axis), 3);
            }
        }
    }

    private static BlockPos getRelativePos(BlockPos start, int x, int y, Direction facing) {
        return switch (facing) {
            case NORTH -> start.offset(x, y, 0);   // Portale su asse Z, espansione su X
            case SOUTH -> start.offset(-x, y, 0);  // Portale su asse Z, espansione su X (invertito)
            case WEST -> start.offset(0, y, x);    // Portale su asse X, espansione su Z
            case EAST -> start.offset(0, y, -x);   // Portale su asse X, espansione su Z (invertito)
            default -> start.offset(x, y, 0);
        };
    }

    private static Direction[] getPerpendicularDirections(Direction facing) {
        return switch (facing) {
            case NORTH, SOUTH -> new Direction[]{Direction.WEST, Direction.EAST};
            case WEST, EAST -> new Direction[]{Direction.NORTH, Direction.SOUTH};
            default -> new Direction[]{Direction.NORTH, Direction.SOUTH};
        };
    }

    // Metodo per rimuovere il portale quando la cornice viene rotta
    public static void checkAndBreakPortal(Level level, BlockPos brokenPos) {
        Block frameBlock = ModBlocks.ARKADIUM_BLOCK.get();
        Block portalBlock = ModBlocks.DEMONVM_PORTAL_BLOCK.get();

        // Se non è Arkadium, ignora
        if (!level.getBlockState(brokenPos).is(frameBlock)) {
            return;
        }

        // Cerca blocchi portale adiacenti (4 direzioni orizzontali + sopra e sotto)
        Direction[] allDirections = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};

        for (Direction dir : allDirections) {
            BlockPos adjacentPos = brokenPos.relative(dir);
            BlockState adjState = level.getBlockState(adjacentPos);
            if (adjState.is(portalBlock)) {
                // Trova tutti i blocchi portale collegati (stesso asse)
                Set<BlockPos> portalBlocks = new HashSet<>();
                Direction.Axis axis = adjState.getValue(BlockStateProperties.HORIZONTAL_AXIS);

                findConnectedPortalBlocks(level, adjacentPos, portalBlock, axis, portalBlocks);

                if (portalBlocks.isEmpty()) {
                    return;
                }

                // Trova i bounds del portale
                PortalBounds bounds = findPortalBounds(portalBlocks, axis);

                // Verifica se il blocco rotto faceva parte della cornice
                if (isPartOfFrame(brokenPos, bounds, axis)) {
                    // Verifica integrità della cornice: se mancanti, rimuovi il portale
                    if (!isFrameIntact(level, bounds, axis, frameBlock)) {
                        for (BlockPos portalPos : portalBlocks) {
                            level.setBlock(portalPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
                // abbiamo già processato il portale rilevato
                return;
            }
        }
    }

    private static boolean isPartOfFrame(BlockPos pos, PortalBounds bounds, Direction.Axis portalAxis) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (portalAxis == Direction.Axis.Z) {
            // Portale su asse X (si espande lungo X), i blocchi portale hanno coordinate z costante = bounds.minZ == bounds.maxZ
            int portalZ = bounds.minZ;
            // Base (sotto)
            if (y == bounds.minY - 1 && x >= bounds.minX && x <= bounds.maxX && z == portalZ) {
                return true;
            }
            // Top (sopra)
            if (y == bounds.maxY + 1 && x >= bounds.minX && x <= bounds.maxX && z == portalZ) {
                return true;
            }
            // Lati (sinistro/destro)
            if (y >= bounds.minY && y <= bounds.maxY && (x == bounds.minX - 1 || x == bounds.maxX + 1) && z == portalZ) {
                return true;
            }
        } else {
            // Portale su asse Z (si espande lungo Z), i blocchi portale hanno coordinate x costante = bounds.minX == bounds.maxX
            int portalX = bounds.minX;
            // Base (sotto)
            if (y == bounds.minY - 1 && z >= bounds.minZ && z <= bounds.maxZ && x == portalX) {
                return true;
            }
            // Top (sopra)
            if (y == bounds.maxY + 1 && z >= bounds.minZ && z <= bounds.maxZ && x == portalX) {
                return true;
            }
            // Lati (sinistro/destro)
            if (y >= bounds.minY && y <= bounds.maxY && (z == bounds.minZ - 1 || z == bounds.maxZ + 1) && x == portalX) {
                return true;
            }
        }

        return false;
    }

    private static class PortalBounds {
        int minX, maxX, minY, maxY, minZ, maxZ;

        PortalBounds(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
            this.minZ = minZ;
            this.maxZ = maxZ;
        }
    }

    private static PortalBounds findPortalBounds(Set<BlockPos> portalBlocks, Direction.Axis axis) {
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE, maxZ = Integer.MIN_VALUE;

        for (BlockPos pos : portalBlocks) {
            minX = Math.min(minX, pos.getX());
            maxX = Math.max(maxX, pos.getX());
            minY = Math.min(minY, pos.getY());
            maxY = Math.max(maxY, pos.getY());
            minZ = Math.min(minZ, pos.getZ());
            maxZ = Math.max(maxZ, pos.getZ());
        }

        return new PortalBounds(minX, maxX, minY, maxY, minZ, maxZ);
    }

    private static boolean isFrameIntact(Level level, PortalBounds bounds, Direction.Axis portalAxis, Block frameBlock) {
        // Il portale è orientato sull'asse X o Z
        if (portalAxis == Direction.Axis.X) {
            // Portale orientato su X (si espande su X)
            // Verifica base (sotto)
            for (int x = bounds.minX; x <= bounds.maxX; x++) {
                BlockPos basePos = new BlockPos(x, bounds.minY - 1, bounds.minZ);
                if (!level.getBlockState(basePos).is(frameBlock)) {
                    return false;
                }
            }

            // Verifica top (sopra)
            for (int x = bounds.minX; x <= bounds.maxX; x++) {
                BlockPos topPos = new BlockPos(x, bounds.maxY + 1, bounds.minZ);
                if (!level.getBlockState(topPos).is(frameBlock)) {
                    return false;
                }
            }

            // Verifica lati sinistro e destro
            for (int y = bounds.minY; y <= bounds.maxY; y++) {
                BlockPos leftPos = new BlockPos(bounds.minX - 1, y, bounds.minZ);
                BlockPos rightPos = new BlockPos(bounds.maxX + 1, y, bounds.minZ);

                if (!level.getBlockState(leftPos).is(frameBlock) ||
                        !level.getBlockState(rightPos).is(frameBlock)) {
                    return false;
                }
            }
        } else {
            // Portale orientato su Z (si espande su Z)
            // Verifica base (sotto)
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                BlockPos basePos = new BlockPos(bounds.minX, bounds.minY - 1, z);
                if (!level.getBlockState(basePos).is(frameBlock)) {
                    return false;
                }
            }

            // Verifica top (sopra)
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                BlockPos topPos = new BlockPos(bounds.minX, bounds.maxY + 1, z);
                if (!level.getBlockState(topPos).is(frameBlock)) {
                    return false;
                }
            }

            // Verifica lati sinistro e destro
            for (int y = bounds.minY; y <= bounds.maxY; y++) {
                BlockPos leftPos = new BlockPos(bounds.minX, y, bounds.minZ - 1);
                BlockPos rightPos = new BlockPos(bounds.minX, y, bounds.maxZ + 1);

                if (!level.getBlockState(leftPos).is(frameBlock) ||
                        !level.getBlockState(rightPos).is(frameBlock)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Flood-fill iterativo che raccoglie tutti i blocchi portale connessi a `start`,
     * ma solo se hanno lo stesso asse orizzontale. Ignora diagonali (usa 6 direzioni).
     */
    private static void findConnectedPortalBlocks(Level level, BlockPos start, Block portalBlock, Direction.Axis requiredAxis, Set<BlockPos> found) {
        final int MAX_PORTAL_BLOCKS = 512;

        Queue<BlockPos> queue = new LinkedList<>();
        BlockState startState = level.getBlockState(start);

        if (!startState.is(portalBlock)) return;
        Direction.Axis startAxis = startState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
        if (startAxis != requiredAxis) return;

        found.add(start);
        queue.add(start);

        Direction[] dirs = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};

        while (!queue.isEmpty() && found.size() < MAX_PORTAL_BLOCKS) {
            BlockPos current = queue.poll();

            for (Direction d : dirs) {
                BlockPos next = current.relative(d);
                if (found.contains(next)) continue;

                BlockState nextState = level.getBlockState(next);

                // Evita di attraversare blocchi solidi o cornici
                if (nextState.is(ModBlocks.ARKADIUM_BLOCK.get())) continue;

                // Prendi solo blocchi portale dello stesso asse
                if (nextState.is(portalBlock)) {
                    Direction.Axis nextAxis = nextState.getValue(BlockStateProperties.HORIZONTAL_AXIS);
                    if (nextAxis == requiredAxis) {
                        // Se non ci sono blocchi di frame adiacenti lungo l'asse perimetrale, aggiungi
                        found.add(next);
                        queue.add(next);
                    }
                }
            }
        }
    }

}