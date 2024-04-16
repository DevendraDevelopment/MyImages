package com.example.myimages.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackupDetails (
  @SerialName("pdfLink"       ) var pdfLink       : String? = null,
  @SerialName("screenshotURL" ) var screenshotURL : String? = null
)