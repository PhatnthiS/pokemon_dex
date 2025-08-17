package com.example.pokemon.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Pokemon(
    val id: String,
    val name: String,
    val imageUrl: String,
): Parcelable
