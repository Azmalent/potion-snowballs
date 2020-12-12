package azmalent.potionsnowballs.common.compat.inspirations;

import azmalent.potionsnowballs.ModConfig;
import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.InspirationsRegistry;

public class InspirationsCompat implements IInspirationsCompat {
    @Override
    public boolean cauldronPotionsEnabled() {
        return Config.enableExtendedCauldron && Config.enableCauldronPotions && Config.cauldronTipArrows;
    }

    @Override
    public void initCauldronRecipes() {
        if (ModConfig.cauldronOutput > 0) {
            InspirationsRegistry.addCauldronRecipe(new TippedSnowballCauldronRecipe());
        }
    }
}
