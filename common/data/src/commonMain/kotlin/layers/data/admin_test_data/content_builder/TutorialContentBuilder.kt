package layers.data.admin_test_data.content_builder

class TutorialContentBuilder {
    private val stringBuilder = StringBuilder()
    fun appendText(text: String, type: TextType): TutorialContentBuilder {
        when (type) {
            TextType.NONE -> stringBuilder.append(text)
            TextType.BOLD -> stringBuilder.append("<b>$text<b/>")
            TextType.HIGHLIGHT -> stringBuilder.append("<high>$text</high>")

        }
        return this
    }

    fun appendLink(text: String, type: LinkType): TutorialContentBuilder {
        when (type) {
            LinkType.IMAGE -> stringBuilder.append("<img:$text>")
            LinkType.VIDEO -> stringBuilder.append("<vid:$text>")
        }
        return this
    }

    fun build(): String {
        return stringBuilder.toString()
    }
}

