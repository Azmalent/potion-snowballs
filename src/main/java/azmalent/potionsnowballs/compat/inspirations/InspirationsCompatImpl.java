package azmalent.potionsnowballs.compat.inspirations;

import azmalent.potionsnowballs.PotionSnowballs;
import knightminer.inspirations.common.Config;
import knightminer.inspirations.library.recipe.cauldron.special.FillPotionCauldronRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import slimeknights.mantle.recipe.SizedIngredient;

import java.util.function.Consumer;

public class InspirationsCompatImpl implements IInspirationsCompat {
    @Override
    public boolean isModLoaded() {
        return true;
    }

    @Override
    public void registerCauldronRecipes(DataGenerator generator) {
        generator.addProvider(new RecipeProvider(generator) {
            @Override
            protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
                PotionSnowballs.LOGGER.info("Registering cauldron recipe...");
                if (Config.enableCauldronPotions.get() && Config.cauldronTipArrows.get()) {
                    consumer.accept(new FillPotionCauldronRecipe.FinishedRecipe(
                            new ResourceLocation(PotionSnowballs.MODID, "cauldron/tipped_snowball"),
                            SizedIngredient.fromItems(16, Items.SNOWBALL), PotionSnowballs.TIPPED_ITEM.get()
                    ));
                }
            }
        });
    }
}
