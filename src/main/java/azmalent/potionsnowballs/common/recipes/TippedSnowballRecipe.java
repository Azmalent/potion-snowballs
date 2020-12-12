package azmalent.potionsnowballs.common.recipes;

import azmalent.potionsnowballs.PotionSnowballs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class TippedSnowballRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private Item potionItem;
    private int outputFactor;

    public TippedSnowballRecipe(String id, Item potionItem, int outputFactor) {
        setRegistryName(PotionSnowballs.MODID, id);
        this.potionItem = potionItem;
        this.outputFactor = outputFactor;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        boolean requireBlocks = outputFactor > 1;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);

            if (i == 4) {
                boolean isPotion = stack.getItem() == potionItem
                        && stack.hasTagCompound()
                        && stack.getTagCompound().hasKey("Potion");
                if (!isPotion) return false;
            }
            else if (requireBlocks) {
                if (i % 2 == 1) {
                    if (stack.getItem() != ItemBlock.getItemFromBlock(Blocks.SNOW)) return false;
                }
                else if (!stack.isEmpty()) return false;
            }
            else if (stack.getItem() != Items.SNOWBALL) return false;
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        ItemStack result = new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, outputFactor * 8);
        result.setTagCompound(new NBTTagCompound());

        ItemStack potion = inv.getStackInRowAndColumn(1, 1);
        PotionUtils.addPotionToItemStack(result, PotionUtils.getPotionFromItem(potion));
        PotionUtils.appendEffects(result, PotionUtils.getFullEffectsFromItem(potion));

        return result;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(PotionSnowballs.TIPPED_SNOWBALL, outputFactor * 8);
    }
}
