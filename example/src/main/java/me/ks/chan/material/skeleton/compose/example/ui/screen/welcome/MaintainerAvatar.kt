package me.ks.chan.material.skeleton.compose.example.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val MaintainerAvatar by lazy {
    materialIcon("MaintainerAvatar") {
        materialPath {
            moveTo(6F, 2F)
            horizontalLineTo(18F)
            verticalLineTo(10F)
            horizontalLineTo(22F)
            verticalLineTo(22F)
            horizontalLineTo(18F)
            verticalLineTo(18F)
            horizontalLineTo(14F)
            verticalLineTo(14F)
            horizontalLineTo(10F)
            verticalLineTo(18F)
            horizontalLineTo(6F)
            verticalLineTo(22F)
            horizontalLineTo(2F)
            verticalLineTo(10F)
            horizontalLineTo(6F)
            verticalLineTo(2F)
            close()
        }
    }
}

@Composable
@Preview
private fun Preview() {
    Image(
        modifier = Modifier.size(320.dp),
        imageVector = MaintainerAvatar,
        colorFilter = ColorFilter.tint(Color(0xFFBF87DC)),
        contentDescription = "Maintainer Avatar",
    )
}