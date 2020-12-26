package azmalent.potionsnowballs.common.entity;

import azmalent.potionsnowballs.ModCompat;
import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.init.ModEntities;
import azmalent.potionsnowballs.common.init.ModItems;
import azmalent.potionsnowballs.common.init.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class TippedSnowballEntity extends ProjectileItemEntity implements IRendersAsItem {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(TippedSnowballEntity.class, DataSerializers.VARINT);
    private boolean hasCustomColor;

    public TippedSnowballEntity(EntityType<TippedSnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public TippedSnowballEntity(double x, double y, double z, World worldIn) {
        super(ModEntities.TIPPED_SNOWBALL.get(), x, y, z, worldIn);
    }

    public TippedSnowballEntity(LivingEntity shooter, World worldIn) {
        super(ModEntities.TIPPED_SNOWBALL.get(), shooter, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(COLOR, -1);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();

        if (world.isRemote && rand.nextFloat() < 0.33) {
            spawnPotionParticles(rand.nextInt(3));
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.TIPPED_SNOWBALL.get();
    }

    @Override
    public void setItem(ItemStack stack) {
        super.setItem(stack);

        int customColor = getCustomColor(stack);
        if (customColor == -1) {
            this.refreshColor();
        }
        else {
            this.setFixedColor(customColor);
        }
    }

    private List<EffectInstance> getAllPotionEffects() {
        ItemStack snowball = getItem();
        Potion potion = PotionUtils.getPotionFromItem(snowball);
        List<EffectInstance> customPotionEffects = PotionUtils.getFullEffectsFromItem(snowball);

        return PotionUtils.mergeEffects(potion, customPotionEffects);
    }

    public int getColor() {
        return this.dataManager.get(COLOR);
    }

    public static int getCustomColor(ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        return tag != null && tag.contains("CustomPotionColor", 99) ? tag.getInt("CustomPotionColor") : -1;
    }

    private void setFixedColor(int p_191507_1_) {
        this.hasCustomColor = true;
        this.dataManager.set(COLOR, p_191507_1_);
    }

    private void refreshColor() {
        this.hasCustomColor = false;

        List<EffectInstance> effects = getAllPotionEffects();
        int color = effects.isEmpty() ? -1 : PotionUtils.getPotionColorFromEffectList(effects);
        this.dataManager.set(COLOR, color);
    }

    @OnlyIn(Dist.CLIENT)
    private void spawnPotionParticles(int particleCount) {
        if (particleCount <= 0) return;

        int color = getColor();
        if (color != -1) {
            double r = (double) (color >> 16 & 255) / 255.0D;
            double g = (double) (color >> 8 & 255) / 255.0D;
            double b = (double) (color & 255) / 255.0D;

            for (int i = 0; i < particleCount; i++) {
                double x = this.getPosXRandom(0.5);
                double y = this.getPosYRandom();
                double z = this.getPosZRandom(0.5);

                world.addParticle(ParticleTypes.ENTITY_EFFECT, x, y, z, r, g, b);
            }
        }
    }

    private void applyScaledEffect(EffectInstance effect, float scale, LivingEntity livingEntity) {
        Effect potion = effect.getPotion();
        int amplifier = effect.getAmplifier();
        boolean undead = livingEntity.isEntityUndead();

        if (potion == Effects.INSTANT_DAMAGE && !undead || potion == Effects.INSTANT_HEALTH && undead) {
            float damage = (6 << amplifier) * scale;
            livingEntity.attackEntityFrom(DamageSource.MAGIC, damage);
            ModCompat.CONSECRATION.smiteWithHealingPotion(livingEntity);
        } else if (potion == Effects.INSTANT_HEALTH && !undead || potion == Effects.INSTANT_DAMAGE && undead) {
            float healing = (4 << effect.getAmplifier()) * scale;
            livingEntity.heal(healing);
        } else {
            int duration = Math.max((int) (effect.getDuration() * scale), 1);
            livingEntity.addPotionEffect(
                new EffectInstance(potion, duration, effect.getAmplifier(), effect.isAmbient(), effect.doesShowParticles())
            );
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            for (EffectInstance effect : getAllPotionEffects()) {
                applyScaledEffect(effect, 1 / 8f, living);
            }
        }

        int damage = entity instanceof BlazeEntity ? 3 : 0;
        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.func_234616_v_()), damage);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            int color = getColor();
            if (color != -1) {
                double r = (double) (color >> 16 & 255) / 255.0D;
                double g = (double) (color >> 8 & 255) / 255.0D;
                double b = (double) (color & 255) / 255.0D;

                for (int i = 0; i < 8; i++) {
                    this.world.addParticle(ModParticles.TIPPED_SNOWBALL.get(), this.getPosX(), this.getPosY(), this.getPosZ(), r, g, b);
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    this.world.addParticle(ParticleTypes.ITEM_SNOWBALL, this.getPosX(), this.getPosY(), this.getPosZ(), 0, 0, 0);
                }
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        if (this.hasCustomColor) {
            compound.putInt("Color", this.getColor());
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("Color", 99)) {
            this.setFixedColor(compound.getInt("Color"));
        } else {
            this.refreshColor();
        }
    }
}
