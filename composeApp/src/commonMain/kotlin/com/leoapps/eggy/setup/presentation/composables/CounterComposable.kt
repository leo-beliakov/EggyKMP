package com.leoapps.setup.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.leoapps.eggy.common.utils.EMPTY_CALCULATED_TIME

@Composable
fun CounterComposable(
    currentTimeText: String,
) {
    var previousTimeText by remember { mutableStateOf(EMPTY_CALCULATED_TIME) }

    SideEffect {
        previousTimeText = currentTimeText
    }

    Row {
        currentTimeText.forEachIndexed { index, char ->
            if (char.isDigit()) {
                AnimatedContent(
                    targetState = currentTimeText[index],
                    transitionSpec = {
                        if (previousTimeText[index].digitToInt() < currentTimeText[index].digitToInt()) {
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                        } else {
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        }
                    }
                ) { digit ->
                    Text(
                        text = digit.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        softWrap = false,
                    )
                }
            } else {
                Text(
                    text = char.toString(),
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}