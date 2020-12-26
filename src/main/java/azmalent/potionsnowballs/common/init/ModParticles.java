package azmalent.potionsnowballs.common.init;

import azmalent.potionsnowballs.PotionSnowballs;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, PotionSnowballs.MODID);

    public static final RegistryObject<BasicParticleType> TIPPED_SNOWBALL = PARTICLES.register("tipped_snowball",
        () -> new BasicParticleType(false)
    );
}
