package com.bws.musclefood.database

class MyExtensionFun {


}

fun MyExtensionFun.myExtenFun2():String{
    return "Extention fun 22222"
}

fun String.myString():String{
    return "1111111111111111111111111111"
}

fun String.removeFirstLastChar(): String =  this.substring(1, this.length - 1)

