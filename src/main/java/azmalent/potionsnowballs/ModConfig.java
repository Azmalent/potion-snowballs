package azmalent.potionsnowballs;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = PotionSnowballs.MODID)
public class ModConfig {
    @Name("Normal Potion Output Factor")
    @Comment("Amount of snowballs crafted with a normal potion. 0 = disabled, 1 = normal, 2 = double (uses snow blocks)")
    @RangeInt(min=0, max=2)
    public static int normalPotionOutput = 0;

    @Name("Splash Potion Output Factor")
    @Comment("Amount of snowballs crafted with a splash potion. 0 = disabled, 1 = normal, 2 = double (uses snow blocks)")
    @RangeInt(min=0, max=2)
    public static int splashPotionOutput = 1;

    @Name("Lingering Potion Output Factor")
    @Comment("Amount of snowballs crafted with a normal potion. 0 = disabled, 1 = normal, 2 = double (uses snow blocks)")
    @RangeInt(min=0, max=2)
    public static int lingeringPotionOutput = 2;

    @Name("Cauldron Output Factor")
    @Comment("Amount of snowballs crafted in a cauldron (requires Inspirations).  0 = disabled, 1 = normal, 2 = double")
    @RangeInt(min=0, max=2)
    public static int cauldronOutput = 1;
}