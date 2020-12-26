package azmalent.potionsnowballs.common.event;

import azmalent.potionsnowballs.ModCompat;
import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.client.particle.TippedSnowballParticle;
import azmalent.potionsnowballs.common.entity.SpectreSnowballEntity;
import azmalent.potionsnowballs.common.entity.TippedSnowballEntity;
import azmalent.potionsnowballs.common.init.ModEntities;
import azmalent.potionsnowballs.common.init.ModItems;
import azmalent.potionsnowballs.common.init.ModParticles;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.data.DataGenerator;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LifecycleEvents {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(LifecycleEvents::setup);
        bus.addListener(LifecycleEvents::clientSetup);
        bus.addListener(LifecycleEvents::gatherData);
        bus.addListener(LifecycleEvents::registerColorHandler);
        bus.addListener(LifecycleEvents::registerParticleFactory);
    }

    private static void setup(FMLCommonSetupEvent event) {
        DispenserBlock.registerDispenseBehavior(ModItems.TIPPED_SNOWBALL.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new TippedSnowballEntity(position.getX(), position.getY(), position.getZ(), worldIn), (snowball) -> {
                    snowball.setItem(stackIn);
                });
            }
        });

        DispenserBlock.registerDispenseBehavior(ModItems.SPECTRE_SNOWBALL.get(), new ProjectileDispenseBehavior() {
            protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new SpectreSnowballEntity(position.getX(), position.getY(), position.getZ(), worldIn), (snowball) -> {
                    snowball.setItem(stackIn);
                });
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    private static void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.TIPPED_SNOWBALL.get(), erm ->
                new SpriteRenderer<>(erm, Minecraft.getInstance().getItemRenderer())
        );

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SPECTRE_SNOWBALL.get(), erm ->
                new SpriteRenderer<>(erm, Minecraft.getInstance().getItemRenderer())
        );
    }

    private static void gatherData(GatherDataEvent event) {
        if (event.includeServer()) {
            DataGenerator generator = event.getGenerator();
            ModCompat.INSPIRATIONS.registerCauldronRecipes(generator);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerColorHandler(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex > 0 ? -1 : PotionUtils.getColor(stack), ModItems.TIPPED_SNOWBALL.get());
    }

    @OnlyIn(Dist.CLIENT)
    private static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        ParticleManager particles = Minecraft.getInstance().particles;
        particles.registerFactory(ModParticles.TIPPED_SNOWBALL.get(), TippedSnowballParticle.Factory::new);
    }
}
