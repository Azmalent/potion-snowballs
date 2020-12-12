package azmalent.potionsnowballs.common.entity;

import azmalent.potionsnowballs.PotionSnowballs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public final class EntityTippedSnowball extends EntityThrowable
{
    private static final DataParameter<ItemStack> SNOWBALL = EntityDataManager.createKey(EntityTippedSnowball.class, DataSerializers.ITEM_STACK);
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityTippedSnowball.class, DataSerializers.VARINT);

    public EntityTippedSnowball(World worldIn) {
        super(worldIn);
    }

    public EntityTippedSnowball(World worldIn, double x, double y, double z, ItemStack snowball) {
        super(worldIn, x, y, z);
        setSnowball(snowball);
    }

    public EntityTippedSnowball(World worldIn, EntityLivingBase shooter, ItemStack snowball) {
        super(worldIn, shooter);
        setSnowball(snowball);
    }

    protected void entityInit() {
        dataManager.register(SNOWBALL, ItemStack.EMPTY);
        dataManager.register(COLOR, Integer.valueOf(-1));
    }

    private void setSnowball(ItemStack stack) {
        dataManager.set(SNOWBALL, stack);
        dataManager.setDirty(SNOWBALL);
        updateColor();
    }

    private void updateColor() {
        ItemStack snowball = dataManager.get(SNOWBALL);
        PotionType potion = PotionUtils.getPotionFromItem(snowball);
        List<PotionEffect> customEffects = PotionUtils.getFullEffectsFromItem(snowball);

        int newColor = PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(potion, customEffects));
        dataManager.set(COLOR, newColor);
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setTag("Snowball", dataManager.get(SNOWBALL).writeToNBT(new NBTTagCompound()));
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        ItemStack snowball = new ItemStack(compound.getCompoundTag("Snowball"));
        if (!snowball.isEmpty()) {
            setSnowball(snowball);
        }
        else {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            spawnPotionParticles(world.rand.nextInt(3));
        }
    }

    private void spawnPotionParticles(int particleCount) {
        if (particleCount <= 0) return;

        int color = dataManager.get(COLOR);

        double r = (double)(color >> 16 & 255) / 255.0D;
        double g = (double)(color >> 8 & 255) / 255.0D;
        double b = (double)(color >> 0 & 255) / 255.0D;

        for (int j = 0; j < particleCount; j++) {
            double x = this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width;
            double y = this.posY + this.rand.nextDouble() * (double)this.height;
            double z = this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width;

            world.spawnParticle(EnumParticleTypes.SPELL_MOB, x, y, z, r, g, b);
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (world.isRemote) return;

        if (result.entityHit != null) {
            if (result.entityHit instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) result.entityHit;
                ItemStack snowball = dataManager.get(SNOWBALL);

                PotionType potion = PotionUtils.getPotionFromItem(snowball);
                if (potion != null) {
                    for (PotionEffect effect : potion.getEffects()) {
                        living.addPotionEffect(
                                new PotionEffect(effect.getPotion(),
                                        Math.max(effect.getDuration() / 8, 1),
                                        effect.getAmplifier(),
                                        effect.getIsAmbient(),
                                        effect.doesShowParticles()
                                )
                        );
                    }
                }

                for (PotionEffect effect : PotionUtils.getFullEffectsFromItem(snowball)) {
                    living.addPotionEffect(effect);
                }
            }

            float damage = result.entityHit instanceof EntityBlaze ? 3 : 0;
            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
        }

        this.world.setEntityState(this, (byte)3);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 0) {
            spawnPotionParticles(world.rand.nextInt(4));
        }
        else if (id == 3) {
            spawnPotionParticles(8);
        }
    }

    public ItemStack getStackToRender() {
        return dataManager.get(SNOWBALL);
    }
}
