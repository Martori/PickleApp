package cat.martori.pickleapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cat.martori.pickleapp.R

@Composable
fun ErrorDialog(errorMessage: String, dismissError: () -> Unit) {
    Dialog(onDismissRequest = { dismissError() }) {
        Column(
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.background)
                .padding(12.dp)
        ) {
            Text(text = stringResource(R.string.defaultErrorMessageTitle), style = MaterialTheme.typography.h6)
            Text(text = errorMessage, style = MaterialTheme.typography.body1)
            TextButton(modifier = Modifier.align(Alignment.End), onClick = { dismissError() }) {
                Text(text = stringResource(R.string.acceptButton))
            }
        }
    }
}