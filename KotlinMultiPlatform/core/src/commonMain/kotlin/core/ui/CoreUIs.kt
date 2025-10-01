package core.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHorizontal(width:Int)= Spacer(Modifier.width(width.dp))
@Composable
fun SpacerVertical(height:Int)= Spacer(Modifier.height(height.dp))