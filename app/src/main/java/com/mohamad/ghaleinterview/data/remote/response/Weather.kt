package com.mohamad.ghaleinterview.data.remote.response

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)