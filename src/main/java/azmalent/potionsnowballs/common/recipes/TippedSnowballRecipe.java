package azmalent.potionsnowballs.common.recipes;

import azmalent.potionsnowballs.PotionSnowballs;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TippedSnowballRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private ItemStack dummyResult;

    public TippedSnowballRecipe() {
        setRegistryName(PotionSnowballs.MODID, "tipped_snowballs");
        dummyResult = new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, 8);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = inv.getStackInRowAndColumn(row, col);
                Item item = stack.getItem();

                if (row == 1 && col == 1) {
                    boolean isLingeringPotion = item == Items.LINGERING_POTION
                            && stack.hasTagCompound()
                            && stack.getTagCompound().hasKey("Potion");
                    if (!isLingeringPotion) return false;
                }
                else {
                    if (item != Items.SNOWBALL) return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, 8);
        result.setTagCompound(new NBTTagCompound());

        ItemStack potion = inv.getStackInRowAndColumn(1, 1);
        PotionUtils.appendEffects(result, PotionUtils.getPotionFromItem(potion).getEffects());

        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return dummyResult;
    }
}
