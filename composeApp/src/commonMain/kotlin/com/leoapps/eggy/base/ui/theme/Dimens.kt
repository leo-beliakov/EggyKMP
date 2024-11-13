package com.leoapps.eggy.base.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    // Screen Paddings
    val screenPaddingS: Dp = 8.dp,
    val screenPaddingM: Dp = 16.dp,
    val screenPaddingL: Dp = 24.dp,
    val screenPaddingXL: Dp = 32.dp,

    // Spaces between Elements
    val spaceXS: Dp = 6.dp,
    val spaceS: Dp = 8.dp,
    val spaceM: Dp = 12.dp,
    val spaceL: Dp = 16.dp,
    val spaceXL: Dp = 18.dp,

    // Element Sizes
    val buttonMaxWidth: Dp = 500.dp,
    val buttonHeight: Dp = 64.dp,
    val selectableButtonHeight: Dp = 80.dp,
    val selectableIconedButtonHeight: Dp = 120.dp,

    // Corner Radiuses
    val cornerS: Dp = 6.dp,
    val cornerM: Dp = 12.dp,
    val cornerL: Dp = 16.dp,

    // Elevations
    val elevationS: Dp = 2.dp,
    val elevationM: Dp = 4.dp,
    val elevationL: Dp = 8.dp,

    // Icon Sizes
    val iconSizeS: Dp = 16.dp,
    val iconSizeM: Dp = 24.dp,
    val iconSizeL: Dp = 32.dp,
    val iconSizeXL: Dp = 42.dp,

    // Interactive Components Sizes
    val minimumInteractiveComponentSize: Dp = 48.dp,

    // Paddings
    val paddingXS: Dp = 4.dp,
    val paddingS: Dp = 8.dp,
    val paddingM: Dp = 12.dp,
    val paddingL: Dp = 16.dp
)

internal val LocalDimens = staticCompositionLocalOf { Dimensions() }

val MaterialTheme.dimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current