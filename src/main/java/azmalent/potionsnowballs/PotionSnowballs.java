package azmalent.potionsnowballs;

import azmalent.potionsnowballs.common.event.LifecycleEvents;
import azmalent.potionsnowballs.common.init.ModEntities;
import azmalent.potionsnowballs.common.init.ModItems;
import azmalent.potionsnowballs.common.init.ModParticles;
import azmalent.potionsnowballs.common.init.ModRecipes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PotionSnowballs.MODID)
public class PotionSnowballs {
    public static final String MODID = "potionsnowballs";

    public PotionSnowballs() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModRecipes.RECIPES.register(bus);

        LifecycleEvents.registerListeners();
    }
}
