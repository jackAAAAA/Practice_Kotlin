package com.example.app.entity

class User(var username: String?, var password: String?, var code: String?) {
    constructor() : this(null, null, null)
}