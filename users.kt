package com.example.chatapp

import android.provider.ContactsContract

class users {

    var name:String?=null
    var uid: String?=null
    var email: String?=null


   constructor(){}

    constructor(name:String?,uid:String?,email:String? ){
        this.uid = uid
        this.name = name
        this.email = email
    }

}