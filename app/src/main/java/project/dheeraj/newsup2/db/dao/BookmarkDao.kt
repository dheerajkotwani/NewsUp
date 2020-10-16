package project.dheeraj.newsup2.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import project.dheeraj.newsup2.db.BookmarkModel

/**
 * Created by Dheeraj Kotwani on 16-10-2020.
 */
@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookmark(bookmark: BookmarkModel)

    @Query("SELECT * FROM bookmark")
    fun getBookmarks(): LiveData<List<BookmarkModel>>

    @Query("DELETE FROM bookmark WHERE title=:title")
    fun removeBookmark(title: String)

    @Query("SELECT * FROM bookmark WHERE title=:title")
    fun checkBookmark(title: String): List<BookmarkModel>


}