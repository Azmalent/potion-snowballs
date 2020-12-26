package azmalent.potionsnowballs.compat.consecration;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.consecration.common.ConsecrationConfig;
import top.theillusivec4.consecration.common.ConsecrationUtils;
import top.theillusivec4.consecration.common.capability.UndyingCapability;

public interface IConsecrationCompat {
    void smiteWithHealingPotion(LivingEntity livingEntity);

    class Dummy implements IConsecrationCompat {
        @Override
        public void smiteWithHealingPotion(LivingEntity livingEntity) {

        }
    }
}
