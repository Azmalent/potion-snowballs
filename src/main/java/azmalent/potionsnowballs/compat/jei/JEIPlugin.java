package azmalent.potionsnowballs.compat.jei;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.plugins.vanilla.brewing.PotionSubtypeInterpreter;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(PotionSnowballs.MODID, "jei");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ModItems.TIPPED_SNOWBALL.get(), PotionSubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration recipeRegistry) {
        recipeRegistry.addRecipes(TippedSnowballRecipeMaker.getCraftingRecipes(), VanillaRecipeCategoryUid.CRAFTING);
    }
}

