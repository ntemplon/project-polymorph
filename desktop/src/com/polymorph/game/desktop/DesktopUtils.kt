package com.polymorph.game.desktop

import javax.swing.JFrame

fun JFrame.maximize() {
    this.extendedState = (this.extendedState or JFrame.MAXIMIZED_BOTH)
}