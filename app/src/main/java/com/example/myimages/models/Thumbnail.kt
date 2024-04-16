package com.example.myimages.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail (
  @SerialName("id"          ) var id          : String?        = null,
  @SerialName("version"     ) var version     : Int?           = null,
  @SerialName("domain"      ) var domain      : String?        = null,
  @SerialName("basePath"    ) var basePath    : String?        = null,
  @SerialName("key"         ) var key         : String?        = null,
  @SerialName("qualities"   ) var qualities   : ArrayList<Int> = arrayListOf(),
  @SerialName("aspectRatio" ) var aspectRatio : Int?           = null
)
