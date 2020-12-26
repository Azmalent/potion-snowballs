package azmalent.potionsnowballs.common.init;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.recipe.TippedSnowballLingeringRecipe;
import azmalent.potionsnowballs.common.recipe.TippedSnowballSplashRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PotionSnowballs.MODID);

    public static final RegistryObject<SpecialRecipeSerializer<?>> TIPPED_SNOWBALL_FROM_SPLASH_POTION = RECIPES.register(
        "tipped_snowball_splash", () -> new SpecialRecipeSerializer<>(TippedSnowballSplashRecipe::new)
    );

    public static final RegistryObject<SpecialRecipeSerializer<?>> TIPPED_SNOWBALL_FROM_LINGERING_POTION =  RECIPES.register(
        "tipped_snowball_lingering", () -> new SpecialRecipeSerializer<>(TippedSnowballLingeringRecipe::new)
    );
}
