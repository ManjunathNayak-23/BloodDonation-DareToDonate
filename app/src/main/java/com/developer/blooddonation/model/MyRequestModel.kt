package com.developer.blooddonation.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class MyRequestModel {
    var name:String?=null
    var bloodRequest:String?=null
    var timeStamp:String?=null
    var key:String?=null
    var hisUid:String?=null


    constructor() {

    }

    constructor(name:String,bloodRequest:String,timeStamp:String,key:String,hisUid:String){
        this.name=name
        this.bloodRequest=bloodRequest
        this.timeStamp=timeStamp
        this.key=key
        this.hisUid=hisUid
    }
}