package azmalent.potionsnowballs.compat.jei;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class TippedSnowballRecipeMaker {
    private static String splashGroup = "potionsnowballs.tipped.snowball.splash";
    private static String lingeringGroup = "potionsnowballs.tipped.snowball.lingering";

    private static ItemStack snowball = new ItemStack(Items.SNOWBALL);
    private static Ingredient snowballIngredient = Ingredient.fromStacks(snowball);

    private static ItemStack snowBlock = new ItemStack(Items.SNOW_BLOCK);
    private static Ingredient snowBlockIngredient = Ingredient.fromStacks(snowBlock);

    private static ShapedRecipe makeSplashRecipe(Potion potionType) {
        ItemStack potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potionType);
        Ingredient potionIngredient = Ingredient.fromStacks(potion);

        NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
            snowballIngredient, snowballIngredient, snowballIngredient,
            snowballIngredient, potionIngredient,   snowballIngredient,
            snowballIngredient, snowballIngredient, snowballIngredient
        );

        ItemStack output = PotionUtils.addPotionToItemStack(new ItemStack(ModItems.TIPPED_SNOWBALL.get(), 8), potionType);
        ResourceLocation id = new ResourceLocation(PotionSnowballs.MODID, splashGroup + "." + output.getTranslationKey());
        return new ShapedRecipe(id, splashGroup, 3, 3, inputs, output);
    }

    private static ShapedRecipe makeLingeringRecipe(Potion potionType) {
        ItemStack potion = PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potionType);
        Ingredient potionIngredient = Ingredient.fromStacks(potion);

        NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
            Ingredient.EMPTY,    snowBlockIngredient, Ingredient.EMPTY,
            snowBlockIngredient, potionIngredient,    snowBlockIngredient,
            Ingredient.EMPTY,    snowBlockIngredient, Ingredient.EMPTY
        );

        ItemStack output = PotionUtils.addPotionToItemStack(new ItemStack(ModItems.TIPPED_SNOWBALL.get(), 16), potionType);
        ResourceLocation id = new ResourceLocation(PotionSnowballs.MODID, lingeringGroup + "." + output.getTranslationKey());
        return new ShapedRecipe(id, lingeringGroup, 3, 3, inputs, output);
    }

    public static List<IShapedRecipe<?>> getCraftingRecipes() {
        List<IShapedRecipe<?>> recipes = new ArrayList<>();

        for (Potion potionType : ForgeRegistries.POTION_TYPES.getValues()) {
            ShapedRecipe splashRecipe = makeSplashRecipe(potionType);
            recipes.add(splashRecipe);

            ShapedRecipe lingeringRecipe = makeLingeringRecipe(potionType);
            recipes.add(lingeringRecipe);
        }

        return recipes;
    }
}

