package com.udacity.project4.remindlist

import java.io.Serializable
import java.util.*


data class RemindDataItem(
    var title: String?,
    var description: String?,
    var location: String?,
    var latitude: Double?,
    var longitude: Double?,
    val id: String = UUID.randomUUID().toString()
) : Serializable