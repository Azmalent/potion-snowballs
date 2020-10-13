package azmalent.potionsnowballs.common.compat;

import net.minecraftforge.fml.common.Loader;

public class ModCompatUtil {
    public static <TProxy, TDummy extends TProxy> TProxy initProxy(String modid, Class<TProxy> proxyClass, String implClassName, Class<TDummy> dummyClass) {
        try {
            if (Loader.isModLoaded(modid)) {
                return Class.forName(implClassName).asSubclass(proxyClass).newInstance();
            }
            else {
                return dummyClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
