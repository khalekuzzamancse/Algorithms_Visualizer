package core.commonui.tutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TutorialImplementationSection(list: List<TutorialContent.Implementation>) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        list.forEach{
            Column( modifier = Modifier.padding(8.dp)){
                Text(
                    it.languageName,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(8.dp))
                CodeEditor(it.code)
            }

        }

    }
}

