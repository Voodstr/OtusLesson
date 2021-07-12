package ru.voodster.otuslesson
abstract class BaseMapper<Entity, Model>() {

    abstract fun reverseMap(entity: Entity?) : Model?

    abstract fun map(model: Model?) : Entity?

    fun reverseMap(entityList: List<Entity>) : List<Model> {
        val modelList = arrayListOf<Model>()
        entityList.forEach {
            reverseMap(it)?.let {
                modelList.add(it)
            }
        }
        return modelList
    }

    fun map(modelList: List<Model>) : List<Entity> {
        val entityList = arrayListOf<Entity>()
        modelList.forEach {
            map(it)?.let {
                entityList.add(it)
            }
        }
        return entityList
    }
}