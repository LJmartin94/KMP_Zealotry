package presentation.screens.dayPartMenu.morningButtons

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.reusableUi.ChiaroscuroDrawable
import presentation.screens.dayPartMenu.checklistButtons.IconTextTimeBundle
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.asclepius_small
import zealotry.composeapp.generated.resources.asclepius_small_inv
import zealotry.composeapp.generated.resources.bins
import zealotry.composeapp.generated.resources.bins_small
import zealotry.composeapp.generated.resources.bins_small_inv
import zealotry.composeapp.generated.resources.breakfast
import zealotry.composeapp.generated.resources.brush_teeth
import zealotry.composeapp.generated.resources.coffee
import zealotry.composeapp.generated.resources.cosmeticism
import zealotry.composeapp.generated.resources.curtains_small
import zealotry.composeapp.generated.resources.curtains_small_inv
import zealotry.composeapp.generated.resources.dishes
import zealotry.composeapp.generated.resources.energy_rune_small
import zealotry.composeapp.generated.resources.energy_rune_small_inv
import zealotry.composeapp.generated.resources.get_up
import zealotry.composeapp.generated.resources.grain_jar_small
import zealotry.composeapp.generated.resources.grain_jar_small_inv
import zealotry.composeapp.generated.resources.health_rune_small
import zealotry.composeapp.generated.resources.health_rune_small_inv
import zealotry.composeapp.generated.resources.ic_baseline_flare_24
import zealotry.composeapp.generated.resources.ic_baseline_flare_24_inv
import zealotry.composeapp.generated.resources.imix_small
import zealotry.composeapp.generated.resources.imix_small_inv
import zealotry.composeapp.generated.resources.language
import zealotry.composeapp.generated.resources.language_small
import zealotry.composeapp.generated.resources.language_small_inv
import zealotry.composeapp.generated.resources.lotus_watered_small
import zealotry.composeapp.generated.resources.lotus_watered_small_inv
import zealotry.composeapp.generated.resources.make_bed
import zealotry.composeapp.generated.resources.meditation
import zealotry.composeapp.generated.resources.meditation_small
import zealotry.composeapp.generated.resources.meditation_small_inv
import zealotry.composeapp.generated.resources.news
import zealotry.composeapp.generated.resources.news_small
import zealotry.composeapp.generated.resources.news_small_inv
import zealotry.composeapp.generated.resources.open_curtains
import zealotry.composeapp.generated.resources.pack_bag
import zealotry.composeapp.generated.resources.pack_small
import zealotry.composeapp.generated.resources.pack_small_inv
import zealotry.composeapp.generated.resources.posession_stone_small
import zealotry.composeapp.generated.resources.posession_stone_small_inv
import zealotry.composeapp.generated.resources.shower
import zealotry.composeapp.generated.resources.smoothie
import zealotry.composeapp.generated.resources.tooth_alt_small
import zealotry.composeapp.generated.resources.tooth_alt_small_inv
import zealotry.composeapp.generated.resources.vitamins
import zealotry.composeapp.generated.resources.wake_up
import zealotry.composeapp.generated.resources.wake_up_small
import zealotry.composeapp.generated.resources.wake_up_small_inv
import zealotry.composeapp.generated.resources.water
import zealotry.composeapp.generated.resources.water_alt_small
import zealotry.composeapp.generated.resources.water_alt_small_inv
import zealotry.composeapp.generated.resources.water_bath_small
import zealotry.composeapp.generated.resources.water_bath_small_inv
import zealotry.composeapp.generated.resources.water_plants

enum class MorningButtons {
    WAKE_UP,
    GET_UP,
    SHOWER,
    NEWS,
    OPEN_CURTAINS,
    COFFEE,
    SMOOTHIE,
    WATER,
    VITAMINS,
    DISHES,
    BREAKFAST,
    WATER_PLANTS,
    MEDITATION,
    LANGUAGE,
    BRUSH_TEETH,
    COSMETICISM,
    PACK_BAG,
    MAKE_BED,
    BINS,
}

@Composable
@Suppress("LongMethod", "CyclomaticComplexMethod")
@OptIn(ExperimentalResourceApi::class)
fun MorningButtons.toBundle(): IconTextTimeBundle =
    when (this) {
        MorningButtons.WAKE_UP ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.wake_up_small, Res.drawable.wake_up_small_inv),
                text = stringResource(Res.string.wake_up),
            )
        MorningButtons.GET_UP ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.wake_up_small, Res.drawable.wake_up_small_inv),
                text = stringResource(Res.string.get_up),
            )
        MorningButtons.SHOWER ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.water_bath_small, Res.drawable.water_bath_small_inv),
                text = stringResource(Res.string.shower),
            )
        MorningButtons.NEWS ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.news_small, Res.drawable.news_small_inv),
                text = stringResource(Res.string.news),
            )
        MorningButtons.OPEN_CURTAINS ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.curtains_small, Res.drawable.curtains_small_inv),
                text = stringResource(Res.string.open_curtains),
            )
        MorningButtons.COFFEE ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.energy_rune_small, Res.drawable.energy_rune_small_inv),
                text = stringResource(Res.string.coffee),
            )
        MorningButtons.SMOOTHIE ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.health_rune_small, Res.drawable.health_rune_small_inv),
                text = stringResource(Res.string.smoothie),
            )
        MorningButtons.WATER ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.water_alt_small, Res.drawable.water_alt_small_inv),
                text = stringResource(Res.string.water),
            )
        MorningButtons.VITAMINS ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.asclepius_small, Res.drawable.asclepius_small_inv),
                text = stringResource(Res.string.vitamins),
            )
        MorningButtons.DISHES ->
            IconTextTimeBundle(
                chiaro =
                    ChiaroscuroDrawable(
                        Res.drawable.posession_stone_small,
                        Res.drawable.posession_stone_small_inv,
                    ),
                text = stringResource(Res.string.dishes),
            )
        MorningButtons.BREAKFAST ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.grain_jar_small, Res.drawable.grain_jar_small_inv),
                text = stringResource(Res.string.breakfast),
            )
        MorningButtons.WATER_PLANTS ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.lotus_watered_small, Res.drawable.lotus_watered_small_inv),
                text = stringResource(Res.string.water_plants),
            )
        MorningButtons.MEDITATION ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.meditation_small, Res.drawable.meditation_small_inv),
                text = stringResource(Res.string.meditation),
            )
        MorningButtons.LANGUAGE ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.language_small, Res.drawable.language_small_inv),
                text = stringResource(Res.string.language),
            )
        MorningButtons.BRUSH_TEETH ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.tooth_alt_small, Res.drawable.tooth_alt_small_inv),
                text = stringResource(Res.string.brush_teeth),
            )
        MorningButtons.COSMETICISM ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.ic_baseline_flare_24, Res.drawable.ic_baseline_flare_24_inv),
                text = stringResource(Res.string.cosmeticism),
            )
        MorningButtons.PACK_BAG ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.pack_small, Res.drawable.pack_small_inv),
                text = stringResource(Res.string.pack_bag),
            )
        MorningButtons.MAKE_BED ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.imix_small, Res.drawable.imix_small_inv),
                text = stringResource(Res.string.make_bed),
            )
        MorningButtons.BINS ->
            IconTextTimeBundle(
                chiaro = ChiaroscuroDrawable(Res.drawable.bins_small, Res.drawable.bins_small_inv),
                text = stringResource(Res.string.bins),
            )
    }
