package com.example.app.entity

//val user = User("aaa","bbb", "ccc")
//val codeView = CodeView
//val str: String? = CacheUtils.get("test")
val user = User("aaa", "bbb", "ccc")
fun main() {
        val copy = user.copy()
        println(user == copy)
        println(user === copy)
}