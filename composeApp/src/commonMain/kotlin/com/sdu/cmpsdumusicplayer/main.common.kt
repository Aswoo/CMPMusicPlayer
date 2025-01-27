package com.sdu.cmpsdumusicplayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.sdu.cmpsdumusicplayer.chartdetails.ChartDetailsScreen
import com.sdu.cmpsdumusicplayer.dashboard.DashboardScreen
import com.sdu.cmpsdumusicplayer.decompose.MusicRoot
import com.sdu.cmpsdumusicplayer.theme.AppTheme

@Composable
internal fun MainCommon(
    rootComponent: MusicRoot,
    isLargeScreen: Boolean
) {
    val dialogOverlay by rootComponent.dialogOverlay.subscribeAsState()

    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Children(
                    stack = rootComponent.childStack
                ) {
                    when (val child = it.instance) {
                        is MusicRoot.Child.Dashboard -> {
                            DashboardScreen(child.dashboardMainComponent)

                        }

                        is MusicRoot.Child.Details -> {
                            ChartDetailsScreen(child.detailsComponent)
                        }
                    }
                }
            }
//            Box(modifier = Modifier.align(Alignment.BottomEnd)) {
//                dialogOverlay.child?.instance?.also {
//                    PlayerView(it)
//                }
//            }
        }
    }
}
