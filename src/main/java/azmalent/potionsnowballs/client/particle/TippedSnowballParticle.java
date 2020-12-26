package azmalent.potionsnowballs.client.particle;

import azmalent.potionsnowballs.common.init.ModItems;
import net.minecraft.client.particle.BreakingParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class TippedSnowballParticle extends BreakingParticle {
    protected TippedSnowballParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, new ItemStack(ModItems.TIPPED_SNOWBALL.get()));
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Particle particle = new TippedSnowballParticle(worldIn, x, y, z);
            particle.setColor((float)xSpeed, (float)ySpeed, (float)zSpeed);
            return particle;
        }
    }
}
