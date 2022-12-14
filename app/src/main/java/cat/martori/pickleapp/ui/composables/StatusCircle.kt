package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cat.martori.pickleapp.domain.entities.Status


private val Status.color
    get() = when (this) {
        Status.Alive -> Color.Green
        Status.Dead -> Color.Red
        Status.Unknown -> Color.LightGray
    }

@Composable
fun StatusCircle(status: Status, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(status.color)
    )
}