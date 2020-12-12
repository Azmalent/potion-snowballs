package azmalent.potionsnowballs.common.compat.inspirations;

import azmalent.potionsnowballs.ModConfig;
import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.InspirationsRegistry;

public interface IInspirationsCompat {
    boolean cauldronPotionsEnabled();
    void initCauldronRecipes();

    class Dummy implements IInspirationsCompat {
        @Override
        public boolean cauldronPotionsEnabled() {
            return false;
        }

        @Override
        public void initCauldronRecipes() {

        }
    }
}
