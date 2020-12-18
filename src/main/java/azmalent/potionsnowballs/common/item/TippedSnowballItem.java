package azmalent.potionsnowballs.common.item;

import azmalent.potionsnowballs.common.entity.TippedSnowballEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SnowballItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TippedSnowballItem extends SnowballItem {
    public TippedSnowballItem() {
        super(new Properties().group(ItemGroup.COMBAT).maxStackSize(16));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!worldIn.isRemote) {
            TippedSnowballEntity snowball = new TippedSnowballEntity(playerIn, worldIn);
            snowball.setItem(stack);
            snowball.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.addEntity(snowball);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }

        return ActionResult.func_233538_a_(stack, worldIn.isRemote());
    }

    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), Potions.POISON);
    }

    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            for(Potion potion : Registry.POTION) {
                if (!potion.getEffects().isEmpty()) {
                    items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potion));
                }
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        String key = PotionUtils.getPotionFromItem(stack).getNamePrefixed(this.getTranslationKey() + ".effect.");
        if (I18n.hasKey(key)) return new TranslationTextComponent(key);

        //Attempt to generate item name if it's missing
        String arrowKey = PotionUtils.getPotionFromItem(stack).getNamePrefixed(Items.TIPPED_ARROW.getTranslationKey() + ".effect.");
        if (I18n.hasKey(arrowKey)) {
            String arrowName = I18n.format(arrowKey);

            String arrow = I18n.format("item.minecraft.arrow");
            String snowball = I18n.format("item.minecraft.snowball");
            if (arrowName.contains(arrow)) {
                return new StringTextComponent(arrowName.replaceFirst(arrow, snowball));
            }
        }

        return new StringTextComponent(key);
    }
}
