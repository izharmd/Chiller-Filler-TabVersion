package com.bws.musclefood

data class ChemicalElement(
    var name: String,
    var symbol: String,
    var atomicNumber: Int,
    var atomicWeight: Double,
    var nobleMetal: Boolean?
) {

    constructor() : this(
        "",
        "",
        -1,
        0.0,
        null
    )
}