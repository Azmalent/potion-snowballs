package azmalent.potionsnowballs.common.compat.jei;

import azmalent.potionsnowballs.PotionSnowballs;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.Arrays;
import java.util.List;

public class TippedSnowballRecipeWrapper implements IShapedCraftingRecipeWrapper {
    private final List<ItemStack> inputs;
    private final ItemStack output;

    public TippedSnowballRecipeWrapper(PotionType type) {
        ItemStack snowball = new ItemStack(Items.SNOWBALL);
        ItemStack lingeringPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), type);
        this.inputs = Arrays.asList(
                snowball, snowball, snowball,
                snowball, lingeringPotion, snowball,
                snowball, snowball, snowball
        );
        ItemStack outputStack = new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, 8);
        this.output = PotionUtils.addPotionToItemStack(outputStack, type);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, this.inputs);
        ingredients.setOutput(VanillaTypes.ITEM, this.output);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}