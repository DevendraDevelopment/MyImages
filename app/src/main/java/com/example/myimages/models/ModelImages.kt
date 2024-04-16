package com.example.myimages.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "model_images")
@TypeConverters(ThumbnailTypeConverter::class, BackupDetailsTypeConverter::class)
@Serializable
data class ModelImages (
  @PrimaryKey
  @SerialName("id"            ) var id            : String         = "0",
  @SerialName("title"         ) var title         : String?        = null,
  @SerialName("language"      ) var language      : String?        = null,
  @SerialName("thumbnail"     ) var thumbnail     : Thumbnail?     = Thumbnail(),
  @SerialName("mediaType"     ) var mediaType     : Int?           = null,
  @SerialName("coverageURL"   ) var coverageURL   : String?        = null,
  @SerialName("publishedAt"   ) var publishedAt   : String?        = null,
  @SerialName("publishedBy"   ) var publishedBy   : String?        = null,
  @SerialName("backupDetails" ) var backupDetails : BackupDetails? = BackupDetails()
)


class ThumbnailTypeConverter {
  private val json = Json { ignoreUnknownKeys = true }

  @TypeConverter
  fun fromThumbnail(thumbnail: Thumbnail?): String? {
    return thumbnail?.let { json.encodeToString(thumbnail) }
  }

  @TypeConverter
  fun toThumbnail(thumbnailString: String?): Thumbnail? {
    return thumbnailString?.let { json.decodeFromString(it) }
  }
}

class BackupDetailsTypeConverter {
  private val json = Json { ignoreUnknownKeys = true }

  @TypeConverter
  fun fromBackupDetails(backupDetails: BackupDetails?): String? {
    return backupDetails?.let { json.encodeToString(backupDetails) }
  }

  @TypeConverter
  fun toBackupDetails(backupDetailsString: String?): BackupDetails? {
    return backupDetailsString?.let { json.decodeFromString(it) }
  }
}