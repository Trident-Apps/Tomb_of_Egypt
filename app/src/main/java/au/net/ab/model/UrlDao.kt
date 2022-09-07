package au.net.ab.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UrlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUrl(url: Url)

    @Query("SELECT * FROM Url LIMIT 1")
    fun getUrl(): LiveData<Url>
}