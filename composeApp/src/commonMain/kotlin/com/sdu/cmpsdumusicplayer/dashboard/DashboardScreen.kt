package com.sdu.cmpsdumusicplayer.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cmpsdumusicplayer.composeapp.generated.resources.Res
import cmpsdumusicplayer.composeapp.generated.resources.explore_details
import cmpsdumusicplayer.composeapp.generated.resources.likes
import com.sdu.cmpsdumusicplayer.network.models.topfiftycharts.TopFiftyCharts
import com.sdu.cmpsdumusicplayer.decompose.DashboardMainComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardScreen(dashboardMainComponent: DashboardMainComponent) {
    val state = dashboardMainComponent.viewModel.dashboardState.collectAsState()

    when (val resultedState = state.value) {
        is DashboardViewState.Failure -> Failure(resultedState.error)
        DashboardViewState.Loading -> Loading()
        is DashboardViewState.Success -> {
            DashboardView(resultedState) {
                dashboardMainComponent.onOutPut(DashboardMainComponent.Output.PlaylistSelected(it))
            }
        }
    }
}

@Composable
internal fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color(0xFFFACD66),
        )
    }
}

@Composable
internal fun Failure(message: String) {
    Box(modifier = Modifier.fillMaxSize().padding(32.dp)) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFFFACD66))
        )
    }
}
@Composable
internal fun DashboardView(
    dashboardState: DashboardViewState.Success,
    navigateToDetails: (String) -> Unit
){
   Column {
       TopChartView(dashboardState.topFiftyCharts, navigateToDetails)
   }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun TopChartView(topFiftyCharts: TopFiftyCharts, navigateToDetails: (String) -> Unit) {
    Box(
        modifier = Modifier.aspectRatio(ratio = (367.0 / 450.0).toFloat())
            .clip(RoundedCornerShape(20.dp))
            .padding(24.dp).clickable(onClick = { navigateToDetails(topFiftyCharts.id.orEmpty()) })
    ) {
//        val painter = rememberAsyncImagePainter(
//            topFiftyCharts.images?.first()?.url.orEmpty()
//        )
//        Image(
//            painter,
//            topFiftyCharts.images?.first()?.url.orEmpty(),
//            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)),
//            contentScale = ContentScale.Crop
//        )
        Column(modifier = Modifier.padding(16.dp).align(Alignment.BottomStart)) {
            Text(
                topFiftyCharts.name.orEmpty(),
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = Color.White
            )
            Text(
                topFiftyCharts.description.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(top = 6.dp)
            )
            Row(modifier = Modifier.padding(top = 40.dp)) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    tint = Color(0xFFFACD66),
                    contentDescription = stringResource(Res.string.explore_details),
                    modifier = Modifier.size(30.dp).align(Alignment.Top)
                )
                Text(
                    text = "${topFiftyCharts.followers?.total ?: 0} ${stringResource(Res.string.likes)}",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}