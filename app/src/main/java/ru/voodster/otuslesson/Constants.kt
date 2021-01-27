package ru.voodster.otuslesson
// По сути замена базы данных, чтобы добавлять фильмы сюда
// Думал запихунуть весь список в ScrollView и LinearLayout
// методом forEach вышло некрасиво и сложно. Теперь узнал про RecyclerView
// буду переделывать через него, а этот сделаю в тупую...
object Constants {
    fun getFilmList():ArrayList<Film>{
        val filmList = ArrayList<Film>()
        val film1 = Film(
            1,
            R.drawable.ic_inbruges,
            "In Bruges",
            "In Bruges",
            4
        )
        filmList.add(film1)
        val film2 = Film(
            2,
            R.drawable.ic_inception,
            "Inception",
            "Inception",
            1
        )
        filmList.add(film2)
        val film3 = Film(
            3,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Lord of the rings",
            1
        )
        filmList.add(film3)
        return filmList
    }
}