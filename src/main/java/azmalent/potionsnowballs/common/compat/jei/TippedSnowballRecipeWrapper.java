package azmalent.potionsnowballs.common.compat.jei;

import azmalent.potionsnowballs.ModConfig;
import azmalent.potionsnowballs.PotionSnowballs;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

import java.util.Arrays;
import java.util.List;

public class TippedSnowballRecipeWrapper implements IShapedCraftingRecipeWrapper {
    private final List<ItemStack> inputs;
    private final ItemStack output;

    public TippedSnowballRecipeWrapper(PotionType potionType, Item potionItem, int outputFactor) {
        ItemStack potion = PotionUtils.addPotionToItemStack(new ItemStack(potionItem), potionType);
        if (outputFactor > 1) {
            ItemStack snowBlock = new ItemStack(ItemBlock.getItemFromBlock(Blocks.SNOW));
            this.inputs = Arrays.asList(
                    null, snowBlock, null,
                    snowBlock, potion, snowBlock,
                    null, snowBlock, null
            );
        }
        else {
            ItemStack snowball = new ItemStack(Items.SNOWBALL);
            this.inputs = Arrays.asList(
                    snowball, snowball, snowball,
                    snowball, potion, snowball,
                    snowball, snowball, snowball
            );
        }

        ItemStack outputStack = new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, outputFactor * 8);
        this.output = PotionUtils.addPotionToItemStack(outputStack, potionType);
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