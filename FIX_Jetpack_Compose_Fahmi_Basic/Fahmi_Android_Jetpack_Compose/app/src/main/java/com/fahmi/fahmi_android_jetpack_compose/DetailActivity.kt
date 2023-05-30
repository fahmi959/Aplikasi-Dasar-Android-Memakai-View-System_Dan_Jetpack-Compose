package com.fahmi.fahmi_android_jetpack_compose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.fahmi.fahmi_android_jetpack_compose.ui.theme.Fahmi_Android_Jetpack_ComposeTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ninjaId = intent.getStringExtra("NINJA_ID") ?: ""
        val detail = narutoCharacters.firstOrNull { it.ninjaId == ninjaId }
        if (detail == null) {
            // Handle error condition
            Toast.makeText(this, "Ninja not found", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Display the detail screen
            setContent {
                Fahmi_Android_Jetpack_ComposeTheme {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                        NarutoCharacterScreen(detail)
                    }
                }
            }
        }
    }
}



@Composable
fun NarutoCharacterScreen(narutoCharacter: NarutoCharacter) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)

    ) {
        Image(
            painter = rememberImagePainter(narutoCharacter.imageUrl),
            contentDescription = narutoCharacter.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    clip = true
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Text(
                text = narutoCharacter.name,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(bottom = 8.dp)
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = true
                    )
            )

            Text(
                text = "Description",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = narutoCharacter.description_char,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Appearance",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = narutoCharacter.appearance,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Abilities",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = narutoCharacter.abilities,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Trivia",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = narutoCharacter.trivia,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}





@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    val narutoCharacter = NarutoCharacter(
        name = "Naruto Uzumaki",
        ninjaId = "838",
        description_char = "A young ninja who dreams of becoming the Hokage.",
        imageUrl = "https://example.com/naruto.jpg",
        trivia = "asd",
        abilities = "KJSIDHSINC",
    appearance = "KUATTT"
    )
    Fahmi_Android_Jetpack_ComposeTheme {
        NarutoCharacterScreen(narutoCharacter)
    }
}