package azmalent.potionsnowballs.compat.inspirations;

import net.minecraft.data.DataGenerator;

public interface IInspirationsCompat {
    void registerCauldronRecipes(DataGenerator generator);

    class Dummy implements IInspirationsCompat {
        @Override
        public void registerCauldronRecipes(DataGenerator generator) {

        }
    }
}


