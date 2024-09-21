package me.ks.chan.material.skeleton.compose.example.ui.screen.welcome

import androidx.compose.ui.graphics.vector.ImageVector
import me.ks.chan.material.symbols.annotation.Filled
import me.ks.chan.material.symbols.annotation.MaterialSymbol
import me.ks.chan.material.symbols.annotation.MaterialSymbolStyle.Rounded
import me.ks.chan.material.symbols.annotation.Style

@MaterialSymbol
abstract class Pending {

    @Style(Rounded)
    protected abstract val rounded: ImageVector

    @Style(Rounded)
    @Filled
    protected abstract val filled: ImageVector

    operator fun invoke(state: Boolean): ImageVector {
        return if (state) filled else rounded
    }

}
