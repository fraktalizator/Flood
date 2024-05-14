package com.fraktalizator.flood

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem

//temporary solution until I manage to get my https://github.com/fraktalizator/ashleyWithActions on maven repo
abstract class EntityAction : EntitySystem() {
    open fun act(entity: Entity) {}
}
