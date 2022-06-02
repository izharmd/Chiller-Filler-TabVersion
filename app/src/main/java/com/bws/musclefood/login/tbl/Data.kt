package com.bws.musclefood.login.tbl


data class Data(
    val ActivityListMaster: List<ActivityListMaster>,
    val ActivityMaster: List<ActivityMaster>,
    val ControlDataMaster: List<ControlDataMaster>,
    val ControlNameMaster: List<ControlNameMaster>,
    val QuestionTypeMaster: List<QuestionTypeMaster>,
    val UserProfileData: UserProfileData,
    val VillageList: List<VillageList>
)