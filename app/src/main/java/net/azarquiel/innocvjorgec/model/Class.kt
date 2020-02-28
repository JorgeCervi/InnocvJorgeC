package net.azarquiel.innocvjorgec.model

import java.io.Serializable

data class User (
    var name: String,
    var birthdate: String,
    var id: Int
): Serializable

data class Respuesta (
    val user: User,
    val users: List<User>
)

