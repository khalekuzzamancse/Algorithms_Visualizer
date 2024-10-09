package feature.navigation.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                start = 4.dp,
                top = 32.dp,
                bottom =8.dp,
                end = 8.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppName()
        }
        Spacer(Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }

}



@Composable
private fun AppName() {
    val text = buildAnnotatedString {
        append(
            AnnotatedString(
                text = "Algorithm",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.primary)
            )
        )
        append(
            AnnotatedString(
                text = " Visualizer",
                spanStyle = SpanStyle(MaterialTheme.colorScheme.secondary)
            )
        )
    }
    Text(
        text = text, style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            fontFamily = FontFamily.Cursive
        )
    )

}

