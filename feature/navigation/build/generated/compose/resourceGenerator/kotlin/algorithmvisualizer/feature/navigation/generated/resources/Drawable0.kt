@file:OptIn(org.jetbrains.compose.resources.InternalResourceApi::class)

package algorithmvisualizer.feature.navigation.generated.resources

import kotlin.OptIn
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
private object Drawable0 {
  public val just_logo: DrawableResource = org.jetbrains.compose.resources.DrawableResource(
        "drawable:just_logo",
          setOf(
            org.jetbrains.compose.resources.ResourceItem(setOf(), "drawable/just_logo.jpg"),
          )
      )

  public val just_logo_transparent: DrawableResource =
      org.jetbrains.compose.resources.DrawableResource(
        "drawable:just_logo_transparent",
          setOf(
            org.jetbrains.compose.resources.ResourceItem(setOf(),
          "drawable/just_logo_transparent.png"),
          )
      )
}

@ExperimentalResourceApi
internal val Res.drawable.just_logo: DrawableResource
  get() = Drawable0.just_logo

@ExperimentalResourceApi
internal val Res.drawable.just_logo_transparent: DrawableResource
  get() = Drawable0.just_logo_transparent
