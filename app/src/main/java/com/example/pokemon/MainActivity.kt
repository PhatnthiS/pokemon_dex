package com.example.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemon.data.remote.RetrofitInstance
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.domain.usecase.GetPokemonDetailUseCase
import com.example.pokemon.domain.usecase.GetPokemonSpeciesUseCase
import com.example.pokemon.domain.usecase.GetPokemonUseCase
import com.example.pokemon.ui.PokemonDetailViewModelFactory
import com.example.pokemon.ui.PokemonListViewModelFactory
import com.example.pokemon.ui.pokemondetail.PokemonDetailScreen
import com.example.pokemon.ui.pokemondetail.PokemonDetailViewModel
import com.example.pokemon.ui.pokemonlist.PokemonListScreen
import com.example.pokemon.ui.pokemonlist.PokemonListViewModel
import com.example.pokemon.utils.navigateSafely


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = PokemonRepository(RetrofitInstance.api)
        val getPokemonUseCase = GetPokemonUseCase(repository)
        val getPokemonDetailUseCase = GetPokemonDetailUseCase(repository)
        val getPokemonSpeciesUseCase = GetPokemonSpeciesUseCase(repository)



        setContent {
            val navController = rememberNavController()
            val pokemonListViewModel: PokemonListViewModel = viewModel(
                factory = PokemonListViewModelFactory(getPokemonUseCase)
            )
            val pokemonDetailViewModel: PokemonDetailViewModel = viewModel(
                factory = PokemonDetailViewModelFactory(
                    getPokemonDetailUseCase,
                    getPokemonSpeciesUseCase
                )
            )

            NavHost(navController, startDestination = "list") {
                composable("list") {
                    PokemonListScreen(pokemonListViewModel) { pokemonId ->
                        navController.navigateSafely { navigate("detail/$pokemonId") }

                    }
                }
                composable(
                    route = "detail/{pokemonId}",
                    arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
                    PokemonDetailScreen(navController, pokemonDetailViewModel, pokemonId)
                }

            }
        }
    }
}
