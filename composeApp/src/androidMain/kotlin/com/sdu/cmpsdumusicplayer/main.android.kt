package musicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.sdu.cmpsdumusicplayer.MainCommon
import com.sdu.cmpsdumusicplayer.decompose.MusicRootImpl


@Composable
fun MainAndroid(root: MusicRootImpl) {
    Column(Modifier.background(color = Color(0xFF1A1E1F))) {
        val context = LocalContext.current
        MainCommon(root, false)
    }
}

