package graph._core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.ui.GlobalColors


@Composable
 fun _NodeStatusIndicator() {
    Column(modifier = Modifier.padding(16.dp)) {
        _StatusIndicatorBox(
            color = GlobalColors.GraphColor.UNDISCOVERED,
            label = "Undiscovered"
        )
        _StatusIndicatorBox(
            color = GlobalColors.GraphColor.DISCOVERED,
            label = "Discovered"
        )
        _StatusIndicatorBox(
            color = GlobalColors.GraphColor.PROCESSED,
            label = "Processed"
        )
    }
}

@Composable
 fun _StatusIndicatorBox(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
