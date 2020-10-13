package azmalent.potionsnowballs.client;

import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class TippedSnowballRendererFactory implements IRenderFactory<EntityTippedSnowball> {
    @Override
    public Render<EntityTippedSnowball> createRenderFor(RenderManager manager) {
        return new TippedSnowballRenderer(manager);
    }
}
