package selection_sort.domain

import androidx.compose.ui.text.AnnotatedString

/**
 * - Used as separate from other module pseudocode so  that module can easily detach from other module
 * - Instead of Raw string,using Annotated String so that we can highlight it
 *  @param line as [AnnotatedString] so that we can highlight it if needed
 *  @param debuggingText is the text that represent the variable or combination of variables state
 *  for this particular line,example show the code along with it debugging text by using `//`:
 *   ```kotlin
 *   if(mid>low) //  10>20=false
 *  ```
 *
 *
 */

internal data class LineForPseudocode(
    val line: AnnotatedString,
    val debuggingText: String? = null,
    val highLighting: Boolean = false,
)