package azmalent.potionsnowballs.common.compat.inspirations;

public class DummyInspirationsCompat implements InspirationsCompatProxy {
    @Override
    public boolean cauldronPotionsEnabled() {
        return false;
    }

    @Override
    public void initCauldronRecipes() {

    }
}
