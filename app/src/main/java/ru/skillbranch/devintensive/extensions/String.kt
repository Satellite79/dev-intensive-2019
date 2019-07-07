package ru.skillbranch.devintensive.extensions

fun String.truncate(newLen: Int = 16) : String {
    val addDots = this.trimEnd().length > newLen
    var newStr = this

    if (newStr.length > newLen) {
        newStr = this.substring(0..newLen - 1)
        if(addDots) {
            while(newStr.last().isWhitespace())
                newStr = newStr.dropLast(1)
            newStr += "..."
        }
    }
    return newStr
}

fun String.stripHtml() : String {
    var newStr = this.replace(Regex(""" +""")," ")

    newStr = newStr.replace(Regex("""<[^<]+>"""),"")

    return newStr
}