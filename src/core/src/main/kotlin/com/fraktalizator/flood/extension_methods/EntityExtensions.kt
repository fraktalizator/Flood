package com.fraktalizator.flood.extension_methods

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

inline fun <reified T : Component?> Entity.get(): T? = getComponent(T::class.java);
inline fun <reified T : Component?> Entity.remove(): T? = remove(T::class.java);
