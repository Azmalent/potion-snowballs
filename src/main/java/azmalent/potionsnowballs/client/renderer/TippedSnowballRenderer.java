package azmalent.potionsnowballs.client.renderer;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class TippedSnowballRenderer extends RenderSnowball<EntityTippedSnowball> {
    public static class Factory implements IRenderFactory<EntityTippedSnowball> {
        @Override
        public Render<EntityTippedSnowball> createRenderFor(RenderManager manager) {
            return new TippedSnowballRenderer(manager);
        }
    }

    public TippedSnowballRenderer(RenderManager manager) {
        super(manager, PotionSnowballs.TIPPED_SNOWBALL, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public ItemStack getStackToRender(EntityTippedSnowball snowball) {
        return snowball.getStackToRender();
    }
}
