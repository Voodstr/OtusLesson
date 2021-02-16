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
            true,10)
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
            R.drawable.ic_shawshank,
            "The Shawshank Redemption",
            "Фильм основанный на повести Стивена Кинга «Рита Хейуорт и спасение из Шоушенка».")
        )
        this.add(
            FilmItem(
            4,
            R.drawable.ic_batman,
            "The Dark Knight",
            "Супергеройский боевик с элементами неонуара режиссёра Кристофера Нолана.")
        )
        this.add(
            FilmItem(
            5,
            R.drawable.ic_godfather,
            "The Godfather",
            "Эпическая гангстерская драма режиссёра Фрэнсиса Форда Копполы.")
        )
        this.add(
            FilmItem(
            6,
            R.drawable.ic_pulp,
            "Pulp Fiction",
            "Мультивселенный криминальный мир от режиссера Квентина Тарантино")
        )
        this.add(
            FilmItem(
            7,
            R.drawable.ic_fight,
            "Fight Club",
            "Кинофильм режиссёра Дэвида Финчера по мотивам одноимённого романа Чака Паланика")
        )
        this.add(
            FilmItem(
            8,
            R.drawable.ic_forrest,
            "Forrest Gump",
            "Драма, девятый полнометражный фильм режиссёра Роберта Земекиса.")
        )
        this.add(
            FilmItem(
            9,
            R.drawable.ic_matrix,
            "Matrix",
            "Американо-авcтpалийcкий научно-фантастический боевик, снятый братьями Вачовски.")
        )
        this.add(
            FilmItem(
            10,
            R.drawable.ic_cucuwka,
            "One Flew Over the Cuckoo’s Nest",
            "Художественный фильм-драма кинорежиссёра Милоша Формана")
        )
        this.add(
            FilmItem(
            11,
            R.drawable.ic_lambs,
            "The Silence of the Lambs",
            "Американский триллер 1991 года режиссёра Джонатана Демми, снятый по мотивам одноимённого романа Томаса Харриса")
        )
    }

}