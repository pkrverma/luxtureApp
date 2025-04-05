package `in`.kay.furture.screens

import `in`.kay.furture.SharedViewModel
import `in`.kay.furture.ui.theme.Typography
import `in`.kay.furture.ui.theme.colorBlack
import `in`.kay.furture.ui.theme.colorPurple
import `in`.kay.furture.ui.theme.colorWhite
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavHostController
import androidx.palette.graphics.Palette

@Composable
fun DetailScreen(viewModel: SharedViewModel, navController: NavHostController) {
    val furnitureModel = viewModel.data ?: return // Avoid null crash
    val context = LocalContext.current
    var btnColor by remember { mutableStateOf(colorPurple) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Color(0xFFF3F6F8)),
        constraintSet = constraintsDetail()
    ) {
        Text(
            text = furnitureModel.type.orEmpty(),
            style = Typography.body1,
            fontSize = 18.sp,
            color = Color(0xFF171717).copy(alpha = 0.4f),
            modifier = Modifier.layoutId("tvType")
        )
        Text(
            text = furnitureModel.name.orEmpty(),
            style = Typography.h1,
            fontSize = 32.sp,
            color = colorBlack,
            modifier = Modifier.layoutId("tvName")
        )
        Text(
            text = "from",
            style = Typography.body1,
            fontSize = 18.sp,
            color = Color(0xFF171717).copy(alpha = 0.4f),
            modifier = Modifier.layoutId("tvFrom")
        )
        Text(
            text = "$ ${furnitureModel.price}",
            style = Typography.h1,
            fontSize = 24.sp,
            color = colorBlack,
            modifier = Modifier.layoutId("tvPrice")
        )
        Image(
            painter = painterResource(id = furnitureModel.drawable),
            contentDescription = "Furniture Image",
            modifier = Modifier
                .height(240.dp)
                .zIndex(1.1f)
                .layoutId("ivImg")
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(colorWhite)
                .layoutId("clDetail")
                .padding(24.dp, 160.dp, 24.dp, 24.dp)
        ) {
            Text(
                text = furnitureModel.name.orEmpty(),
                style = Typography.h1,
                fontSize = 18.sp,
                color = Color(0xFF171717)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = furnitureModel.description.orEmpty(),
                style = Typography.body2,
                fontSize = 16.sp,
                color = Color(0xFF171717).copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(64.dp))

            val bitmap = runCatching {
                BitmapFactory.decodeResource(context.resources, furnitureModel.drawable)
            }.getOrNull()

            bitmap?.let {
                Palette.from(it).generate { palette ->
                    val vibrantColor = palette?.vibrantSwatch?.rgb
                        ?: palette?.darkMutedSwatch?.rgb
                    vibrantColor?.let { rgb ->
                        btnColor = Color(rgb)
                    }
                }
            }

            Button(
                onClick = {
                    val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                        .appendQueryParameter("file", furnitureModel.link)
                        .appendQueryParameter("mode", "ar_only")
                        .appendQueryParameter("title", furnitureModel.name)
                        .build()

                    val sceneViewerIntent = Intent(Intent.ACTION_VIEW).apply {
                        data = intentUri
                        setPackage("com.google.android.googlequicksearchbox")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }

                    context.startActivity(sceneViewerIntent)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = btnColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "View in Your Space",
                    style = Typography.body1,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }

            Button(
                onClick = {
                    viewModel.setSelectedItem(furnitureModel) // ✅ Updated here
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkoutItem", furnitureModel)
                    navController.navigate("checkout")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorPurple),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Buy now",
                    style = Typography.body1,
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

fun constraintsDetail(): ConstraintSet {
    return ConstraintSet {
        val tvType = createRefFor("tvType")
        val tvName = createRefFor("tvName")
        val tvFrom = createRefFor("tvFrom")
        val tvPrice = createRefFor("tvPrice")
        val ivImg = createRefFor("ivImg")
        val clDetail = createRefFor("clDetail")

        constrain(tvType) {
            top.linkTo(parent.top, 64.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvName) {
            top.linkTo(tvType.bottom, 4.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvFrom) {
            top.linkTo(tvName.bottom, 24.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(tvPrice) {
            top.linkTo(tvFrom.bottom, 4.dp)
            start.linkTo(parent.start, 24.dp)
        }
        constrain(ivImg) {
            top.linkTo(clDetail.top, 32.dp)
            bottom.linkTo(clDetail.top, 32.dp)
            start.linkTo(parent.start, 24.dp)
            end.linkTo(parent.end, 24.dp)
            width = Dimension.fillToConstraints
        }
        constrain(clDetail) {
            top.linkTo(tvPrice.bottom, 80.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }
}
