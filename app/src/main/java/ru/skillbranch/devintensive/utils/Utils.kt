package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName : String?) : Pair<String?, String?> {
        val parts : List<String>? = fullName?.split(" ")

        var firstName :String? = parts?.getOrNull(0)
        var lastName  :String? = parts?.getOrNull(1)

        if(firstName.isNullOrBlank())
            firstName = null
        if(lastName.isNullOrBlank())
            lastName = null

//        return Pair(firstName, lastName)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var newPayload = ""
        val map = mapOf('а' to "a", 'б' to "b",'в' to "v",'г' to "g", 'д' to "d", 'е' to "e", 'ё' to "e", 'ж' to "zh", 'з' to "z", 'и' to "i", 'й' to "i", 'к' to "k", 'л' to "l",
                        'м' to "m", 'н' to "n", 'о' to "o", 'п' to "p", 'р' to "r", 'с' to "s", 'т' to "t", 'у' to "u", 'ф' to "f", 'х' to "h", 'ц' to "c", 'ч' to "ch", 'ш' to "sh",
                        'щ' to "sh", 'ъ' to "", 'ы' to "i", 'ь' to "", 'э' to "e", 'ю' to "yu", 'я' to "ya",
                        'А' to "A", 'Б' to "B", 'В' to "V",'Г' to "G", 'Д' to "D", 'Е' to "E", 'Ё' to "E", 'Ж' to "Zh", 'З' to "Z", 'И' to "I", 'Й' to "I", 'К' to "K", 'Л' to "L",
                        'М' to "M", 'Н' to "N", 'О' to "O", 'П' to "P", 'Р' to "R", 'С' to "S", 'Т' to "T", 'У' to "U", 'Ф' to "F", 'Х' to "H", 'Ц' to "C", 'Ч' to "Ch", 'Ш' to "Sh",
                        'Щ' to "Sh", 'Ъ' to "", 'Ы' to "I", 'Ь' to "", 'Э' to "E", 'Ю' to "Yu", 'Я' to "Ya")

        for(i in 0.. payload.length-1) {
            if(payload[i].isLetter() && map.containsKey(payload[i]))
                newPayload += map.get(payload[i])
            else if (payload[i].isWhitespace())
                newPayload += divider
            else
                newPayload += payload[i]
        }

        return newPayload
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val firstChar: Char?
        val secondChar : Char?

        firstChar = if(firstName.isNullOrBlank()) null else firstName.firstOrNull()!!.toUpperCase()
        secondChar = if(lastName.isNullOrBlank()) null else lastName.firstOrNull()!!.toUpperCase()

        return when {
            firstChar != null && secondChar != null -> "$firstChar$secondChar"
            firstChar != null && secondChar == null -> "$firstChar"
            firstChar == null && secondChar != null -> "$secondChar"
            else -> null
        }
    }


    fun validateGitHubRep(repository: String) : Boolean {

        if(repository.isEmpty())
            return true

        val exceptionSet = setOf("enterprise", "features", "topics", "collections", "trending", "events", "marketplace",
                                 "pricing", "nonprofit", "customer-stories", "security", "login", "join")
        val regex = Regex("^((?:https://)|(?:https://www\\.)|(?:www\\.))?github.com/[a-zA-Z0-9-]+$")

        //val matched = regex.containsMatchIn(repository)
        var matched = regex.matches(repository)

        if(matched) {
            val userName = repository.substringAfterLast("/")
            if (userName.first() == '-' || userName.last() == '-')
                matched = false

            if (exceptionSet.contains(userName))
                matched = false
        }
        println(matched)

        return matched
    }
}