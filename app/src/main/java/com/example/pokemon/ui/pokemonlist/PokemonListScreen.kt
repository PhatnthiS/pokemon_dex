package com.example.pokemon.ui.pokemonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.pokemon.domain.model.Pokemon
import com.example.pokemon.utils.PokemonLoading
import kotlinx.coroutines.delay


@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onPokemonClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
            )
            .padding(top = 48.dp)
    ) {

        when (uiState) {
            is PokemonListUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    PokemonLoading()
                }
            }

            is PokemonListUiState.Error -> {}

            is PokemonListUiState.Success -> {
                val successState = uiState as PokemonListUiState.Success
                val list = successState.filteredList
                val featuredList = successState.featurePokemon

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Title
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = "Recommended Pokémon",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFF44336),
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ), modifier = Modifier
                                .padding(start = 24.dp)
                        )
                    }

                    // Horizontal ScrollView
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        PokemonOfTheDayList(
                            featuredList = featuredList,
                            onPokemonClick = { id -> onPokemonClick(id) },
                        )

                    }

                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Title
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = "Pokémon List",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50),
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            modifier = Modifier
                                .padding(start = 24.dp)
                        )
                    }

                    // Search Bar
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        CustomSearchBar(
                            query = successState.searchQuery,
                            onQueryChange = { viewModel.onSearchQueryChanged(it) }
                        )
                    }

                    // GridView
                    items(list, key = { it.id }) { pokemon ->
                        PokemonCard(pokemon) { onPokemonClick(pokemon.id) }
                    }
                }
            }
        }
    }

}

@Composable
fun PokemonCard(pokemon: Pokemon, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = { onClick() }
            ),
        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(80.dp),

                imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components { add(SvgDecoder.Factory()) }
                    .build(),
            )
            Text(
                pokemon.name,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PokemonOfTheDayList(
    featuredList: List<Pokemon>,
    onPokemonClick: (String) -> Unit
) {
    val listState = rememberLazyListState()
    var scrollDirection by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(featuredList) {
        if (featuredList.isNotEmpty()) {
            while (true) {
                listState.scrollBy(scrollDirection * 3f)
                delay(16)

                if (!listState.canScrollForward) {
                    scrollDirection = -1f
                } else if (!listState.canScrollBackward) {
                    scrollDirection = 1f
                }
            }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(featuredList, key = { it.id }) { pokemon ->
            PokemonCard(pokemon) { onPokemonClick(pokemon.id) }
        }
    }
}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search Pokémon") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .background(Color.White, RoundedCornerShape(24.dp)),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}
