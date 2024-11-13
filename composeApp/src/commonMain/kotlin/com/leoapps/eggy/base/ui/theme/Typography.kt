package com.leoapps.eggy.base.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import eggy.composeapp.generated.resources.Res
import eggy.composeapp.generated.resources.monsterrat_bold
import eggy.composeapp.generated.resources.monsterrat_extrabold
import eggy.composeapp.generated.resources.monsterrat_regular
import eggy.composeapp.generated.resources.titan_one
import org.jetbrains.compose.resources.Font

val MonsterratFontFamily
    @Composable get() = FontFamily(
        fonts = listOf(
            Font(
                resource = Res.font.monsterrat_extrabold,
                weight = FontWeight.W900,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.monsterrat_bold,
                weight = FontWeight.W700,
                style = FontStyle.Normal
            ),
            Font(
                resource = Res.font.monsterrat_regular,
                weight = FontWeight.W500,
                style = FontStyle.Normal
            )
        )
    )

val TitanOneFamily
    @Composable get() = FontFamily(
        fonts = listOf(
            Font(resource = Res.font.titan_one)
        )
    )

// Set of Material typography styles to start with
val Typography
    @Composable get() = Typography(
        displayMedium = TextStyle(
            fontWeight = FontWeight.W900,
            fontFamily = TitanOneFamily,
            textAlign = TextAlign.Center,
            fontSize = 46.sp,
            lineHeight = 36.sp,
            color = Black,
        ),
        displaySmall = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.W900,
            fontSize = 34.sp,
            lineHeight = 40.sp,
            color = Black
        ),
        headlineLarge = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.W900,
            fontSize = 26.sp,
            lineHeight = 32.sp,
            letterSpacing = -0.5.sp,
            color = Black
        ),
        headlineMedium = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.W900,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = -0.5.sp,
            color = Black
        ),
        headlineSmall = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.3.sp,
            color = Black
        ),
        titleLarge = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.2.sp,
            color = Black
        ),
        titleMedium = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = 0.1.sp,
            color = Black
        ),
        titleSmall = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            letterSpacing = 0.2.sp,
            lineHeight = 20.sp,
            color = GrayLight
        ),
        bodyLarge = TextStyle(
            fontFamily = MonsterratFontFamily,
            fontWeight = FontWeight.Normal,
            letterSpacing = 0.1.sp,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        )
    )
