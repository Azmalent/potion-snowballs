package azmalent.potionsnowballs;

import azmalent.potionsnowballs.common.entity.SpectreSnowballEntity;
import azmalent.potionsnowballs.common.entity.TippedSnowballEntity;
import azmalent.potionsnowballs.common.item.SpectreSnowballItem;
import azmalent.potionsnowballs.common.item.TippedSnowballItem;
import azmalent.potionsnowballs.common.recipe.TippedSnowballRecipe;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PotionSnowballs.MODID)
public class PotionSnowballs {
    public static final String MODID = "potionsnowballs";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> TIPPED_ITEM = ITEMS.register("tipped_snowball", TippedSnowballItem::new);
    public static final RegistryObject<Item> SPECTRE_ITEM = ITEMS.register("spectre_snowball", SpectreSnowballItem::new);

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final RegistryObject<EntityType<TippedSnowballEntity>> TIPPED_ENTITY = ENTITIES.register("tipped_snowball",
        () -> EntityType.Builder.<TippedSnowballEntity>create(TippedSnowballEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F).trackingRange(4)
            .build(new ResourceLocation(MODID, "tipped_snowball").toString()));
    public static final RegistryObject<EntityType<SpectreSnowballEntity>> SPECTRE_ENTITY = ENTITIES.register("spectre_snowball",
        () -> EntityType.Builder.<SpectreSnowballEntity>create(SpectreSnowballEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F).trackingRange(4)
            .build(new ResourceLocation(MODID, "spectre_snowball").toString()));

    private static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);
    public static final RegistryObject<SpecialRecipeSerializer<?>> TIPPED_RECIPE =  RECIPES.register("tipped_snowball",
        () -> new SpecialRecipeSerializer<>(TippedSnowballRecipe::new)
    );

    public PotionSnowballs() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(PotionSnowballs::setup);
        bus.addListener(PotionSnowballs::clientSetup);
        bus.addListener(PotionSnowballs::gatherData);
        bus.addListener(PotionSnowballs::registerColorHandler);

        ITEMS.register(bus);
        ENTITIES.register(bus);
        RECIPES.register(bus);
    }

    private static void setup(FMLCommonSetupEvent event) {
        DispenserBlock.registerDispenseBehavior(TIPPED_ITEM.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return Util.make(new TippedSnowballEntity(position.getX(), position.getY(), position.getZ(), worldIn), (snowball) -> {
                snowball.setItem(stackIn);
            });
            }
        });

        DispenserBlock.registerDispenseBehavior(SPECTRE_ITEM.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
            return Util.make(new SpectreSnowballEntity(position.getX(), position.getY(), position.getZ(), worldIn), (snowball) -> {
                snowball.setItem(stackIn);
            });
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(TIPPED_ENTITY.get(), erm ->
            new SpriteRenderer<>(erm, Minecraft.getInstance().getItemRenderer())
        );

        RenderingRegistry.registerEntityRenderingHandler(SPECTRE_ENTITY.get(), erm ->
            new SpriteRenderer<>(erm, Minecraft.getInstance().getItemRenderer())
        );
    }

    public static void gatherData(GatherDataEvent event) {
        if (event.includeServer()) {
            DataGenerator generator = event.getGenerator();
            ModCompat.INSPIRATIONS.registerCauldronRecipes(generator);
        }
    }

    private static void registerColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack), TIPPED_ITEM.get());
    }
}
