package com.armaApp.armaFDC

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

val rhs_d30a = Gun ( // final
    name = "RHS D-30A",
    velocities = listOf (179.4F, 220.8F, 269.1F, 303.6F, 345F, 389.2F),
    minQE = 350,
    maxQE = 1260,
    spread = 3.9F
)

val rhs_podnos = Gun ( // final
    name = "RHS 2B14-1 Podnos",
    velocities = listOf (73.8F, 147.7F, 211F),
    minQE = 704,
    maxQE = 1505,
    spread = 0.93F
)

val rhs_2s1 = Gun ( // final
    name = "RHS 2S1",
    velocities = listOf (179.4F, 220.8F, 269.1F, 303.6F, 345F, 389.2F),
    minQE = 350,
    maxQE = 1282,
    spread = 3.62F
)

val cup_d30 = Gun ( // final
    name = "CUP D-30",
    velocities = listOf (153.9F, 243F, 388.8F),
    minQE = 350,
    maxQE = 1422,
    spread = 1.6F
)

val cup_l16 = Gun (
    name = "CUP L16A2",
    velocities = listOf (70F, 140F, 200F),
    minQE = 800,
    maxQE = 1515,
    spread = 0.96F
)

val sholef = Gun ( // final
    name = "Sholef",
    velocities = listOf (153.9F, 388.8F, 648F, 810F),
    minQE = 350,
    maxQE = 1420,
    spread = 1.62F
)

val sochor = Gun ( // final
    name = "Sochor",
    velocities = listOf (153.9F, 388.8F, 648F, 810F),
    minQE = 350,
    maxQE = 1419,
    spread = 1.62F
)

val seara = Gun ( // final
    name = "Seara",
    velocities = listOf (114.8F, 130.1F, 148.8F, 170F, 193.8F, 221.9F, 255F, 289.9F, 329.8F, 374F, 420.8F, 476F, 543.2F, 629F, 715.7F, 816F),
    minQE = 350,
    maxQE = 1166,
    spread = 0.25F
)

val mk6 = Gun ( // final
    name = "Mk 6",
    velocities = listOf (70F, 140F, 200F),
    minQE = 800,
    maxQE = 1564,
    spread = 0.97F
)

val zamak_mrl = Gun ( // final
    name = "Zamak MRL",
    velocities = listOf (114.8F, 130.1F, 148.8F, 170F, 193.8F, 221.9F, 255F, 289.9F, 329.8F, 374F, 420.8F, 476F, 543.2F, 629F, 715.7F, 816F),
    minQE = 350,
    maxQE = 1187,
    spread = 0.25F
)

val kamaz_mrl = Gun ( // final
    name = "KamAZ MRL",
    velocities = listOf (114.8F, 130.1F, 148.8F, 170F, 193.8F, 221.9F, 255F, 289.9F, 329.8F, 374F, 420.8F, 476F, 543.2F, 629F, 715.7F, 816F),
    minQE = 350,
    maxQE = 1159,
    spread = 0.24F
)

val cup_m270_he = Gun ( // final
    name = "CUP M270 (HE)",
    velocities = listOf (114.8F, 130.1F, 148.8F, 170F, 193.8F, 221.9F, 255F, 289.9F, 329.8F, 374F, 420.8F, 476F, 543.2F, 629F, 715.7F, 816F),
    minQE = 350,
    maxQE = 1515,
    spread = 0.25F
)

val cup_bm21 = Gun ( // final
    name = "CUP BM-21",
    velocities = listOf (289.8F, 310.5F, 345F, 393.3F),
    minQE = 350,
    maxQE = 1492,
    spread = 2.02F
)

val cup_podnos = Gun ( // final
    name = "CUP 2B14 Podnos",
    velocities = listOf (70F, 140F, 200F),
    minQE = 800,
    maxQE = 1511,
    spread = 1F
)

val cup_rm70 = Gun ( // final
    name = "CUP RM-70",
    velocities = listOf (289.8F, 310.5F, 345F, 393.3F),
    minQE = 350,
    maxQE = 1511,
    spread = 2.03F
)

val cup_m119 = Gun ( // final
    name = "CUP M119",
    velocities = listOf (153.9F, 243F, 388.8F),
    minQE = 350,
    maxQE = 1422,
    spread = 1.6F
)

val cup_m1129 = Gun ( // final
    name = "CUP M1129",
    velocities = listOf (105.3F, 186.3F, 267.3F),
    minQE = 808,
    maxQE = 1519,
    spread = 1.26F
)

val cup_m252 = Gun ( // final
    name = "CUP M252",
    velocities = listOf (70F, 140F, 200F),
    minQE = 800,
    maxQE = 1515,
    spread = 0.95F
)

val rhs_m252 = Gun ( // final
    name = "RHS M252",
    velocities = listOf (40F, 80F, 120F, 160F, 200F),
    minQE = 800,
    maxQE = 1515,
    spread = 1.07F,
)

val rhs_2s3m1 = Gun (
    name = "RHS 2S3M1",
    velocities = listOf (167.7F, 195.2F, 226.6F, 262.0F, 307.9F, 353.7F, 415.3F),
    minQE = 350,
    maxQE = 1193,
    spread = 2.7F,
)

val rhs_m109a6 = Gun (
    name = "RHS M109A6",
    velocities = listOf (153.9F, 243.0F, 388.8F, 648.0F, 810.0F),
    minQE = 350,
    maxQE = 1422,
    spread = 1.14F,
)

val rhs_m119a2 = Gun (
    name = "RHS M119A2",
    velocities = listOf (153.9F, 243.0F, 388.8F),
    minQE = 350,
    maxQE = 1422,
    spread = 1.57F,
)

/*val mod_gun = Gun (
    name =
    velocities =
    minQE =
    maxQE =
    spread =
)*/

val guns = listOf(
    cup_bm21,
    cup_d30,
    cup_l16,
    cup_m1129,
    cup_m119,
    cup_m252,
    cup_m270_he,
    cup_podnos,
    cup_rm70,
    kamaz_mrl,
    mk6,
    rhs_podnos,
    rhs_2s1,
    rhs_2s3m1,
    rhs_d30a,
    rhs_m109a6,
    rhs_m119a2,
    rhs_m252,
    seara,
    sholef,
    sochor,
    zamak_mrl,
)