package com.developer.blooddonation.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val uid:String?=null,val username: String? = null, val email: String? = null,val bloodGroup:String?=null,val city:String?=null,val phoneNumber:String?=null,val donated:Int?=null,val requested:Int?=null,val readyToDonate:Boolean?=null,val notificationToken:String?=null) {

}