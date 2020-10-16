package project.dheeraj.newsup2.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Dheeraj Kotwani on 16-10-2020.
 */
@Entity(tableName = "bookmark")
class BookmarkModel(
    var author: String? = "",
    var name: String? = "",
    var title: String? = "",
    var description: String? = "",
    var url: String? = "",
    var urlToImage: String? = "",
    var publishedAt: String? = "",
    var content: String? = ""
){
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
}