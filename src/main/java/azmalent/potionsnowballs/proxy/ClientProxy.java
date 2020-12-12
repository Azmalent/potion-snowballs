package azmalent.potionsnowballs.proxy;

import azmalent.potionsnowballs.client.renderer.TippedSnowballRenderer;
import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

import static azmalent.potionsnowballs.PotionSnowballs.TIPPED_SNOWBALL;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                EntityTippedSnowball.class,
                new TippedSnowballRenderer.Factory()
        );
    }

    @Override
    public void init(FMLInitializationEvent event) {
        registerColorHandler();
    }

    @SubscribeEvent
    public static void onRegisterModels(ModelRegistryEvent event) {
        ModelResourceLocation mrl = new ModelResourceLocation(TIPPED_SNOWBALL.REGISTRY_NAME);
        ModelLoader.setCustomModelResourceLocation(TIPPED_SNOWBALL, 0, mrl);
    }

    private void registerColorHandler() {
        ItemColors colors = Minecraft.getMinecraft().getItemColors();
        colors.registerItemColorHandler(
            (stack, tintIndex) -> {
                List<PotionEffect> effects = PotionUtils.getEffectsFromStack(stack);
                return PotionUtils.getPotionColorFromEffectList(effects);
            },
            TIPPED_SNOWBALL
        );
    }
}
