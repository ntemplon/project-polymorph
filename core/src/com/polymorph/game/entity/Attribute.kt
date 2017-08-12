package com.polymorph.game.entity

import com.badlogic.ashley.core.Component
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
sealed class Attribute {
    val modifiers = AttributeModifierCollection()
    val value: Int
        get() {
            var currentValue = baseValue
            for(modifier in modifiers) {
                currentValue = modifier.modifier(currentValue)
            }
            return currentValue
        }

    abstract val baseValue: Int

    private class FixedAttribute(value: Int): Attribute() {
        private val _value: Int = value

        override val baseValue: Int
            get() = _value
    }

    private class DerivedAttribute(private val valueFunction: () -> Int): Attribute() {
        override val baseValue: Int
            get() = valueFunction()
    }

    companion object {
        val HitPoints: String = "HIT_POINTS"
        val Level: String = "LEVEL"

        val Strength: String = "STRENGTH"
        val Dexterity: String = "DEXTERITY"
        val Constitution: String = "CONSTITUTION"
        val Intelligence: String = "INTELLIGENCE"
        val Wisdom: String = "WISDOM"
        val Charisma: String = "CHARISMA"

        fun fixed(value: Int): Attribute = FixedAttribute(value)
        fun derived(valueFunction: () -> Int): Attribute = DerivedAttribute(valueFunction)
    }

    class AttributeModifier(val priority: Int, val modifier: (Int) -> Int): Comparable<AttributeModifier> {
        override fun compareTo(other: AttributeModifier): Int {
            return this.priority - other.priority
        }

        companion object {
            fun additive(additive: Int) = AttributeModifier(Int.MAX_VALUE) { it + additive }
        }
    }

    class AttributeModifierCollection: Collection<AttributeModifier> {
        private val _internalList = mutableListOf<AttributeModifier>()

        override val size: Int
            get() = _internalList.size

        override fun contains(element: AttributeModifier): Boolean = _internalList.contains(element)

        override fun containsAll(elements: Collection<AttributeModifier>): Boolean = _internalList.containsAll(elements)

        override fun isEmpty(): Boolean = _internalList.isEmpty()

        fun add(element: AttributeModifier): Boolean {
            val index = Collections.binarySearch(_internalList, element)
            val insertionIndex = if (index > 0) index else index.inv()
            _internalList.add(insertionIndex, element)
            return true
        }

        fun clear() = _internalList.clear()

        override fun iterator(): Iterator<AttributeModifier> = _internalList.iterator()

        fun remove(element: AttributeModifier): Boolean = _internalList.remove(element)
    }
}

class AttributeComponent: Component {
    val attributes = mutableMapOf<String, Attribute>()
}