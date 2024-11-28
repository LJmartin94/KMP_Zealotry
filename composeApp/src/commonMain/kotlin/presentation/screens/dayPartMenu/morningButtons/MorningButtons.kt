package presentation.screens.dayPartMenu.morningButtons

import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.reusableUi.IconTextTimeBundle
import zealotry.composeapp.generated.resources.Res
import zealotry.composeapp.generated.resources.asclepius_small
import zealotry.composeapp.generated.resources.bins
import zealotry.composeapp.generated.resources.bins_small
import zealotry.composeapp.generated.resources.breakfast
import zealotry.composeapp.generated.resources.brush_teeth
import zealotry.composeapp.generated.resources.coffee
import zealotry.composeapp.generated.resources.cosmeticism
import zealotry.composeapp.generated.resources.curtains_small
import zealotry.composeapp.generated.resources.dishes
import zealotry.composeapp.generated.resources.energy_rune_small
import zealotry.composeapp.generated.resources.get_up
import zealotry.composeapp.generated.resources.grain_jar_small
import zealotry.composeapp.generated.resources.health_rune_small
import zealotry.composeapp.generated.resources.imix_small
import zealotry.composeapp.generated.resources.language
import zealotry.composeapp.generated.resources.language_small
import zealotry.composeapp.generated.resources.lotus_watered_small
import zealotry.composeapp.generated.resources.make_bed
import zealotry.composeapp.generated.resources.meditation
import zealotry.composeapp.generated.resources.meditation_small
import zealotry.composeapp.generated.resources.news
import zealotry.composeapp.generated.resources.news_small
import zealotry.composeapp.generated.resources.open_curtains
import zealotry.composeapp.generated.resources.pack_bag
import zealotry.composeapp.generated.resources.pack_small
import zealotry.composeapp.generated.resources.perfume_small
import zealotry.composeapp.generated.resources.posession_stone_small
import zealotry.composeapp.generated.resources.shower
import zealotry.composeapp.generated.resources.smoothie
import zealotry.composeapp.generated.resources.tooth_alt_small
import zealotry.composeapp.generated.resources.vitamins
import zealotry.composeapp.generated.resources.wake_up
import zealotry.composeapp.generated.resources.wake_up_small
import zealotry.composeapp.generated.resources.water
import zealotry.composeapp.generated.resources.water_alt_small
import zealotry.composeapp.generated.resources.water_bath_small
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

@Suppress("LongMethod", "CyclomaticComplexMethod")
@OptIn(ExperimentalResourceApi::class)
fun MorningButtons.toBundle(): IconTextTimeBundle =
    when (this) {
        MorningButtons.WAKE_UP ->
            IconTextTimeBundle(
                iconRes = Res.drawable.wake_up_small,
                textRes = Res.string.wake_up,
            )
        MorningButtons.GET_UP ->
            IconTextTimeBundle(
                iconRes = Res.drawable.wake_up_small,
                textRes = Res.string.get_up,
            )
        MorningButtons.SHOWER ->
            IconTextTimeBundle(
                iconRes = Res.drawable.water_bath_small,
                textRes = Res.string.shower,
            )
        MorningButtons.NEWS ->
            IconTextTimeBundle(
                iconRes = Res.drawable.news_small,
                textRes = Res.string.news,
            )
        MorningButtons.OPEN_CURTAINS ->
            IconTextTimeBundle(
                iconRes = Res.drawable.curtains_small,
                textRes = Res.string.open_curtains,
            )
        MorningButtons.COFFEE ->
            IconTextTimeBundle(
                iconRes = Res.drawable.energy_rune_small,
                textRes = Res.string.coffee,
            )
        MorningButtons.SMOOTHIE ->
            IconTextTimeBundle(
                iconRes = Res.drawable.health_rune_small,
                textRes = Res.string.smoothie,
            )
        MorningButtons.WATER ->
            IconTextTimeBundle(
                iconRes = Res.drawable.water_alt_small,
                textRes = Res.string.water,
            )
        MorningButtons.VITAMINS ->
            IconTextTimeBundle(
                iconRes = Res.drawable.asclepius_small,
                textRes = Res.string.vitamins,
            )
        MorningButtons.DISHES ->
            IconTextTimeBundle(
                iconRes = Res.drawable.posession_stone_small,
                textRes = Res.string.dishes,
            )
        MorningButtons.BREAKFAST ->
            IconTextTimeBundle(
                iconRes = Res.drawable.grain_jar_small,
                textRes = Res.string.breakfast,
            )
        MorningButtons.WATER_PLANTS ->
            IconTextTimeBundle(
                iconRes = Res.drawable.lotus_watered_small,
                textRes = Res.string.water_plants,
            )
        MorningButtons.MEDITATION ->
            IconTextTimeBundle(
                iconRes = Res.drawable.meditation_small,
                textRes = Res.string.meditation,
            )
        MorningButtons.LANGUAGE ->
            IconTextTimeBundle(
                iconRes = Res.drawable.language_small,
                textRes = Res.string.language,
            )
        MorningButtons.BRUSH_TEETH ->
            IconTextTimeBundle(
                iconRes = Res.drawable.tooth_alt_small,
                textRes = Res.string.brush_teeth,
            )
        MorningButtons.COSMETICISM ->
            IconTextTimeBundle(
                iconRes = Res.drawable.perfume_small, // TODO: change
                textRes = Res.string.cosmeticism,
            )
        MorningButtons.PACK_BAG ->
            IconTextTimeBundle(
                iconRes = Res.drawable.pack_small,
                textRes = Res.string.pack_bag,
            )
        MorningButtons.MAKE_BED ->
            IconTextTimeBundle(
                iconRes = Res.drawable.imix_small,
                textRes = Res.string.make_bed,
            )
        MorningButtons.BINS ->
            IconTextTimeBundle(
                iconRes = Res.drawable.bins_small,
                textRes = Res.string.bins,
            )
    }
