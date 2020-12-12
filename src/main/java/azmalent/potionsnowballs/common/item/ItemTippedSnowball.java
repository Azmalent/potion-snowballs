package azmalent.potionsnowballs.common.item;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.entity.EntityTippedSnowball;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTippedSnowball extends ItemSnowball
{
    public static final String NAME = "tipped_snowball";
    public static final String REGISTRY_NAME = PotionSnowballs.MODID + ":" + NAME;

    public ItemTippedSnowball() {
        setRegistryName(NAME);
        setTranslationKey(NAME);
        setCreativeTab(CreativeTabs.MISC);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), PotionTypes.POISON);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityTippedSnowball snowball = new EntityTippedSnowball(worldIn, playerIn, stack);
            snowball.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(snowball);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (PotionType potionType : PotionType.REGISTRY) {
                if (!potionType.getEffects().isEmpty()) {
                    items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potionType));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        PotionUtils.addPotionTooltip(stack, tooltip, 0.125F);
    }

    public String getItemStackDisplayName(ItemStack stack) {
        String key = PotionUtils.getPotionFromItem(stack).getNamePrefixed("snowball.effect.");
        if (I18n.hasKey(key)) return I18n.format(key);

        //Attempt to generate item name if it's missing
        String arrowKey = PotionUtils.getPotionFromItem(stack).getNamePrefixed("tipped_arrow.effect.");
        if (I18n.hasKey(arrowKey)) {
            String arrowName = I18n.format(arrowKey);

            String arrow = I18n.format("item.arrow.name");
            String snowball = I18n.format("item.snowball.name");
            if (arrowName.contains(arrow)) return arrowName.replaceFirst(arrow, snowball);
        }

        return key;
    }
}