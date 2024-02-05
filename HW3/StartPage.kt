package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import java.io.File

@Composable
fun StartingPage(
    db: AppDatabase,
    onNavigateToChat: () -> Unit
) {

    var userName: String by remember {
        mutableStateOf(if (db.dao.getRowCount() > 0) db.dao.getUser().userName else "")
    }

    var selectedImageUri: String by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val imageFile by remember { mutableStateOf(File(context.filesDir, "selectedImage")) }

    if (imageFile.exists() && imageFile.readBytes().toString() != "") {
        selectedImageUri = imageFile.toUri().toString()
    }

    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            // Process the selected image URI
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri.toString()

            // Save the image to internal storage
            val inputStream = context.contentResolver.openInputStream(uri)
            val outputStream = context.openFileOutput("selectedImage", Context.MODE_PRIVATE)
            inputStream?.use { input ->
                outputStream?.use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    Column(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background)
                )
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = if (db.dao.getRowCount() > 0) {
                "Hello, \n${db.dao.getUser().userName}"
            } else {
                "Welcome"
            },
            fontSize = 32.sp,
            lineHeight = 80.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.fillMaxHeight(0.6f))

        Row(
            Modifier.padding(top = 10.dp),
            Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .size(120.dp)
                    .clickable {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center,
            ) {
                if (selectedImageUri != "") {
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = "add",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp)
                    )
                }
            }

            TextField(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 8.dp)
                    .width(220.dp),
                value = userName,
                onValueChange = { userName = it },
                label = { Text(text = "Enter your name") },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
            ClickableText(
                text = AnnotatedString("Go"),
                onClick = {
                    if (userName != "") onNavigateToChat()
                    db.dao.addUser(User(userName))
                },
                style = TextStyle(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.padding(8.dp)
            )
        }
        if (selectedImageUri == "") {
            Text(text = "Choose a profile picture!\"")
        }

        if (userName == "") {
            Text(text = "Enter your username!")
        }
    }
}
