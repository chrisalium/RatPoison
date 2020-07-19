package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.settings.MAX_ENTITIES

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0

typealias EntityList = Object2ObjectArrayMap<EntityType, MutableList<EntityContext>>

var entitiesValues = arrayOfNulls<MutableList<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, MutableList<EntityContext>> = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = mutableListOf<EntityContext>()
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun forEntities(type: EntityType = EntityType.CCSPlayer, crossinline body: (EntityContext) -> Unit) {
	val forEnts = ArrayList(entities[type]!!)

	forEnts.forEach(body)
}