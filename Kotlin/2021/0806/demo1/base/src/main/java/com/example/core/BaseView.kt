package com.example.core

interface BaseView<T> {
    fun getPresenter(): T
}