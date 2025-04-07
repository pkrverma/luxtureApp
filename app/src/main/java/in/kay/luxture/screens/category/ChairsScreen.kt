package `in`.kay.luxture.screens

import androidx.compose.foundation.Image
import `in`.kay.luxture.SharedViewModel
import `in`.kay.luxture.models.FurnitureModel
import `in`.kay.luxture.ui.theme.colorWhite
import `in`.kay.luxture.utils.getChairs
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ChairsScreen(onViewDetailClick: (FurnitureModel) -> Unit) {
    val chairs = getChairs()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(chairs) { chair ->
            ChairCard(chair = chair, onClick = { onViewDetailClick(chair) })
        }
    }
}

@Composable
fun ChairCard(chair: FurnitureModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = chair.drawable),
                contentDescription = chair.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = chair.name ?: "Chair", style = MaterialTheme.typography.h6)
            Text(text = "₹${chair.price}", style = MaterialTheme.typography.body1)
        }
    }
}
