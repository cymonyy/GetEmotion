package com.opensource.getemotion.models

import com.google.firebase.Timestamp

class Interaction {

    var clipID : String = ""
    var timestamp : Timestamp? = null
    var confidence : Int = 0

    constructor()

    constructor(dataclipID : String, timestamp : Timestamp, confidence : Int) : this() {
        this.clipID = dataclipID
        this.timestamp = timestamp
        this.confidence = confidence
    }
}