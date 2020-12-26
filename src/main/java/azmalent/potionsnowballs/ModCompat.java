package azmalent.potionsnowballs;

import azmalent.potionsnowballs.compat.consecration.IConsecrationCompat;
import azmalent.potionsnowballs.compat.inspirations.IInspirationsCompat;
import net.minecraftforge.fml.ModList;

public class ModCompat {
    public static IConsecrationCompat CONSECRATION;
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

        if (ModList.get().isLoaded("consecration")) {
            try {
                CONSECRATION = Class.forName("azmalent.potionsnowballs.compat.consecration.ConsecrationCompatImpl").asSubclass(IConsecrationCompat.class).newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            CONSECRATION = new IConsecrationCompat.Dummy();
        }
    }
}
