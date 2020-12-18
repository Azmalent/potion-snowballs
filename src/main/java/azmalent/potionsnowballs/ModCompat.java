package azmalent.potionsnowballs;

import azmalent.potionsnowballs.compat.inspirations.IInspirationsCompat;
import net.minecraftforge.fml.ModList;

public class ModCompat {
    public static IInspirationsCompat INSPIRATIONS;

    static {
        if (ModList.get().isLoaded("inspirations")) {
            try {
                INSPIRATIONS = Class.forName("azmalent.potionsnowballs.compat.inspirations.InspirationsCompatImpl").asSubclass(IInspirationsCompat.class).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            INSPIRATIONS = new IInspirationsCompat.Dummy();
        }
    }
}
