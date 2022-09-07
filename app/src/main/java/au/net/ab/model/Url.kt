package au.net.ab.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Url(
    @PrimaryKey(autoGenerate = true) val uid: Int = 1,
    val url: String = ""
)