package com.polymorph.game.entity

import com.badlogic.ashley.core.*

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
object EntityEngine {
    private val engine = Engine()

    fun addEntity(op: Entity.() -> Unit): Entity {
        val entity = Entity()
        entity.op()
        engine.addEntity(entity)
        return entity
    }

    fun update(delta: Float) = engine.update(delta)

    object Families {
        val id: Family = Family.all(IdComponent::class.java).get()
        val attribute: Family = Family.all(AttributeComponent::class.java).get()
        val collision: Family = Family.all(CollisionComponent::class.java).get()
    }

    object Mappers {
        val id: ComponentMapper<IdComponent> = ComponentMapper.getFor(IdComponent::class.java)
        val attribute: ComponentMapper<AttributeComponent> = ComponentMapper.getFor(AttributeComponent::class.java)
        val collision: ComponentMapper<CollisionComponent> = ComponentMapper.getFor(CollisionComponent::class.java)
    }
}