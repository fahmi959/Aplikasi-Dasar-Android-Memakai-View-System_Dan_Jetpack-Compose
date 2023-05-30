package com.fahmi.fahmi_android_jetpack_compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fahmi.fahmi_android_jetpack_compose.ui.theme.Fahmi_Android_Jetpack_ComposeTheme

class AboutAplikasiActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fahmi_Android_Jetpack_ComposeTheme {
                AboutScreen(onBackPressed = { navigateToMainActivity(this@AboutAplikasiActivity) })
            }
        }
        }
    }

fun navigateToMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}


@Composable
fun AboutScreen(onBackPressed: () -> Unit) {


    TopAppBar(
        title = { Text(text = "Tentang Developer") },
        navigationIcon = {
            IconButton(onClick = { onBackPressed.invoke() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )



    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val imageModifier = Modifier
                .size(300.dp)
                .clip(CircleShape)
            Image(
                painter = painterResource(R.drawable.fahmi_foto),
                contentDescription = "Developer Photo",
                modifier = imageModifier,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Fahmi Ardiansyah",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "fahmiardiansyah959@students.unnes.ac.id",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    Fahmi_Android_Jetpack_ComposeTheme {
        AboutScreen(onBackPressed = { })
    }
}