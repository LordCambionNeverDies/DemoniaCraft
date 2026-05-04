package net.lordcambion.demoniacraft.entity.custom;

import net.lordcambion.demoniacraft.entity.ai.ScytheMeleeAttackGoal;
import net.lordcambion.demoniacraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BoneReaperEntity extends WitherSkeleton {
    // Stati di animazione
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0; // AGGIUNGI QUESTA

    public BoneReaperEntity(EntityType<? extends WitherSkeleton> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setCanPickUpLoot(true);
    }

    private void setupAnimationStates() {
        // Gestione idle animation
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        // Gestione attack animation - MODIFICATO
        if (this.swinging) {
            this.attackAnimationState.startIfStopped(this.tickCount);
            this.walkAnimationState.stop();
            this.idleAnimationState.stop();
            this.attackAnimationTimeout = 40; // Durata dell'animazione in tick
        } else {
            if (this.attackAnimationTimeout > 0) {
                --this.attackAnimationTimeout;
            }

            if (this.attackAnimationTimeout <= 0) {
                this.attackAnimationState.stop();
            }
        }

        // Gestione walk animation - solo se NON sta attaccando
        double speed = this.getDeltaMovement().horizontalDistanceSqr();

        if (this.attackAnimationTimeout <= 0) { // Non camminare/idle durante attacco
            if (speed > 1.0E-6) {
                this.walkAnimationState.startIfStopped(this.tickCount);
                if (!this.idleAnimationState.isStarted()) {
                    this.idleAnimationState.start(this.tickCount);
                }
            } else if (speed <= 1.0E-6) {
                this.walkAnimationState.stop();
                this.idleAnimationState.startIfStopped(this.tickCount);
            }
        }
    }
    public boolean isPerformingAttack() {
        return this.attackAnimationTimeout > 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

//    @Override
//    public boolean doHurtTarget(ServerLevel pLevel, Entity pEntity) {
//        boolean result = super.doHurtTarget(pLevel, pEntity);
//        if (result) {
//            // Trigger attack animation
//            this.attackAnimationState.start(this.tickCount);
//        }
//        return result;
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return WitherSkeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D)
                .add(Attributes.ARMOR, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RestrictSunGoal(this));
        this.goalSelector.addGoal(2, new FleeSunGoal(this, 1.0D));

        // Sostituisci questa riga:
        // this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));

        // Con questa:
        this.goalSelector.addGoal(3, new ScytheMeleeAttackGoal(this, 1.2D, false));

        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        // SEMPRE equipaggia la Demon Scythe
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.DEMON_SCYTHE.get()));
        this.setDropChance(EquipmentSlot.MAINHAND, 0F); // 0% di drop per l'arma

        // Armature con probabilità di spawn e drop molto più basse
        if (pRandom.nextFloat() < 0.3F) {
            this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.NETHERITE_HELMET));
            this.setDropChance(EquipmentSlot.HEAD, 0.02F); // 2% di drop
        }

        if (pRandom.nextFloat() < 0.5F) {
            this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
            this.setDropChance(EquipmentSlot.CHEST, 0.02F); // 2% di drop
        }

        if (pRandom.nextFloat() < 0.4F) {
            this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.NETHERITE_LEGGINGS));
            this.setDropChance(EquipmentSlot.LEGS, 0.02F); // 2% di drop
        }

        if (pRandom.nextFloat() < 0.3F) {
            this.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.NETHERITE_BOOTS));
            this.setDropChance(EquipmentSlot.FEET, 0.02F); // 2% di drop
        }
    }



    @Override
    protected void populateDefaultEquipmentEnchantments(ServerLevelAccessor pLevel, RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentEnchantments(pLevel, pRandom, pDifficulty);
    }

    @Override
    public boolean canPickUpLoot() {
        return true;
    }

    @Override
    protected void pickUpItem(ServerLevel pLevel, ItemEntity pEntity) {
        ItemStack itemstack = pEntity.getItem();
        EquipmentSlot slot = this.getEquipmentSlotForItem(itemstack);

        // NON raccogliere l'arma principale - la Demon Scythe è permanente
        if (slot == EquipmentSlot.MAINHAND) {
            return; // Ignora completamente le armi
        }

        // Per le armature, raccoglie solo se è un miglioramento
        if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
            ItemStack currentItem = this.getItemBySlot(slot);

            // Se non ha niente in quello slot, raccoglie
            if (currentItem.isEmpty()) {
                if (this.canHoldItem(itemstack)) {
                    this.onItemPickup(pEntity);
                    this.setItemSlot(slot, itemstack.copy());
                    this.setGuaranteedDrop(slot); // Droppa garantito alla morte
                    this.take(pEntity, itemstack.getCount());
                    pEntity.discard();
                }
                return;
            }

            // Se ha già qualcosa, raccoglie solo se è migliore
            if (this.canReplaceCurrentItem(itemstack, currentItem, slot)) {
                this.onItemPickup(pEntity);
                this.setItemSlot(slot, itemstack.copy());
                this.setGuaranteedDrop(slot);
                this.take(pEntity, itemstack.getCount());
                pEntity.discard();
            }
        }
    }

    @Override
    public boolean canHoldItem(ItemStack pStack) {
        EquipmentSlot slot = this.getEquipmentSlotForItem(pStack);

        // NON può tenere armi (protegge la Demon Scythe)
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            return false;
        }

        // Per le armature, usa la logica standard
        return super.canHoldItem(pStack);
    }

    @Override
    protected boolean canReplaceCurrentItem(ItemStack pCandidate, ItemStack pExisting, EquipmentSlot pSlot) {
        // NON sostituire MAI l'arma principale
        if (pSlot == EquipmentSlot.MAINHAND) {
            return false;
        }

        // Per le armature, usa la logica vanilla che controlla la qualità
        return super.canReplaceCurrentItem(pCandidate, pExisting, pSlot);
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        // Proteggi la Demon Scythe dalla rimozione
        if (pSlot == EquipmentSlot.MAINHAND && !pStack.is(ModItems.DEMON_SCYTHE.get())) {
            return; // Ignora tentativi di cambiare l'arma principale
        }

        super.setItemSlot(pSlot, pStack);
    }

    @Override
    public @Nullable EquipmentSlot resolveSlot(ItemStack pStack, List<EquipmentSlot> pExcludedSlots) {
        return super.resolveSlot(pStack, pExcludedSlots);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WITHER_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.WITHER_SKELETON_STEP;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel pLevel, DamageSource pDamageSource, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pLevel, pDamageSource, pRecentlyHit);

        if (this.random.nextFloat() < 0.5F) {
            this.spawnAtLocation(pLevel, Items.BONE);
        }

        if (this.random.nextFloat() < 0.2F) {
            this.spawnAtLocation(pLevel, Items.COAL, this.random.nextInt(2) + 1);
        }

        if (this.random.nextFloat() < 0.01F) {
            this.spawnAtLocation(pLevel, Items.WITHER_SKELETON_SKULL);
        }

        if (this.random.nextFloat() < 0.09F) {
            ItemStack scythe = new ItemStack(ModItems.DEMON_SCYTHE.get());

            // Imposta durability random
            int maxDurability = scythe.getMaxDamage();
            if (maxDurability > 0) {
                // Damage casuale tra 0 e maxDurability (dove 0 = nuovo, maxDurability = rotto)
                int randomDamage = this.random.nextInt(maxDurability + 1);
                scythe.setDamageValue(randomDamage);
            }

            this.spawnAtLocation(pLevel, scythe);
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    public static boolean checkBoneReaperSpawnRules(
            EntityType<? extends WitherSkeleton> pType,
            ServerLevelAccessor pLevel,
            EntitySpawnReason pSpawnType,
            BlockPos pPos,
            RandomSource pRandom) {
        return checkMonsterSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom) &&
                pLevel.getRawBrightness(pPos, 0) <= 7;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor pLevel,
            DifficultyInstance pDifficulty,
            EntitySpawnReason pSpawnType,
            @Nullable SpawnGroupData pSpawnGroupData) {

        pSpawnGroupData = super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);

        this.populateDefaultEquipmentSlots(pLevel.getRandom(), pDifficulty);
        this.populateDefaultEquipmentEnchantments(pLevel, pLevel.getRandom(), pDifficulty);

        return pSpawnGroupData;
    }

    @Override
    protected int getBaseExperienceReward(ServerLevel pLevel) {
        return 8;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        if (pEffectInstance.getEffect() == MobEffects.WITHER) {
            return false;
        }
        return super.canBeAffected(pEffectInstance);
    }
}