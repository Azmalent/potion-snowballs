package azmalent.potionsnowballs.common.compat.jei;

import azmalent.potionsnowballs.PotionSnowballs;
import knightminer.inspirations.plugins.jei.cauldron.CauldronRecipeCategory;
import knightminer.inspirations.plugins.jei.cauldron.ICauldronRecipeWrapper;
import knightminer.inspirations.plugins.jei.cauldron.PotionWrapper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.plugins.vanilla.brewing.PotionSubtypeInterpreter;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(PotionSnowballs.TIPPED_SNOWBALL, PotionSubtypeInterpreter.INSTANCE);
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.addRecipes(getCraftingRecipes(), VanillaRecipeCategoryUid.CRAFTING);

        if (PotionSnowballs.inspirationsCompat.cauldronPotionsEnabled()) {
            registry.addRecipes(getCauldronRecipes(), CauldronRecipeCategory.CATEGORY);
        }
    }

    private List<TippedSnowballRecipeWrapper> getCraftingRecipes() {
        List<TippedSnowballRecipeWrapper> recipes = new ArrayList();
        for (ResourceLocation potionTypeResourceLocation : PotionType.REGISTRY.getKeys()) {
            PotionType potionType = PotionType.REGISTRY.getObject(potionTypeResourceLocation);
            TippedSnowballRecipeWrapper recipe = new TippedSnowballRecipeWrapper(potionType);
            recipes.add(recipe);
        }

        return recipes;
    }

    private List<ICauldronRecipeWrapper> getCauldronRecipes() {
        List<ICauldronRecipeWrapper> recipes = new ArrayList();
        recipes.add(new PotionWrapper.Fill(
                new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, 8),
                new ItemStack(Items.SNOWBALL, 8))
        );

        return recipes;
    }
}
