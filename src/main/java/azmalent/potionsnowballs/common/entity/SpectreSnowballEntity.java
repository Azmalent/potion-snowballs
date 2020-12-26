package azmalent.potionsnowballs.common.entity;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.init.ModEntities;
import azmalent.potionsnowballs.common.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
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
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.List;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class SpectreSnowballEntity extends ProjectileItemEntity implements IRendersAsItem {
    private static final int DURATION = 200;

    public SpectreSnowballEntity(EntityType<SpectreSnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpectreSnowballEntity(double x, double y, double z, World worldIn) {
        super(ModEntities.SPECTRE_SNOWBALL.get(), x, y, z, worldIn);
    }

    public SpectreSnowballEntity(LivingEntity shooter, World worldIn) {
        super(ModEntities.SPECTRE_SNOWBALL.get(), shooter, worldIn);
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
            world.addParticle(ParticleTypes.INSTANT_EFFECT, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SPECTRE_SNOWBALL.get();
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;

            EffectInstance glow = new EffectInstance(Effects.GLOWING, DURATION, 0);
            living.addPotionEffect(glow);
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
            IParticleData data = new ItemParticleData(ParticleTypes.ITEM, getItem());

            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(data, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
