package azmalent.potionsnowballs.client;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemStack;

public class TippedSnowballRenderer extends RenderSnowball<EntityTippedSnowball> {
    public TippedSnowballRenderer(RenderManager manager) {
        super(manager, PotionSnowballs.TIPPED_SNOWBALL, Minecraft.getMinecraft().getRenderItem());
    }

    @Override
    public ItemStack getStackToRender(EntityTippedSnowball snowball) {
        return snowball.getStackToRender();
    }
}
