package com.polymorph.game.entity

import com.badlogic.ashley.core.*
import java.util.*

/**
 * Copyright (c) 2017 Nathan S. Templon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
object IdSystem: EntitySystem(), EntityListener {
    private val lookup = mutableMapOf<UUID, Entity>()

    override fun addedToEngine(engine: Engine) {
        engine.addEntityListener(EntityEngine.Families.id, this)
    }

    override fun removedFromEngine(engine: Engine) {
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        lookup[entity.id] = entity
    }

    override fun entityRemoved(entity: Entity) {
        // This is called after the component has already been removed from the entity, so we can't just look it up
        val id = lookup.entries.filter { it.value === entity }.first().key
        lookup.remove(id)
    }
}

class IdComponent(val id: UUID) : Component {
    companion object {
        fun random(): IdComponent = IdComponent(UUID.randomUUID())
    }
}