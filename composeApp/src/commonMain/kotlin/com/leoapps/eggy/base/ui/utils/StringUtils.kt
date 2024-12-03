package com.leoapps.base_ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun annotatedStringResource(res: StringResource): AnnotatedString {
    val string = stringResource(res)
    return parseHtmlLikeString(string)
}

fun parseHtmlLikeString(input: String): AnnotatedString {
    return buildAnnotatedString {
        var index = 0
        while (index < input.length) {
            when {
                input.startsWith("<b>", index) -> {
                    // Start bold style
                    val endIndex = input.indexOf("</b>", index)
                    if (endIndex != -1) {
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append(input.substring(index + 3, endIndex))
                        pop() // End bold style
                        index = endIndex + 4
                    } else {
                        append(input.substring(index))
                        break
                    }
                }
                input.startsWith("<i>", index) -> {
                    // Start italic style
                    val endIndex = input.indexOf("</i>", index)
                    if (endIndex != -1) {
                        pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                        append(input.substring(index + 3, endIndex))
                        pop() // End italic style
                        index = endIndex + 4
                    } else {
                        append(input.substring(index))
                        break
                    }
                }
                else -> {
                    // Append normal text
                    append(input[index])
                    index++
                }
            }
        }
    }
}