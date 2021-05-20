package ru.voodster.otuslesson.api

import com.google.gson.annotations.SerializedName

class FilmModel {
    @SerializedName("id")
    var id: Int = 0
    @SerializedName("img")
    var img: String = "empty"
    @SerializedName("title")
    var title: String = "empty"
    @SerializedName("description")
    var description: String = "empty"
    @SerializedName("fav")
    var fav: Boolean = false
    @SerializedName("likes")
    var likes: Int = 0


    constructor() {

    }

    constructor(id:Int, title: String, description: String, img: String, likes: Int, fav:Boolean) {
        this.id = id
        this.title = title
        this.description = description
        this.img = img
        this.likes = likes
        this.fav = fav
    }

}