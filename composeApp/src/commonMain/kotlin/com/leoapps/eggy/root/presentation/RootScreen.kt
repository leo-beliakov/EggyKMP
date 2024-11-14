package com.leoapps.eggy.root.presentation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.eggy.welcome.presentation.BoilSetupScreen
import com.leoapps.eggy.welcome.presentation.WelcomeScreen
import com.leoapps.progress.presentation.BoilProgressScreen
import com.leoapps.progress.presentation.BoilProgressViewModel
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreenDestination

@Serializable
object BoilSetupScreenDestination

@Serializable
data class BoilProgressScreenDestination(
    val type: EggBoilingType,
    val calculatedTime: Long,
)

private const val NAVIGATION_ANIM_DURATION = 400

@Composable
fun RootScreen() {
    val navController = rememberNavController()
    val fadeAnimationSpec = tween<Float>(
        durationMillis = NAVIGATION_ANIM_DURATION,
        easing = LinearEasing
    )
    val slideAnimationSpec = tween<IntOffset>(
        durationMillis = NAVIGATION_ANIM_DURATION,
        easing = LinearEasing
    )

    NavHost(
        navController = navController,
        startDestination = WelcomeScreenDestination
    ) {
        composable<WelcomeScreenDestination>(
            exitTransition = { fadeOut(fadeAnimationSpec) }
        ) {
            WelcomeScreen(
                onContinueClicked = {
                    navController.navigate(BoilSetupScreenDestination) {
                        popUpTo<WelcomeScreenDestination> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<BoilSetupScreenDestination>(
            enterTransition = { fadeIn(fadeAnimationSpec) },
            exitTransition = { fadeOut(fadeAnimationSpec) }
        ) {
            BoilSetupScreen(
                onContinueClicked = { type, time ->
                    navController.navigate(
                        BoilProgressScreenDestination(
                            type = type,
                            calculatedTime = time,
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<BoilProgressScreenDestination>(
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = slideAnimationSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = slideAnimationSpec
                )
            }
        ) {
            BoilProgressScreen(
                viewModel = BoilProgressViewModel(SavedStateHandle()),
                onBackClicked = {
                    navController.navigateUp()
                }
            )
        }
    }
}