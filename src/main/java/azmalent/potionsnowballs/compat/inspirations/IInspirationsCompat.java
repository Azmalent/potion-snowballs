package azmalent.potionsnowballs.compat.inspirations;

import net.minecraft.data.DataGenerator;

public interface IInspirationsCompat {
    boolean isModLoaded();

    void registerCauldronRecipes(DataGenerator generator);

    public static class Dummy implements IInspirationsCompat {
        @Override
        public boolean isModLoaded() {
            return false;
        }

        @Override
        public void registerCauldronRecipes(DataGenerator generator) {

        }
    }
}


