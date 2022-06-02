package com.bws.musclefood.login.tbl

data class ActivityMaster(
    val Active: Boolean,
    val ActivityName: String,
    val CreatedBy: String,
    val CreatedOn: String,
    val ID: Int,
    val Level: Int,
    val ParentID: Int
)