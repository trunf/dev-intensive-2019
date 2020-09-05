package ru.skillbranch.devintensive.extensions

fun String.truncate(n: Int = 16) = if (this.count() > n) {
    var res = this.dropLast(this.count() - n)
    if (res.last() == ' ') res = res.dropLast(1)
    if (res.last() != ' ') res += "..."
    res
} else {
    this
}

fun String.stripHtml(): String {
    var result = this.replace(Regex(pattern = """<([a-z]+) *[^/]*?>"""), "")
    result = result.replace(Regex(pattern = """</([a-z]+) *[^/]*?>"""), "")
    return result.replace(Regex(pattern = """\s{2,}"""), " ")
}

