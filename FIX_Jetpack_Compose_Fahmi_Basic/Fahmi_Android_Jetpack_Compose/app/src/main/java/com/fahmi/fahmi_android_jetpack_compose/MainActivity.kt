package com.fahmi.fahmi_android_jetpack_compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.rememberImagePainter

@Preview(showBackground = true)
@Composable
fun DefaultPreview(modifier: Modifier = Modifier) {
    RecyclerViewJCYTTTheme {
        NarutoCharacterList(narutoCharacters) { character ->

        }
    }
}

@Composable
fun RecyclerViewJCYTTTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NarutoCharacterList(narutoCharacters) { character ->
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("NINJA_ID", character.ninjaId)
                startActivity(intent)
            }
        }
    }
}

@Composable
fun NarutoCharacterItem(
    character: NarutoCharacter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .height(260.dp)
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
//            verticalArrangement = Arrangement.Bottom
                ){
            Image(
                painter = rememberImagePainter(character.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = character.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Ninja ID: ${character.ninjaId}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(8.dp, 8.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NarutoCharacterList(
    characters: List<NarutoCharacter>,
    onCharacterClick: (NarutoCharacter) -> Unit
) { //TAMBAHKAN FUNGSI DISINI
    var searchKeyword by remember { mutableStateOf("") }
    val filteredCharacters = if (searchKeyword.isBlank()) {
        characters
    } else {
        characters.filter { character ->
            character.name.contains(searchKeyword, ignoreCase = true) or
            character.ninjaId.contains(searchKeyword, ignoreCase = true)
        }
    }


    // TAMPILAN NAMBAHIN VIEW ATAU LAYOUT
    Column {


//        TopAppBar(
//            title = {
//                //                Text(text = "Anime Naruto")
//
//                Icon(
//                    painter = painterResource(R.drawable.logo_about),
//                    contentDescription = "Settings Icon",
//                    modifier = Modifier
//                        .clickable {
//                            // Aksi ketika ikon About di klik
//                            // Misalnya membuka halaman About
//                        }
//                        .fillMaxHeight()
//                        .padding(start = 8.dp,
//                            top = 8.dp,
//                            end = 24.dp,
//                            bottom = 8.dp)
//                        .size(30.dp),
//
//                    tint = Color.White
//                )
//
//                TextField(
//
//                    value = searchKeyword,
//                    onValueChange = { searchKeyword = it },
//                    label = {
//                        Text(
//                            "Cari nama atau id",
//                            textAlign = TextAlign.Center,
//                            modifier = Modifier,
//                            color = Color.White
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                        .background(Color.Black),
//
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(R.drawable.logo_search),
//                            contentDescription = "Search Icon" ,
//                            modifier = Modifier
//                                .fillMaxHeight()
//                                .size(24.dp),
//                            tint = Color.White
//                        )
//
//                    },
////                    trailingIcon = {
////                        Icon(
////                            painter = painterResource(R.drawable.logo_about),
////                            contentDescription = "Settings Icon" ,
////                            modifier = Modifier
////                                .size(24.dp),
////                            tint = Color.White
////                        )
////                    }
//
//
//                )
//
//
//
//
//
//
//            }
//
//
//        )

        TopAppBar(
            title = {

                TextField(
                    value = searchKeyword,
                    onValueChange = { searchKeyword = it },
                    label = {
                        Text(
                            "Cari nama atau id",
                            textAlign = TextAlign.Center,
                            modifier = Modifier,
                            color = Color.White
                        )
                    },
                    modifier = Modifier.fillMaxHeight()
                        .background(Color.Black),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.logo_search),
                            contentDescription = "Search Icon" ,
                            modifier = Modifier
                                .fillMaxHeight()
                                .size(24.dp),
                            tint = Color.White
                        )

                    },



                )
                MyComposable()

            }
        )



        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredCharacters.size) { index ->
                NarutoCharacterItem(
                    character = filteredCharacters[index],
                    onClick = { onCharacterClick(filteredCharacters[index]) }
                )
            }
        }
    }
}

@Composable
fun MyComposable() {
    val context = LocalContext.current
    Icon(
        painter = painterResource(R.drawable.logo_about),
        contentDescription = "about_page",
        modifier = Modifier
            .clickable {
                // Aksi ketika ikon About di klik
                // Misalnya membuka halaman About
                val intent = Intent(context, AboutAplikasiActivity::class.java)
                context.startActivity(intent)
            }
            .fillMaxHeight()
            .background(Color.Red)
            .padding(start = 8.dp,
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp)
            .size(50.dp),

        tint = Color.White
    )
}




