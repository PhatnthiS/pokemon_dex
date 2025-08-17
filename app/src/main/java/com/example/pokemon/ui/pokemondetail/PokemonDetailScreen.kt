package com.example.pokemon.ui.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.pokemon.domain.model.PokemonTypeSlot
import com.example.pokemon.utils.PokemonLoading
import com.example.pokemon.utils.decimeterToMeter
import com.example.pokemon.utils.getSpriteUrl
import com.example.pokemon.utils.hectogramToKg
import com.example.pokemon.utils.popBackStackSafely

@Composable
fun PokemonDetailScreen(
    navController: NavController,
    viewModel: PokemonDetailViewModel,
    pokemonId: String,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    when (uiState) {
        is PokemonDetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                PokemonLoading()
            }
        }

        is PokemonDetailUiState.Error ->
            ErrorScreen(
                message = (uiState as PokemonDetailUiState.Error).message,
                onBackClick = { navController.popBackStackSafely() }
            )

        is PokemonDetailUiState.Success -> {
            val successState = uiState as PokemonDetailUiState.Success
            val pokemon = successState.detail
            val specie = successState.specie

            val primaryColor = pokemon.types.first().type.typeEnum.color
            val secondaryColor = if (pokemon.types.size > 1) {
                pokemon.types.last().type.typeEnum.color
            } else {
                Color.White
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(primaryColor, secondaryColor),
                            start = Offset.Zero,
                            end = Offset.Infinite

                        )
                    )
            ) {
                // Back button
                BackIconButton(onClick = { navController.popBackStackSafely() })

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                ) {
                    Spacer(modifier = Modifier.height(48.dp))

                    // Image
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        AsyncImage(
                            model = getSpriteUrl(pokemonId),
                            contentDescription = pokemon.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f),
                            imageLoader = ImageLoader.Builder(LocalContext.current)
                                .components { add(SvgDecoder.Factory()) }.build(),
                        )
                    }

                    // Switch Button
//                    SwitchButton(
//                        modifier = Modifier
//                            .align(Alignment.End)
//                            .padding(end = 36.dp),
//                        onClick = {})

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy((-48).dp),
                    ) {
                        // Name
                        PokemonNameBanner(pokemon.name, primaryColor, secondaryColor)

                        // Rounded container content
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = Color.Transparent.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .border(
                                    width = 2.dp,
                                    color = primaryColor,
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .padding(bottom = 48.dp)

                        ) {
                            // Column content
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(
                                        text = specie.flavorText.replace("\n", ""),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(top = 48.dp)
                                    )

                                    // Type Info
                                    PokemonTypeInfo(pokemon.exp, pokemon.types)
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                // Information
                                PokemonInfoRow(
                                    height = pokemon.height,
                                    weight = pokemon.weight,
                                    habitat = specie.habitat,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "STAT",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Stat
                                pokemon.stats.forEach { stat ->
                                    StatRow(stat.name, stat.value, primaryColor)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BackIconButton(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 48.dp)
            .size(48.dp)
            .background(
                color = Color.White.copy(alpha = 0.7f),
                shape = CircleShape
            )
            .zIndex(1f)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = Color.Black
        )
    }
}


@Composable
fun PokemonNameBanner(
    name: String,
    primaryColor: Color,
    secondaryColor: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = primaryColor,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 48.dp,
                    bottomEnd = 48.dp,
                    bottomStart = 0.dp
                )
            )
            .zIndex(1f)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = secondaryColor,
                    offset = Offset(2f, 2f),
                    blurRadius = 4f
                ),

                ),
            color = Color.White,
        )
    }
}


@Composable
fun PokemonTypeInfo(
    exp: Int,
    types: List<PokemonTypeSlot>
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Experience",
                tint = Color.Red
            )
            Text(
                text = exp.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }

        types.forEach { pokemonType ->
            Box(
                modifier = Modifier.clip(RoundedCornerShape(36.dp))
            ) {
                AsyncImage(
                    model = pokemonType.type.url,
                    contentDescription = pokemonType.type.typeEnum.displayName,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}


@Composable
fun PokemonInfoRow(
    height: Int,
    weight: Int,
    habitat: String?,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min),

        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        InfoColumn(
            title = "Height",
            value = decimeterToMeter(height)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = Color.LightGray
        )

        InfoColumn(
            title = "Weight",
            value = hectogramToKg(weight)
        )

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp),
            color = Color.LightGray
        )

        InfoColumn(
            title = "Habitat",
            value = habitat?.replaceFirstChar { it.uppercase() } ?: "-"
        )
    }
}


@Composable
fun InfoColumn(
    title: String,
    value: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            value,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun StatRow(
    statName: String,
    statValue: Int,
    color: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = statName,
            color = Color.White,
            modifier = Modifier.width(60.dp)
        )
        Text(
            text = statValue.toString(),
            color = Color.White,
            modifier = Modifier.width(40.dp)
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
                .background(
                    Color.Transparent.copy(alpha = 0.1f),
                    RoundedCornerShape(8.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(statValue / 100f)
                    .background(
                        color,
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun ErrorScreen(
    message: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFFFFF8DC),
                        Color(0xFFC8E6C9),
                        Color(0xFFB3E5FC),
                    ),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Error !!",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onBackClick,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 2.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F),
                    contentColor = Color.White
                )
            ) {
                Text("Back")
            }
        }
    }
}



