package libs.localisation

//REF: https://www.unicode.org/cldr/cldr-aux/charts/36.1/supplemental/language_plural_rules.html

enum class PluralForm {
    ZERO,
    ONE,
    TWO,
    FEW,
    MANY,
    OTHER
}

interface Locale {
    fun getOrdinal(n: Int): PluralForm
}

fun getLocale(locale: String): Locale {
    return when (locale){
        "en" -> En()
        else -> En()
    }
}

class Af: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Sq: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (n % 10 == 4 && n % 100 != 14) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Am: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Ar: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class An: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Hy: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}


class As: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 5 || n in 7..10) -> PluralForm.ONE
            (n == 2 || n == 3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            (n == 6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}
//
//
//class Tmp: Locale {
//    override fun getOrdinal(n: Int): PluralForm {
//        return when {
//            (n == 0) -> PluralForm.ZERO
//            (n == 1) -> PluralForm.ONE
//            (n == 2) -> PluralForm.TWO
//            (n == 3) -> PluralForm.FEW
//            (n == 4) -> PluralForm.MANY
//            else -> PluralForm.OTHER
//        }
//    }
//}


class En: Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 == 1 && n % 100 != 11) -> PluralForm.ONE
            (n % 10 == 2 && n % 100 != 12) -> PluralForm.TWO
            (n % 10 == 3 && n % 100 != 13) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}