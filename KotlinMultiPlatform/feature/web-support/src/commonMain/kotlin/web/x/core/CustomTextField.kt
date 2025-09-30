package web.x.core
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
 fun CustomTextField(
    modifier: Modifier=Modifier,
    label: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly:Boolean=false,
    onValueChange: (String) -> Unit,
    trailingIcon: (@Composable (Modifier) -> Unit)? = null
) {
    _BasicAuthTextField(
        modifier = modifier,
        label = label,
        value = value,
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        keyboardType = keyboardType,
        onValueChanged = onValueChange,
        readOnly=readOnly,
        trailingIcon=trailingIcon
    )
}

@Composable
private fun _BasicAuthTextField(
    modifier: Modifier,
    label: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: ImageVector?,
    keyboardType: KeyboardType,
    onValueChanged: (String) -> Unit,
    readOnly:Boolean=false,
    trailingIcon: (@Composable (Modifier) -> Unit)? = null
) {

    val borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
    val placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
    val fontSize = 15.sp

    //Why BasicTextField
    //Less height,no default inner padding,leading icon can be centred for custom height

    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = TextStyle(fontSize = fontSize, color = MaterialTheme.colorScheme.onSurface),
        singleLine = true,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),//make sure cursor visible in dark mode also
        decorationBox = { innerText ->
                Row(
                    modifier
                        .border(width = 2.dp, color = borderColor, CircleShape)
                        .padding(vertical = 10.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            tint = MaterialTheme.colorScheme.tertiary,
                            contentDescription = "leading icon",
                            modifier = Modifier.padding(start = 12.dp).size(22.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                    }

                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            _Placeholder(label, fontSize, placeholderColor)
                        }
                        // Call innerText in both cases to ensure the cursor is shown
                        innerText()
                    }
                    if (trailingIcon != null) {
                        Spacer(Modifier.width(8.dp))
                        trailingIcon(Modifier.padding(end = 8.dp))
                    }

                }

        }
    )


}

@Composable
private fun _Placeholder(
    text: String,
    fontSize: TextUnit,
    color: Color,
    fontWeight: FontWeight = FontWeight.W600,
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight
    )

}