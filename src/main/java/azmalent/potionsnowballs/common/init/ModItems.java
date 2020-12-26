package azmalent.potionsnowballs.common.init;

import azmalent.potionsnowballs.PotionSnowballs;
import azmalent.potionsnowballs.common.item.SpectreSnowballItem;
import azmalent.potionsnowballs.common.item.TippedSnowballItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PotionSnowballs.MODID);

    public static final RegistryObject<Item> TIPPED_SNOWBALL = ITEMS.register("tipped_snowball", TippedSnowballItem::new);
    public static final RegistryObject<Item> SPECTRE_SNOWBALL = ITEMS.register("spectre_snowball", SpectreSnowballItem::new);
}
