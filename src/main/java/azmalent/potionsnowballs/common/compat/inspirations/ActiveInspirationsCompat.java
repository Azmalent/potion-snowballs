package azmalent.potionsnowballs.common.compat.inspirations;

import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.InspirationsRegistry;
import net.minecraftforge.fml.common.Loader;

public class ActiveInspirationsCompat implements InspirationsCompatProxy {
    @Override
    public boolean cauldronPotionsEnabled() {
        return Config.enableExtendedCauldron && Config.enableCauldronPotions && Config.cauldronTipArrows;
    }

    @Override
    public void initCauldronRecipes() {
        InspirationsRegistry.addCauldronRecipe(new TippedSnowballCauldronRecipe());
    }
}
