package com.polymorph.game.entity

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

class CollisionComponent(val collides: Boolean = true) : Component

val Entity.collides: Boolean
    get() = if (EntityEngine.Families.collision.matches(this)) {
        EntityEngine.Mappers.collision[this].collides
    } else {
        false
    }