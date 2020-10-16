package project.dheeraj.newsup2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import project.dheeraj.newsup2.db.dao.BookmarkDao

/**
 * Created by Dheeraj Kotwani on 16-10-2020.
 */
@Database(entities = [BookmarkModel::class], version = 1 )
abstract class BookmarkDatabase(): RoomDatabase() {

    abstract fun bookmarkDao() : BookmarkDao

    companion object{
        @Volatile private var instance : BookmarkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            BookmarkDatabase::class.java,
            "bookmark"
        ).allowMainThreadQueries().build()
    }


}