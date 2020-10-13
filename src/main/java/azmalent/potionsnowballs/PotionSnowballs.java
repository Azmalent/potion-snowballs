package azmalent.potionsnowballs;

import azmalent.potionsnowballs.common.compat.ModCompatUtil;
import azmalent.potionsnowballs.common.compat.inspirations.DummyInspirationsCompat;
import azmalent.potionsnowballs.common.compat.inspirations.InspirationsCompatProxy;
import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import azmalent.potionsnowballs.common.item.ItemTippedSnowball;
import azmalent.potionsnowballs.common.recipes.TippedSnowballRecipe;
import azmalent.potionsnowballs.proxy.IProxy;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
        modid = PotionSnowballs.MODID,
        name = PotionSnowballs.NAME,
        version = PotionSnowballs.VERSION,
        dependencies = "after:inspirations"
)
@Mod.EventBusSubscriber
public class PotionSnowballs
{
    public static final String MODID = "potionsnowballs";
    public static final String NAME = "Potion Snowballs";
    public static final String VERSION = "1.0";

    private static final String CLIENT_PROXY = "azmalent.potionsnowballs.proxy.ClientProxy";
    private static final String SERVER_PROXY = "azmalent.potionsnowballs.proxy.ServerProxy";

    @SidedProxy(clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    private static final String INSPIRATIONS_COMPAT_IMPL = "azmalent.potionsnowballs.common.compat.inspirations.ActiveInspirationsCompat";
    public static InspirationsCompatProxy inspirationsCompat;

    @GameRegistry.ObjectHolder(ItemTippedSnowball.REGISTRY_NAME)
    public static ItemTippedSnowball TIPPED_SNOWBALL;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);

        inspirationsCompat = ModCompatUtil.initProxy(
                "inspirations",
                InspirationsCompatProxy.class,
                INSPIRATIONS_COMPAT_IMPL,
                DummyInspirationsCompat.class

        );
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        if (inspirationsCompat.cauldronPotionsEnabled()) {
            inspirationsCompat.initCauldronRecipes();
        }
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemTippedSnowball());
    }

    @SubscribeEvent
    public static void onRegisterEntities(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(
                EntityEntryBuilder.create()
                    .entity(EntityTippedSnowball.class)
                    .id(new ResourceLocation(MODID), 0)
                    .name("tipped_snowball")
                    .tracker(64, 1, true)
                .build()
        );
    }

    @SubscribeEvent
    public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().register(new TippedSnowballRecipe());
    }
}
