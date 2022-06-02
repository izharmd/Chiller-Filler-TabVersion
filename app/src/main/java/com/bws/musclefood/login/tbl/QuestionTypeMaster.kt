package com.bws.musclefood.login.tbl

data class QuestionTypeMaster(
    val CatID: Int,
    val ControlID: Int,
    val Header: String,
    val Link: String,
    val QOrder: String,
    val Qid: Int,
    val Qname: String,
    val Sort: Double,
    val Staus: Int
)