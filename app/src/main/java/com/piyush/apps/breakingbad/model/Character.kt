package com.piyush.apps.breakingbad.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created By Piyush Pandey on 04-07-2020
 * Apps@Piyush
 */
@Parcelize
data class Character(
    val charId : Int,
    val name : String,
    val birthday : String,
    val img : String,
    val portrayed : String,
    val status : String,
    val nickname : String,
    val category : String,
    val appearance : List<Int>,
    val occupation : List<String>
) : Parcelable