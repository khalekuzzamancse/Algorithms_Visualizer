package bubble_sort

fun grep(lines: List<String>, pattern: String, action: (String) -> Unit) {
    val regex = pattern.toRegex()
    lines.filter(regex::containsMatchIn)
        .forEach(action)
}