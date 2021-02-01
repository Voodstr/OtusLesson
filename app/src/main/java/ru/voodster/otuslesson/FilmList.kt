package ru.voodster.otuslesson

// По сути замена базы данных, чтобы добавлять фильмы сюда
// Думал запихунуть весь список в ScrollView и LinearLayout
// методом forEach вышло некрасиво и сложно. Теперь узнал про RecyclerView
// буду переделывать через него, а этот сделаю в тупую...
object FilmList: ArrayList<FilmItem>() {


    init {
        newSampleList()
    }

    fun  favList():ArrayList<FilmItem>{
        val list = ArrayList<FilmItem>()
        this.forEach{
            if(it.fav){list.add(it)}
        }
        return list
    }


    private fun newSampleList(){
        this.add(
            FilmItem(
            0,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года.",
            true)
        )
        this.add(
            FilmItem(
            1,
            R.drawable.ic_inception,
            "Inception",
            " Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        this.add(
            FilmItem(
            2,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        this.add(
            FilmItem(
            3,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года.")
        )
        this.add(
            FilmItem(
            4,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        this.add(
            FilmItem(
            5,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        this.add(
            FilmItem(
            6,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года")
        )
        this.add(
            FilmItem(
            7,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        this.add(
            FilmItem(
            8,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
        this.add(
            FilmItem(
            9,
            R.drawable.ic_inbruges,
            "In Bruges",
            "Чёрная трагикомедия режиссёра и сценариста Мартина Макдонаха 2008 года")
        )
        this.add(
            FilmItem(
            10,
            R.drawable.ic_inception,
            "Inception",
            "Научно-фантастический триллер Кристофера Нолана, основанный на идее осознанных сновидений")
        )
        this.add(
            FilmItem(
            11,
            R.drawable.ic_lotr,
            "Lord of the rings",
            "Экранизация романа Дж. Р. Р. Толкина «Властелин колец»")
        )
    }

}