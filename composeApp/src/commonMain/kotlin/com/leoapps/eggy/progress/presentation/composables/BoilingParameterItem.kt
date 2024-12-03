package com.leoapps.progress.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.leoapps.eggy.base.ui.theme.GrayLight
import com.leoapps.eggy.base.ui.theme.Primary
import com.leoapps.eggy.base.ui.theme.White
import com.leoapps.eggy.base.ui.theme.dimens

@Composable
fun BoilingParameterItem(
    painter: Painter,
    title: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceM),
        modifier = Modifier
            .widthIn(max = 400.dp)
            .fillMaxWidth()
    ) {
        Icon(
            painter = painter,
            tint = White,
            contentDescription = null,
            modifier = Modifier
                .size(MaterialTheme.dimens.iconSizeXL)
                .background(Primary, RoundedCornerShape(MaterialTheme.dimens.cornerS))
                .padding(MaterialTheme.dimens.paddingS)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = GrayLight,
            modifier = Modifier.weight(1f, true)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Black,
        )
    }
}