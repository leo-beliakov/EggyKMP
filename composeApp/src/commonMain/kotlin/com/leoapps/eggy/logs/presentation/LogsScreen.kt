package com.leoapps.eggy.logs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.leoapps.eggy.base.ui.theme.Amber300
import com.leoapps.eggy.base.ui.theme.Black
import com.leoapps.eggy.base.ui.theme.DeepOrange500
import com.leoapps.eggy.base.ui.theme.Grey400
import com.leoapps.eggy.base.ui.theme.Red700
import com.leoapps.eggy.base.ui.theme.White
import com.leoapps.eggy.common.utils.TimeFormatPattern
import com.leoapps.eggy.common.utils.toFormattedTime
import com.leoapps.eggy.logs.data.model.LogSeverity
import com.leoapps.eggy.logs.domain.model.Log
import com.leoapps.eggy.logs.presentation.model.LogsState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LogsScreen(
    viewModel: LogsViewModel = koinViewModel(),
    onBackClicked: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LogsScreen(
        state = state,
        onBackClicked = onBackClicked,
        onClearLogsClicked = viewModel::onClearLogsClicked,
        onCopyClicked = viewModel::onCopyClicked,
        onFakeTimerToggle = viewModel::onFakeTimerToggle,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LogsScreen(
    state: LogsState,
    onBackClicked: () -> Unit,
    onCopyClicked: () -> Unit,
    onFakeTimerToggle: () -> Unit,
    onClearLogsClicked: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Logs",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Clear") },
                            onClick = {
                                menuExpanded = false
                                onClearLogsClicked()
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Copy") },
                            onClick = {
                                menuExpanded = false
                                onCopyClicked()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = state.fakeTimerEnabled,
                                        onCheckedChange = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("FakeTimer")
                                }
                            },
                            onClick = {
                                menuExpanded = false
                                onFakeTimerToggle()
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (state.logs.isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text(
                    "No logs available",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(state.logs) { log ->
                    LogItem(log)
                }
            }
        }
    }

}

@Composable
private fun LogItem(log: Log) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(
                min = 70.dp,
                max = if (expanded) Dp.Unspecified else 70.dp
            )
            .clickable { expanded = !expanded }
            .background(log.severity.toColor)
            .padding(8.dp)
    ) {
        Text(
            text = log.timestamp.toFormattedTime(TimeFormatPattern.HH_MM_SS),
            style = MaterialTheme.typography.bodySmall,
            color = White,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = log.message,
            style = MaterialTheme.typography.bodySmall,
            color = Black,
            modifier = Modifier.weight(1f)
        )
    }
}

private val LogSeverity.toColor: Color
    get() = when (this) {
        LogSeverity.VERBOSE,
        LogSeverity.DEBUG -> Grey400

        LogSeverity.INFO -> Amber300

        LogSeverity.ERROR,
        LogSeverity.ASSERT -> DeepOrange500

        LogSeverity.WARN -> Red700
    }

