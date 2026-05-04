package net.lordcambion.demoniacraft.entity.custom;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.lordcambion.demoniacraft.entity.HedgehogVariants;
import net.lordcambion.demoniacraft.entity.ModEntities;
import net.lordcambion.demoniacraft.entity.ModEntityDataSerializers;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.Util;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.IntFunction;

public class HedgehogEntity extends Animal {
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<HedgehogState> HEDGEHOG_STATE =
            SynchedEntityData.defineId(HedgehogEntity.class, ModEntityDataSerializers.HEDGEHOG_STATE);

    private long inStateTicks = 0L;
    public final AnimationState rollOutAnimationState = new AnimationState();
    public final AnimationState rollUpAnimationState = new AnimationState();
    public final AnimationState peekAnimationState = new AnimationState();
    private boolean peekReceivedClient = false;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public HedgehogEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(ModItems.STRAWBERRY.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttribute() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 14D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.TEMPT_RANGE, 12D);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ModItems.STRAWBERRY.get());
    }

    /// Metodo per infliggere danno thorns/generico
    // Metodo per infliggere danno thorns/generico all'attaccante
    private void hurtAttacker(Entity attacker) {


        if (attacker instanceof LivingEntity livingAttacker && this.level() instanceof ServerLevel serverLevel) {
            // Calcola il danno base
            float damage = 2.0F;
            boolean isBareHands = isAttackingWithBareHands(livingAttacker);
            if (!isBareHands) {
                return;
            }
            // Danno bonus se è spaventato e rotolato
            if (this.isScared() && this.shouldHideInShell()) {
                damage = 3.0F;
            }

            // Ottiene la difficoltà del livello
            Difficulty difficulty = serverLevel.getDifficulty();
            DamageSource damageSource;

            // Se la difficoltà è PEACEFUL, usiamo damageSources().magic() o generic()
            // per aggirare l'annullamento del danno delle entità in Peaceful.
            if (difficulty == Difficulty.PEACEFUL) {
                // Usiamo il danno magico, che solitamente ignora la maggior parte delle armature/restrizioni base
                damageSource = this.damageSources().magic();
            } else {
                // Nelle altre difficoltà, usiamo il danno Thorns standard
                damageSource = this.damageSources().thorns(this);
            }

            // Infliggi il danno all'attaccante
            boolean wasHurt = livingAttacker.hurtServer(serverLevel, damageSource, damage);

            // Suono quando si infligge danno
            if (wasHurt) {
                this.playSound(SoundEvents.ARMADILLO_HURT, 0.5F, 1.0F);
            }
        }
    }
    @Override
    public boolean hurtServer(ServerLevel pLevel, DamageSource pDamageSource, float pAmount) {
        // *** MODIFICA TAG QUI ***
        // Se la fonte del danno è già etichettata come danno riflesso (o proiettile), non riflettere di nuovo.
        if (pDamageSource.is(DamageTypeTags.AVOIDS_GUARDIAN_THORNS) || pDamageSource.is(DamageTypeTags.IS_PROJECTILE)) {
            return super.hurtServer(pLevel, pDamageSource, pAmount);
        }

        // 1. Applica danno thorns all'attaccante OGNI VOLTA che viene colpito
        if (pDamageSource.getDirectEntity() != null) {
            Entity attacker = pDamageSource.getDirectEntity();
            this.hurtAttacker(attacker); // Questo usa damageSources().thorns(this)
        }

        // 2. Prosegui con il danno che il riccio subisce
        return super.hurtServer(pLevel, pDamageSource, pAmount);
    }


    // Metodo per controllare se il giocatore ha scarpe di metallo adeguate
    private boolean hasMetalBoots(Player player) {
        ItemStack boots = player.getInventory().getEquipment().get(EquipmentSlot.FEET);

        if (boots.isEmpty()) {
            return false;
        }

        // Controlla se gli stivali hanno tag di metallo
        return boots.is(Tags.Items.INGOTS_IRON) ||
                boots.is(Tags.Items.INGOTS_GOLD) ||
                boots.is(Tags.Items.INGOTS_NETHERITE) ||
                boots.is(Tags.Items.GEMS_DIAMOND) ||
                isCustomMetalArmor(boots);
    }
    private boolean isCustomMetalArmor(ItemStack armor) {
        // Controlla i nomi degli item per materiali metallici delle mod
        String itemName = armor.getItem().toString().toLowerCase();

        return itemName.contains("iron") ||
                itemName.contains("diamond") ||
                itemName.contains("netherite") ||
                itemName.contains("chainmail") ||
                itemName.contains("gold") ||
                itemName.contains("steel") ||
                itemName.contains("bronze") ||
                itemName.contains("copper") ||
                itemName.contains("silver") ||
                itemName.contains("cobalt") ||
                itemName.contains("manyullyn") ||
                itemName.contains("metal")  ||
                itemName.contains("arkadium");
    }


    private boolean isAttackingWithBareHands(LivingEntity attacker) {
        // Only return true if the attacker's main hand is completely empty
        return attacker.getMainHandItem().isEmpty();
    }
    private boolean hasNoBoots(LivingEntity attacker) {
        // Only return true if the attacker's main hand is completely empty
        return attacker.getItemBySlot(EquipmentSlot.FEET).isEmpty();
    }
//    @Override
//    protected void actuallyHurt(ServerLevel pLevel, DamageSource pDamageSource, float pAmount) {
//        // Se il riccio è spaventato e viene colpito, infliggi danno thorns all'attaccante
//        if (this.isScared() && this.shouldHideInShell() && pDamageSource.getDirectEntity() != null) {
//            Entity attacker = pDamageSource.getDirectEntity();
//            this.hurtAttacker(attacker);
//        }
//
//        super.actuallyHurt(pLevel, pDamageSource, pAmount);
//    }

    @Override
    public void playerTouch(Player pPlayer) {
        super.playerTouch(pPlayer);

//        // Se il riccio è spaventato e il giocatore lo tocca senza scarpe adeguate
//        if (this.isScared() && this.shouldHideInShell() && !this.hasMetalBoots(pPlayer) && this.level() instanceof ServerLevel serverLevel) {
//
//            DamageSource damageSource;
//            // Usa danno magico/generico in Peaceful per aggirare le restrizioni
//            if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
//                damageSource = this.damageSources().magic();
//            } else {
//                damageSource = this.damageSources().thorns(this);
//            }
//
//            // Infliggi danno al giocatore usando hurtServer
//            boolean wasHurt = pPlayer.hurtServer(serverLevel, damageSource, 1.0F);
//            // ... (resto della logica: knockback e suono)
//            if (wasHurt) {
//                // Piccolo knockback - riduci la forza
//                Vec3 knockback = pPlayer.position().subtract(this.position()).normalize().scale(0.1);
//                pPlayer.setDeltaMovement(knockback.x, 0.1, knockback.z);
//
//                // Suono
//                this.playSound(SoundEvents.ARMADILLO_HURT, 0.5F, 1.0F);
//            }
//        }
    }
    // Metodo per gestire quando un'entità calpesta il riccio
    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, net.minecraft.world.level.block.state.BlockState pState, net.minecraft.core.BlockPos pPos) {
        // Rimuovi la logica di calpestio da qui, è stata spostata in handleSteppingDamage
        super.checkFallDamage(pY, pOnGround, pState, pPos);
    }

    @Override
    public void push(Entity pEntity) {
        // Se il riccio è spaventato, non respingere le entità che si avvicinano
        // Questo permette di avvicinarsi abbastanza per calpestarlo
        if (!this.isScared() || !this.shouldHideInShell()) {
            super.push(pEntity);
        }
    }
    @Override
    public boolean isPushable() {
        // Se il riccio è spaventato, non è spingibile
        return !this.isScared() || !this.shouldHideInShell();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 64 && this.level().isClientSide) {
            this.peekReceivedClient = true;
            this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMADILLO_PEEK, this.getSoundSource(), 1.0F, 1.0F, false);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    // Metodi per il comportamento di paura
    public boolean isScaredBy(LivingEntity pEntity) {
        float detectionRange = this.isBaby() ? 10.0F : 7.0F;
        float heightRange = this.isBaby() ? 3.0F : 2.0F;

        if (!this.getBoundingBox().inflate(detectionRange, heightRange, detectionRange)
                .intersects(pEntity.getBoundingBox())) {
            return false;
        } else if (this.getLastHurtByMob() == pEntity) {
            return true;
        } else if (pEntity instanceof Player player) {
            return player.isSpectator() ? false : player.isSprinting() || player.isPassenger();
        } else {
            return pEntity instanceof Monster || pEntity.getType() == EntityType.WOLF;
        }
    }

    public void rollUp() {
        if (!this.isScared()) {
            this.stopInPlace();
            this.resetLove();
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.playSound(SoundEvents.ARMADILLO_ROLL, 1.0F, this.isBaby() ? 1.2F : 1.0F);
            this.switchToState(HedgehogState.ROLLING);
            this.setDeltaMovement(0, 0, 0);
        }
    }

    public boolean canStayRolledUp() {
        return !this.isPanicking() && !this.isInLiquid() && !this.isLeashed() && !this.isPassenger() && !this.isVehicle();
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isScared();
    }

    public void rollOut() {
        if (this.getState() == HedgehogState.SCARED && this.inStateTicks >= HedgehogState.SCARED.animationDuration()) {
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.playSound(SoundEvents.ARMADILLO_UNROLL_FINISH, 1.0F, 1.0F);
            this.switchToState(HedgehogState.UNROLLING);
        }
    }

    @Override
    public void aiStep() {
        boolean wasScared = this.isScared();
        super.aiStep();

        if (this.isScared() && this.getState() != HedgehogState.UNROLLING) {
            if (this.horizontalCollision && this.onGround()) {
                // Collision handling
            }
        }

        if (this.isScared() && !wasScared) {
            this.stopInPlace();
        }
    }

    @Override
    protected void customServerAiStep(ServerLevel pLevel) {
        super.customServerAiStep(pLevel);

        if (this.isScared() && this.getState() != HedgehogState.UNROLLING) {
            this.getNavigation().stop();

            for (WrappedGoal wrappedGoal : this.goalSelector.getAvailableGoals()) {
                if (wrappedGoal.isRunning()) {
                    wrappedGoal.stop();
                }
            }

            this.goalSelector.disableControlFlag(Goal.Flag.MOVE);
            this.goalSelector.disableControlFlag(Goal.Flag.JUMP);
            this.goalSelector.disableControlFlag(Goal.Flag.LOOK);
        } else {
            this.goalSelector.enableControlFlag(Goal.Flag.MOVE);
            this.goalSelector.enableControlFlag(Goal.Flag.JUMP);
            this.goalSelector.enableControlFlag(Goal.Flag.LOOK);
        }

        if (!this.isScared() && this.tickCount % 80 == 0) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(7.0, 2.0, 7.0));

            for (LivingEntity entity : nearbyEntities) {
                if (this.isScaredBy(entity)) {
                    this.rollUp();
                    break;
                }
            }
        }

        if (this.shouldSwitchToScaredState()) {
            this.switchToState(HedgehogState.SCARED);
        }

        if (this.getState() == HedgehogState.SCARED && this.inStateTicks > 100 && !this.isThreatened()) {
            this.switchToState(HedgehogState.UNROLLING);
        }

        if (this.getState() == HedgehogState.UNROLLING && this.inStateTicks > HedgehogState.UNROLLING.animationDuration()) {
            this.switchToState(HedgehogState.IDLE);
        }
    }

    private boolean isThreatened() {
        List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(7.0, 2.0, 7.0));

        for (LivingEntity entity : nearbyEntities) {
            if (this.isScaredBy(entity)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void clampHeadRotationToBody() {
        this.setYHeadRot(this.getYRot());
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isScared() ? 0 : 32;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        HedgehogEntity baby = ModEntities.HEDGEHOG.get().create(pLevel, EntitySpawnReason.BREEDING);
        if (baby != null) {
            baby.setAge(-24000);

            if (pOtherParent instanceof HedgehogEntity otherHedgehog) {
                HedgehogVariants variant = this.random.nextBoolean() ?
                        this.getVariant() : otherHedgehog.getVariant();
                baby.setVariant(variant);
            } else {
                baby.setVariant(this.getVariant());
            }
        }
        return baby;
    }

    private void setupAnimationStates() {
        switch (this.getState()) {
            case IDLE:
                this.rollOutAnimationState.stop();
                this.rollUpAnimationState.stop();
                this.peekAnimationState.stop();

                if (this.idleAnimationTimeout <= 0) {
                    this.idleAnimationTimeout = 70;
                    this.idleAnimationState.start(this.tickCount);
                } else {
                    --this.idleAnimationTimeout;
                }

                if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D) {
                    this.walkAnimationState.startIfStopped(this.tickCount);
                    this.idleAnimationState.stop();
                } else {
                    this.walkAnimationState.stop();
                    this.idleAnimationState.startIfStopped(this.tickCount);
                }
                break;

            case ROLLING:
                this.rollOutAnimationState.stop();
                this.rollUpAnimationState.startIfStopped(this.tickCount);
                this.peekAnimationState.stop();
                this.walkAnimationState.stop();
                this.idleAnimationState.stop();
                break;

            case SCARED:
                this.rollOutAnimationState.stop();
                this.rollUpAnimationState.stop();
                this.walkAnimationState.stop();
                this.idleAnimationState.stop();

                if (this.peekReceivedClient) {
                    this.peekAnimationState.stop();
                    this.peekReceivedClient = false;
                }

                if (this.inStateTicks == 0L) {
                    this.peekAnimationState.start(this.tickCount);
                    this.peekAnimationState.fastForward(HedgehogState.SCARED.animationDuration(), 1.0F);
                } else {
                    this.peekAnimationState.startIfStopped(this.tickCount);
                }
                break;

            case UNROLLING:
                this.rollOutAnimationState.startIfStopped(this.tickCount);
                this.rollUpAnimationState.stop();
                this.peekAnimationState.stop();
                this.walkAnimationState.stop();
                this.idleAnimationState.stop();
                break;
        }
    }
    private void handleSteppingDamage(@Nullable ServerLevel serverLevel) {
        if (serverLevel == null) return;

        DamageSource damageSource;
        // Prepara la sorgente di danno basata sulla difficoltà
        if (serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
            damageSource = this.damageSources().magic();
        } else {
            damageSource = this.damageSources().thorns(this);
        }

        // Rileva le entità che si intersecano con la bounding box leggermente espansa verso l'alto
        List<Entity> entitiesAbove = this.level().getEntities(this,
                this.getBoundingBox().inflate(0.2, 0.1, 0.2));

        for (Entity entity : entitiesAbove) {
            // ... (condizioni per calpestio)
            if (entity instanceof LivingEntity livingEntity &&
                    entity != this &&
                    entity.getBoundingBox().minY > this.getBoundingBox().maxY - 0.2) {

                if (livingEntity instanceof Player player) {
                    if (!this.hasMetalBoots(player)) {
                        // Danno da calpestio al giocatore
                        boolean wasHurt = player.hurtServer(serverLevel, damageSource, 2.0F);
                        Vec3 knockback = player.position().subtract(this.position()).normalize().scale(0.05); // Ridotto a 0.05
                        player.setDeltaMovement(knockback.x, 0.05, knockback.z); // Ridotto l'impulso verticale
                        player.hasImpulse = true; // Assicura che il movimento venga applicato

                        this.playSound(SoundEvents.ARMADILLO_HURT, 0.5F, 1.0F);
                    }
                } else {
                    // Danno da calpestio per altre entità
                    boolean wasHurt = livingEntity.hurtServer(serverLevel, damageSource, 2.0F);
                    if (wasHurt) {
                        this.playSound(SoundEvents.ARMADILLO_HURT, 0.5F, 1.0F);
                    }
                }
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        } else {
            // --- ADD SERVER-SIDE STEPPING DAMAGE CHECK HERE ---
            if (this.isScared() && this.shouldHideInShell()) {
                handleSteppingDamage(this.level().getServer().getLevel(this.level().dimension()));
            }
        }

        if (this.isScared()) {
            this.clampHeadRotationToBody();
        }

        this.inStateTicks++;
    }

    /* VARIANT */
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(VARIANT, 0);
        pBuilder.define(HEDGEHOG_STATE, HedgehogState.IDLE);
    }

    public int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public boolean isScared() {
        return this.entityData.get(HEDGEHOG_STATE) != HedgehogState.IDLE;
    }

    public boolean shouldHideInShell() {
        return this.getState().shouldHideInShell(this.inStateTicks);
    }

    public boolean shouldSwitchToScaredState() {
        return this.getState() == HedgehogState.ROLLING && this.inStateTicks > HedgehogState.ROLLING.animationDuration();
    }

    public HedgehogVariants getVariant() {
        return HedgehogVariants.byId(this.getTypeVariant());
    }

    private void setVariant(HedgehogVariants variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    public HedgehogState getState() {
        return this.entityData.get(HEDGEHOG_STATE);
    }

    public void switchToState(HedgehogState pState) {
        this.entityData.set(HEDGEHOG_STATE, pState);
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pPose) {
        return super.getDefaultDimensions(pPose).scale(this.isBaby() ? 0.5f : 1.0f);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isScared() && this.getState() != HedgehogState.UNROLLING) {
            Vec3 currentMovement = this.getDeltaMovement();

            this.setXxa(0.0F);
            this.setYya(0.0F);
            this.setZza(0.0F);

            if (!this.onGround() && !this.isNoGravity()) {
                this.setDeltaMovement(currentMovement.x * 0.91D, currentMovement.y - 0.08D, currentMovement.z * 0.91D);
            } else {
                this.setDeltaMovement(currentMovement.x * 0.91D, currentMovement.y * 0.98D, currentMovement.z * 0.91D);
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            return;
        }

        super.travel(pTravelVector);
    }

    public void stopInPlace() {
        this.getNavigation().stop();
        this.setDeltaMovement(0, 0, 0);
        this.setXxa(0.0F);
        this.setZza(0.0F);
        this.setSpeed(0.0F);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput pOutput) {
        super.addAdditionalSaveData(pOutput);
        pOutput.putInt("Variant", this.getTypeVariant());
        pOutput.putString("State", this.getState().getSerializedName());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput pInput) {
        super.readAdditionalSaveData(pInput);

        int variantId = pInput.getIntOr("Variant", 0);
        this.entityData.set(VARIANT, variantId);

        String stateName = pInput.getStringOr("State", "idle");
        HedgehogState state = HedgehogState.byName(stateName);
        if (state != null) {
            this.switchToState(state);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        EntitySpawnReason pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        HedgehogVariants variant = Util.getRandom(HedgehogVariants.values(), this.random);
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel pLevel, Animal pAnimal, @Nullable AgeableMob pBaby) {
        super.finalizeSpawnChildFromBreeding(pLevel, pAnimal, pBaby);

        if (pBaby instanceof HedgehogEntity babyHedgehog) {
            if (pAnimal instanceof HedgehogEntity otherParent) {
                HedgehogVariants variant = this.random.nextBoolean() ?
                        this.getVariant() : otherParent.getVariant();
                babyHedgehog.setVariant(variant);
            }
        }
    }

    /* SOUNDS */
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BAT_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BAT_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.OCELOT_DEATH;
    }

    @Override
    public boolean isBaby() {
        return super.isBaby();
    }

    @Override
    public void setBaby(boolean baby) {
        // this.entityData.set(DATA_BABY_ID, baby);
    }

    @Override
    public void setAge(int pAge) {
        super.setAge(pAge);
        this.refreshDimensions();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (HEDGEHOG_STATE.equals(pKey)) {
            this.inStateTicks = 0L;
        }
        super.onSyncedDataUpdated(pKey);
    }

    public enum HedgehogState implements StringRepresentable {
        IDLE("idle", false, 0, 0) {
            @Override
            public boolean shouldHideInShell(long pInStateTicks) {
                return false;
            }
        },
        ROLLING("rolling", true, 10, 1) {
            @Override
            public boolean shouldHideInShell(long pInStateTicks) {
                return pInStateTicks > 5L;
            }
        },
        SCARED("scared", true, 50, 2) {
            @Override
            public boolean shouldHideInShell(long pInStateTicks) {
                return true;
            }
        },
        UNROLLING("unrolling", true, 30, 3) {
            @Override
            public boolean shouldHideInShell(long pInStateTicks) {
                return pInStateTicks < 26L;
            }
        };

        static final Codec<HedgehogState> CODEC = StringRepresentable.fromEnum(HedgehogState::values);
        private static final IntFunction<HedgehogState> BY_ID = ByIdMap.continuous(
                HedgehogState::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO
        );
        public static final StreamCodec<ByteBuf, HedgehogState> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, HedgehogState::id);

        private final String name;
        private final boolean isThreatened;
        private final int animationDuration;
        private final int id;

        HedgehogState(final String pName, final boolean pIsThreatened, final int pAnimationDuration, final int pId) {
            this.name = pName;
            this.isThreatened = pIsThreatened;
            this.animationDuration = pAnimationDuration;
            this.id = pId;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return this.id;
        }

        public abstract boolean shouldHideInShell(long pInStateTicks);

        public boolean isThreatened() {
            return this.isThreatened;
        }

        public int animationDuration() {
            return this.animationDuration;
        }

        @Nullable
        public static HedgehogState byName(String name) {
            for (HedgehogState state : values()) {
                if (state.name.equals(name)) {
                    return state;
                }
            }
            return null;
        }
    }
}