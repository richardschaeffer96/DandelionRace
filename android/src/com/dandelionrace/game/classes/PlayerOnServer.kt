package com.dandelionrace.game.classes

class PlayerOnServer (name: String, mail: String, game:String) {
    val name = name
    val mail = mail
    val game = game
    var posx: Int = 0
    var posy: Int = 0
    var ready: Boolean = false
}