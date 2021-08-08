package com.example.app.entity

class User @JvmOverloads constructor(val username: String? = null, val password: String? = null, val code: String? = null) {
//    直接在构造参数赋值为null，即可得到空参的构造函数
//    constructor() : this("", "", "")
}
//二元构参不能继承一元的；但是一元的能够继承二元的，通过把其中一参数设置为默认值即可
//class User() : this("", "", "") {
//    constructor(uername: String, password: String, code: String) : this() {
//
//    }
//}