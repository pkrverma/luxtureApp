package `in`.kay.furture.utils

import `in`.kay.furture.R
import `in`.kay.furture.models.FurnitureModel
import kotlin.random.Random

fun getCategories() = listOf(
    FurnitureModel("Chair", R.drawable.ic_chair),
    FurnitureModel("Sofa", R.drawable.ic_sofa),
    FurnitureModel("Home Decor", R.drawable.ic_home_decor),
    FurnitureModel("Office", R.drawable.ic_office),
    FurnitureModel("Tables", R.drawable.ic_table),
)

fun getRecommended(): List<FurnitureModel>   {
    val list = mutableListOf<FurnitureModel>()
    list.add(getChairs()[(0 until getChairs().size).random()])
    list.add(getOffices()[(0 until getOffices().size).random()])
    list.add(getSofas()[(0 until getSofas().size).random()])
    list.add(getHomeDecors()[(0 until getHomeDecors().size).random()])
    list.add(getTables()[(0 until getTables().size).random()])
    return list
}

fun getChairs() = listOf(
    FurnitureModel(
        "Modern Chair",
        R.drawable.chair_grey,
        price = 800,
        description = randomDescription("Modern Chair"),
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/chair/arm_chair__furniture/scene.gltf",
        type = "Chair"
    ),
    FurnitureModel(
        "Manchester Chair",
        R.drawable.chair_old,
        price = 1259,
        description = randomDescription("Manchester Chair"),
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/the_matrix_red_chesterfield_chair/scene.gltf",
        type = "Chair"
    ),
    FurnitureModel(
        "Orange Sheen Chair",
        R.drawable.chair_orange,
        price = 1899,
        description = randomDescription("Orange Sheen Chair"),
        link = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/SheenChair/glTF/SheenChair.gltf",
        type = "Chair"
    ),
    FurnitureModel(
        "Classic Chair",
        R.drawable.chair_wood,
        price = 689,
        description = randomDescription("Classic Chair"),
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/chair/furniture_for_real-time_visualization_engine/scene.gltf",
        type = "Chair"
    )
)

fun getSofas() = listOf(
    FurnitureModel(
        "Velvet Sofa",
        R.drawable.sofa1,
        price = 6370,
        description = randomDescription("Velvet Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/KhronosGroup/glTF-Sample-Models/master/2.0/GlamVelvetSofa/glTF/GlamVelvetSofa.gltf"
    ),
    FurnitureModel(
        "Leather Sofa",
        R.drawable.sofa2,
        price = 7400,
        description = randomDescription("Leather Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/sofas/sofa_game_ready__2k_pbr/scene.gltf"
    ),
    FurnitureModel(
        "Victorian Sofa",
        R.drawable.sofa3,
        price = 9490,
        description = randomDescription("Victorian Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/victorian_lounge_sofa/scene.gltf"
    ),
    FurnitureModel(
        "Chesterfield Sofa",
        R.drawable.sofa5,
        price = 4489,
        description = randomDescription("Chesterfield Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/sofa_02_4k.gltf/sofa_02_4k.gltf"
    ),
    FurnitureModel(
        "Modern Sofa",
        R.drawable.sofa6,
        price = 8430,
        description = randomDescription("Modern Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/sofa_6/scene.gltf"
    ),
    FurnitureModel(
        "Lawson Sofa",
        R.drawable.sofa8,
        price = 6260,
        description = randomDescription("Lawson Sofa"),
        type = "Sofa",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/sofas/sofa/scene.gltf"
    )
)

fun randomDescription(type: String): String {
    val description = "This $type is an elegant and functional piece of furniture. It is suitable for family visits and parties with friends and perfect for relaxing in front of the TV after hard work."
    return description.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

fun randomPrice(lowerPrice: Int, upperPrice: Int): Int {
    return (lowerPrice..upperPrice).random()
}

fun getHomeDecors() = listOf(
    FurnitureModel(
        "Modern Pot",
        R.drawable.dec1,
        price = 330,
        description = randomDescription("Modern Pot"),
        type = "Home Decoration",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/decor/lavender_vase/scene.gltf"
    ),
    FurnitureModel(
        "Lamp",
        R.drawable.dec2,
        price = 250,
        description = randomDescription("Lamp"),
        type = "Home Decoration",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/decor/lamp/scene.gltf"
    ),
    FurnitureModel(
        "Flowers",
        R.drawable.dec3,
        price = 480,
        description = randomDescription("Flowers"),
        type = "Home Decoration",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/decor/flower_vase%20(1)/scene.gltf"
    )
)

fun getOffices() = listOf(
    FurnitureModel(
        "Gaming Chair",
        R.drawable.office,
        price = 6845,
        description = randomDescription("Gaming Chair"),
        type = "Office",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/office%20essentials%20and%20cabinets/gaming_chair_1-_black/scene.gltf"
    ),
    FurnitureModel(
        "Classic chair ",
        R.drawable.office_chair,
        price = 2589,
        description = randomDescription("Classic chair "),
        type = "Office",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/office%20essentials%20and%20cabinets/office_chair%20(1)/scene.gltf"
    ),
    FurnitureModel(
        "Desk",
        R.drawable.office_desk,
        price = 7859,
        description = randomDescription("Desk"),
        type = "Office",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/office%20essentials%20and%20cabinets/modern_table_set/scene.gltf"
    )
)

fun getTables() = listOf(
    FurnitureModel(
        "Table",
        R.drawable.table1,
        price = 1569,
        description = randomDescription("Table"),
        type = "Table",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/table/mahogany_table/scene.gltf"
    ),
    FurnitureModel(
        "Wooden Table",
        R.drawable.table2,
        price = 1259,
        description = randomDescription("Wooden Table"),
        type = "Table",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/office%20essentials%20and%20cabinets/simple_desk_free/scene.gltf"
    ),
    FurnitureModel(
        "Metal Table",
        R.drawable.table3,
        price = 895,
        description = randomDescription("Metal Table"),
        type = "Table",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/table/industrial_table/scene.gltf"
    ),
    FurnitureModel(
        "Classic Table",
        R.drawable.table4,
        price = 1350,
        description = randomDescription("Classic Table"),
        type = "Table",
        link = "https://raw.githubusercontent.com/Sachinbhola/App-Templates/master/Resources/table/victorian_coffee_table/scene.gltf"
    )
)

fun getSize(idx: Int): Int {
    return when (idx) {
        0 -> getChairs().size
        1 -> getSofas().size
        2 -> getHomeDecors().size
        3 -> getOffices().size
        4 -> getTables().size
        else -> 0
    }
}

