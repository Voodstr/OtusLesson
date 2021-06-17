package ru.voodster.otuslesson.db

import androidx.room.*

@Dao
interface FilmDao {

   // @Query("SELECT * FROM films_table ")
   // fun getAll(): List<FilmModel>


    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT 10")
    fun getInitial():List<FilmEntity>

    @Query("SELECT * FROM films_table ORDER BY rowID ASC LIMIT :size OFFSET :start")
    fun getRange(start:Int, size:Int):List<FilmEntity>

    //@Update(entity = FilmModel::class)
   // fun updateFilm(film: FilmModel)

   // @Query("Select t.rowID,t.fav,t.id,t.img,t.title,t.description,t.likes from films_table t inner join user_favorites f on t.id = f.filmID order by t.id ")
   // fun getFilmsJoinFavorites():List<FilmModel>

    @Query("Select filmID from user_favorites order by filmID")
    fun getUserFavorites():List<UserFavorites>

    //@Query("delete from user_favorites where filmID = :id")
   // fun rmFav(id: Int)

    //@Query("Insert into user_favorites values (null,:id)")
    //fun addFav(id: Int)

    //@Insert(entity = UserFavorites::class)
    //fun addFav2(fav: UserFavorites)


    @Query("DELETE FROM user_favorites")
    fun deleteAllFavorites()
//entity = UserFavorites::class,
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: List<UserFavorites>)

    @Query("DELETE FROM films_table")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg films: FilmEntity)

    @Delete
    fun delete(film: FilmEntity)

}