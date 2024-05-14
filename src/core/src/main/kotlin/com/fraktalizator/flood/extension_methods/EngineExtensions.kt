package com.fraktalizator.flood.extension_methods

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.fraktalizator.flood.EntityAction
import ktx.ashley.MissingEntitySystemException
import kotlin.reflect.KClass

inline fun <reified T : EntityAction?> Engine.getAction(): T? = getSystem(T::class.java)

fun Engine.addAction(actin: EntityAction) = addSystem(actin)

operator fun <T : EntitySystem> Engine.get(type: KClass<T>): T? = getSystem(type.java)

inline fun <reified T : EntitySystem> Engine.getSystem(): T =
    getSystem(T::class.java) ?: throw MissingEntitySystemException(T::class)
