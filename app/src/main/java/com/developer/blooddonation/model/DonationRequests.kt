package com.developer.blooddonation.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class DonationRequests {
    var bloodRequest: String? = null
    var uid: String? = null
    var location: String? = null
    var name: String? = null
    var timeStamp: String? = null
    var mobile: String? = null
    var note: String? = null
    var accepted: Boolean? = null
    var key: String? = null

    constructor() {

    }

    constructor(
        bloodRequest: String,
        uid: String,
        location: String,
        name: String,
        timeStamp: String,
        mobile: String,
        note: String,
        accepted: Boolean,
        key: String
    ) {
        this.bloodRequest = bloodRequest
        this.uid = uid
        this.location = location
        this.name = name
        this.timeStamp = timeStamp
        this.mobile = mobile
        this.note = note
        this.accepted = accepted
        this.key = key
    }
}
