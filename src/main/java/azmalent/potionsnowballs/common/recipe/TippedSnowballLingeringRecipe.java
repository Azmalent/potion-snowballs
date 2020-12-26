package azmalent.potionsnowballs.common.recipe;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.init.ModItems;
import azmalent.potionsnowballs.common.init.ModRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TippedSnowballLingeringRecipe extends SpecialRecipe {
    private static ItemStack DUMMY = new ItemStack(ModItems.TIPPED_SNOWBALL.get(), 16);

    public TippedSnowballLingeringRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            Item item = stack.getItem();

            if (i == 4) {
                boolean isLingeringPotion = item == Items.LINGERING_POTION
                        && stack.hasTag() && stack.getTag().contains("Potion");
                if (!isLingeringPotion) return false;
            }
            else if (i % 2 == 1) {
                if (item != Items.SNOW_BLOCK) return false;
            }
            else if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack result = new ItemStack(ModItems.TIPPED_SNOWBALL.get(), 16);
        result.setTag(new CompoundNBT());

        ItemStack potion = inv.getStackInSlot(4);
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
        return DUMMY;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.TIPPED_SNOWBALL_FROM_LINGERING_POTION.get();
    }
}