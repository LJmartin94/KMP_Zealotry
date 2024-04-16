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

/**
 * Only add actually supported languages to this when statement
 * (otherwise you will get app-default language with locale's Ordinal system applied)
 */
fun getLocale(locale: String): Locale {
    return when (locale) {
        "en" -> En()
        "nl" -> Nl()
        else -> En()
    }
}

class Af : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Sq : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (n % 10 == 4 && n % 100 != 14) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Am : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Ar : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class An : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Hy : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}


class As : Locale {
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


class Az : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 in listOf(1, 2, 5, 7, 8) ||
                    n % 100 in listOf(20, 50, 70, 80)) -> PluralForm.ONE

            (n % 10 in 3..4 ||
                    n % 1000 in listOf(
                100, 200, 300, 400, 500,
                600, 700, 800, 900
            )) -> PluralForm.FEW

            (n == 0 || n % 10 == 6 || n % 100 in listOf(40, 60, 90)) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Bn : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 5 || n in 7..10) -> PluralForm.ONE
            (n in 2..3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            (n == 6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Eu : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Be : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 in 2..3 && n % 100 !in 12..13) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}


class Bs : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Bg : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class My : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Yue : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Ca : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 3) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}


class Ce : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Zh : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Kw : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 100 in 1..4 ||
                    n % 100 in 21..24 ||
                    n % 100 in 41..44 ||
                    n % 100 in 61..64 ||
                    n % 100 in 81..84) -> PluralForm.ONE

            (n % 100 == 5) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Hr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Cs : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Da : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Tmp : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n == 3) -> PluralForm.FEW
            (n == 4) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}


class Nl : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 100 in 2..7 || n % 100 in 9..19) -> PluralForm.TWO
            else -> PluralForm.OTHER
        }
    }
}


class En : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 == 1 && n % 100 != 11) -> PluralForm.ONE
            (n % 10 == 2 && n % 100 != 12) -> PluralForm.TWO
            (n % 10 == 3 && n % 100 != 13) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}