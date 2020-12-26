package azmalent.potionsnowballs.common.init;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.entity.SpectreSnowballEntity;
import azmalent.potionsnowballs.common.entity.TippedSnowballEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, PotionSnowballs.MODID);

    public static final RegistryObject<EntityType<TippedSnowballEntity>> TIPPED_SNOWBALL = ENTITIES.register("tipped_snowball",
        () -> EntityType.Builder.<TippedSnowballEntity>create(TippedSnowballEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F).trackingRange(4)
            .build(new ResourceLocation(PotionSnowballs.MODID, "tipped_snowball").toString())
    );

    public static final RegistryObject<EntityType<SpectreSnowballEntity>> SPECTRE_SNOWBALL = ENTITIES.register("spectre_snowball",
        () -> EntityType.Builder.<SpectreSnowballEntity>create(SpectreSnowballEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F).trackingRange(4)
            .build(new ResourceLocation(PotionSnowballs.MODID, "spectre_snowball").toString())
    );
}
