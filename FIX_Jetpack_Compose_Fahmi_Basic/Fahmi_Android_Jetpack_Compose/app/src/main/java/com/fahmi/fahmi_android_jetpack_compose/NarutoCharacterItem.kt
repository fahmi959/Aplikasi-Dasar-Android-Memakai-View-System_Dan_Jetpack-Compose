package com.fahmi.fahmi_android_jetpack_compose


class NarutoCharacterItem(private val character: NarutoCharacter) {
    val name: String
        get() = character.name

    val ninjaId: String
        get() = character.ninjaId

    val imageUrl: String
        get() = character.imageUrl

    val description_char: String
        get() = character.description_char
}