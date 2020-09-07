package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.trimIndent()?.split(" ")
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return (if (firstName.isNullOrEmpty()) null else firstName) to (if (lastName.isNullOrEmpty()) null else lastName)
    }

    fun toInitials(firstName: String?, lastName: String? = null): String? {
        var result = ""
        val fn = firstName?.trimIndent()
        val ln = lastName?.trimIndent()
        if (!fn.isNullOrEmpty()) result += fn.first()
        if (!ln.isNullOrEmpty()) result += ln.first()
        return if (result == "") null else result.toUpperCase()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val map = mapOf<Char, String>(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        var result = ""
        for (r in payload) {
            var t = map[r.toLowerCase()]
            if (t != null) {
                if (r.isUpperCase()) {
                    t = "${t.substring(0, 1).toUpperCase()}${t.substring(1)}"
                }
                result += t
            } else {
                result += if (r == ' ') divider else r
            }
        }
        return result
    }
}