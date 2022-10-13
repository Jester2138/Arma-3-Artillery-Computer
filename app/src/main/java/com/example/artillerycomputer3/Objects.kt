package com.example.artillerycomputer3

// solutions class
class Solution (
    val azimuth: Int,
    val quadElev: Int,
    val charge: Int,
    val tof: Int,
    val spread: Int
)

// gun data
class Gun (
    val name: String,
    val velocities: List<Float>,
    val minQE: Int,
    val maxQE: Int,
    val spread: Float
)

val rhs_d30a = Gun (
    name = "RHS D-30A",
    velocities = listOf (179.4F, 220.8F, 269.1F, 303.6F, 345F, 389.2F),
    minQE = 350,
    maxQE = 1260,
    spread = 3.9F
)

val rhs_podnos = Gun (
    name = "RHS Podnos",
    velocities = listOf (73.8F, 147.7F, 211F),
    minQE = 704,
    maxQE = 1505,
    spread = 0.93F
)

val rhs_2s1 = Gun (
    name = "RHS 2S1",
    velocities = listOf (179.4F, 220.8F, 269.1F, 303.6F, 345F, 389.2F),
    minQE = 350,
    maxQE = 1282,
    spread = 3.62F
)

val cup_d30 = Gun (
    name = "CUP D-30",
    velocities = listOf (153.9F, 243F, 388.8F),
    minQE = 350,
    maxQE = 1404,
    spread = 0F
)

val rm70 = Gun (
    name = "RM-70",
    velocities = listOf (251.9F, 286.4F, 324.3F, 357.4F, 400.2F, 448.5F),
    minQE = 350,
    maxQE = 1151,
    spread = 6.82F
)

val lib_leFH = Gun (
    name = "LIB LeFH 18",
    velocities = listOf (138F, 207F, 299F),
    minQE = 350,
    maxQE = 1486,
    spread = 10.1F
)

val cup_l16 = Gun (
    name = "CUP L16",
    velocities = listOf (70F, 140F, 200F),
    minQE = 800,
    maxQE = 1515,
    spread = 0.97F
)

val guns = listOf(
    cup_d30,
    cup_l16,
    lib_leFH,
    rhs_2s1,
    rhs_d30a,
    rhs_podnos,
    rm70
)