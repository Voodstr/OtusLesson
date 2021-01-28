package ru.voodster.otuslesson
// По сути замена базы данных, чтобы добавлять фильмы сюда
// Думал запихунуть весь список в ScrollView и LinearLayout
// методом forEach вышло некрасиво и сложно. Теперь узнал про RecyclerView
// буду переделывать через него, а этот сделаю в тупую...
object Constants {
    fun sampleList():ArrayList<FilmItem>{
        val filmList = ArrayList<FilmItem>()
        filmList.add(FilmItem(
            1,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года.")
        )
        filmList.add(FilmItem(
            2,
            R.drawable.ic_inception,
            "Inception",
            " Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        filmList.add(FilmItem(
            3,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        filmList.add(FilmItem(
            1,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года.")
        )
        filmList.add(FilmItem(
            2,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        filmList.add(FilmItem(
            3,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        filmList.add(FilmItem(
            1,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года")
        )
        filmList.add(FilmItem(
            2,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        filmList.add(FilmItem(
            3,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        filmList.add(FilmItem(
            1,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года")
        )
        filmList.add(FilmItem(
            2,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        filmList.add(FilmItem(
            3,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )

        return filmList
    }
}