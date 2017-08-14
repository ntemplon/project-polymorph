package com.polymorph.game.entity

import com.badlogic.ashley.core.Component

class SizeComponent(var dimension: Dimension): Component

data class Dimension(val width: Int, val height: Int)