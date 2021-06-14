package ru.voodster.otuslesson.api


import com.google.gson.annotations.SerializedName

data class FilmModel (
    @SerializedName("id") var id: Int=0,
    @SerializedName("img") var img: String = "empty",
    @SerializedName("title") var title: String = "empty",
    @SerializedName("description") var description: String = "empty",
    @SerializedName("likes") var likes: Int = 0
){
    constructor() : this(id=0, img="empty", title="empty", description="empty", likes=0)
}