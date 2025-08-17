package com.example.pokemon.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource


fun getSpriteUrl(id: String): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

fun getIdFromUrl(url: String): String {
    return url.trimEnd('/').split("/").last()
}


fun getDisplayStat(statName: String): String {
    return mapOf(
        "hp" to "HP",
        "attack" to "ATK",
        "defense" to "DEF",
        "special-attack" to "SP. ATK",
        "special-defense" to "SP. DEF",
        "speed" to "SPD"
    )[statName] ?: statName
}

fun getSpriteBadgeUrl(id: String): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/types/generation-viii/sword-shield/$id.png"
}

@SuppressLint("DefaultLocale")
fun decimeterToMeter(decimeters: Int): String {
    val meters = decimeters * 0.1
    return String.format("%.2f m", meters)
}

@SuppressLint("DefaultLocale")
fun hectogramToKg(hectograms: Int): String {
    val kg = hectograms * 0.1
    return String.format("%.1f kg", kg)
}

@Composable
fun PokemonLoading() {
    DotLottieAnimation(
        source = DotLottieSource.Asset("pokeball_loading.lottie"),
        autoplay = true,
        loop = true,
        speed = 3f,
        useFrameInterpolation = true,

        )
}

fun NavController.navigateSafely(safely: Boolean = true, navigate: NavController.() -> Unit) {
    val state = currentBackStackEntry?.lifecycle?.currentState ?: Lifecycle.State.RESUMED
    if (!safely || state == Lifecycle.State.RESUMED) {
        navigate()
    }
}

fun NavController.popBackStackSafely(safely: Boolean = true, destinationId: Int? = null, inclusive: Boolean = false): Boolean {
    val state = currentBackStackEntry?.lifecycle?.currentState ?: Lifecycle.State.RESUMED
    return if (!safely || state == Lifecycle.State.RESUMED) {
        if (destinationId != null) {
            popBackStack(destinationId, inclusive)
        } else {
            popBackStack()
        }
    } else {
        false
    }
}

