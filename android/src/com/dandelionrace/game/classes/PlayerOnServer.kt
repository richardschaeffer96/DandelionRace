package com.dandelionrace.game.classes

class PlayerOnServer (name: String, mail: String, game:String) {
    val name = name
    val mail = mail
    val game = game
    var posx: Float = 0f
    var posy: Float = 0f
    var ready: Boolean = false
}