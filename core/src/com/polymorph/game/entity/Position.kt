package com.polymorph.game.entity

import com.badlogic.ashley.core.Component

class PositionComponent(var position: Location): Component

data class Location(val x: Int, val y: Int)