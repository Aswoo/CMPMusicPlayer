package com.sdu.cmpsdumusicplayer.decompose

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MusicRoot {

    val childStack: Value<ChildStack<*, Child>>
    val dialogOverlay: Value<ChildSlot<*, PlayerComponent>>

    sealed class Child {
        data class Dashboard(val dashboardMainComponent: DashboardMainComponent) : Child()
        data class Details(val detailsComponent: ChartDetailsComponent) : Child()
    }
}
