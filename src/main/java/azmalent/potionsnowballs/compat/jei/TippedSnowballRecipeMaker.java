package azmalent.potionsnowballs.compat.jei;

import azmalent.potionsnowballs.PotionSnowballs;
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
    public static List<IShapedRecipe<?>> getCraftingRecipes() {
        List<IShapedRecipe<?>> recipes = new ArrayList<>();
        String group = "potionsnowballs.tipped.snowball";

        for (Potion potionType : ForgeRegistries.POTION_TYPES.getValues()) {
            ItemStack snowBlock = new ItemStack(Items.SNOW_BLOCK);
            ItemStack lingeringPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potionType);

            Ingredient snowBlockIngredient = Ingredient.fromStacks(snowBlock);
            Ingredient potionIngredient = Ingredient.fromStacks(lingeringPotion);
            NonNullList<Ingredient> inputs = NonNullList.from(Ingredient.EMPTY,
                Ingredient.EMPTY,       snowBlockIngredient,    Ingredient.EMPTY,
                snowBlockIngredient,    potionIngredient,       snowBlockIngredient,
                Ingredient.EMPTY,       snowBlockIngredient,    Ingredient.EMPTY
            );

            ItemStack output = new ItemStack(PotionSnowballs.TIPPED_ITEM.get(), 16);
            PotionUtils.addPotionToItemStack(output, potionType);
            ResourceLocation id = new ResourceLocation(PotionSnowballs.MODID, group + "." + output.getTranslationKey());
            ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }
}

