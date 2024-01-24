package com.example.myapplication

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun Settings(
    onNavigateToMessages: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Settings section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dark Theme",
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Open the theme settings if needed */ }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Theme settings")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Button to navigate back to Messages
        Button(onClick = onNavigateToMessages) {
            Text("Messages")
        }
    }
}

@Preview
@Composable
fun PreviewSettings() {
    MyApplicationTheme {
        Settings(onNavigateToMessages = {})
    }
}
