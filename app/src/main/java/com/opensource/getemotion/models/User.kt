package com.opensource.getemotion.models

class User {

    var id : String = ""
    var name : String = ""
    var email : String = ""
    var password : String = ""
    var sex : String = ""
    var age : Int = 0
    var dominantFinger : String = ""
    var dominantHand : String = ""


    constructor() {}

    constructor(
        name : String,
        email : String,
        password : String,
        sex : String,
        age : Int,
        dominantFinger : String,
        dominantHand : String
    ) : this() {

        this.name = name
        this.email = email
        this.password = password
        this.sex = sex
        this.age = age
        this.dominantFinger = dominantFinger
        this.dominantHand = dominantHand
    }




}