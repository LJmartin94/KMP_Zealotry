package libs.localisation

// REF: https://www.unicode.org/cldr/cldr-aux/charts/36.1/supplemental/language_plural_rules.html

enum class PluralForm {
    ZERO,
    ONE,
    TWO,
    FEW,
    MANY,
    OTHER,
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
        return PluralForm.OTHER
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
        return PluralForm.OTHER
    }
}

class Ar : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class An : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
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
            (n % 10 in listOf(1, 2, 5, 7, 8) || n % 100 in listOf(20, 50, 70, 80)) -> PluralForm.ONE

            (
                n % 10 in 3..4 || n % 1000 in
                    listOf(
                        100, 200, 300, 400, 500, 600, 700, 800, 900,
                    )
            ) -> PluralForm.FEW

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
        return PluralForm.OTHER
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
        return PluralForm.OTHER
    }
}

class Bg : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class My : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Yue : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
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
        return PluralForm.OTHER
    }
}

class Zh : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Kw : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (
                n % 100 in 1..4 ||
                    n % 100 in 21..24 || n % 100 in 41..44 ||
                    n % 100 in 61..64 || n % 100 in 81..84
            ) -> PluralForm.ONE

            (n % 100 == 5) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Hr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Cs : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Da : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
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

class Et : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Fil : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Fi : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Fr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Gl : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ka : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (
                n == 0 || n % 100 in 2..20 ||
                    n % 100 in listOf(40, 60, 80)
            ) -> PluralForm.MANY

            else -> PluralForm.OTHER
        }
    }
}

class De : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class El : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Gu : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (n in 2..3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            (n == 6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class He : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Hi : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (n in 2..3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            (n == 6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Hu : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 5) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Is : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Id : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class In : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ia : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ga : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class It : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n in listOf(8, 11, 80, 800)) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Ja : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Kn : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Kk : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 == 6 || n % 10 == 9 || n % 10 == 0 && n != 0) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Km : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ko : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ky : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Lo : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Lv : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Lt : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Dsb : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Mk : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 == 1 && n % 100 != 11) -> PluralForm.ONE
            (n % 10 == 2 && n % 100 != 12) -> PluralForm.TWO
            (n % 10 in 7..8 && n % 100 !in 17..18) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Ms : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Ml : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Mr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            (n in 2..3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}

class Mn : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ne : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n in 1..4) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Nb : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Or : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 5 || n in 7..9) -> PluralForm.ONE
            (n in 2..3) -> PluralForm.TWO
            (n == 4) -> PluralForm.FEW
            (n == 6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Ps : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Fa : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Pl : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Pt : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Prg : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Pa : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ro : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Ru : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Sc : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n in listOf(8, 11, 80, 800)) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Gd : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1 || n == 11) -> PluralForm.ONE
            (n == 2 || n == 12) -> PluralForm.TWO
            (n == 3 || n == 13) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}

class Sr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Scn : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n in listOf(8, 11, 80, 800)) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Sd : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Si : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Sk : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Sl : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Es : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Sw : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Sv : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 in 1..2 && n % 100 !in 11..12) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Gsw : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ta : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Te : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Th : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Tr : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Tk : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 in listOf(6, 9) || n == 10) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}

class Uk : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n % 10 == 3 && n % 100 != 13) -> PluralForm.FEW
            else -> PluralForm.OTHER
        }
    }
}

class Hsb : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Ur : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Uz : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Vi : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 1) -> PluralForm.ONE
            else -> PluralForm.OTHER
        }
    }
}

class Cy : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return when {
            (n == 0 || n in 7..9) -> PluralForm.ZERO
            (n == 1) -> PluralForm.ONE
            (n == 2) -> PluralForm.TWO
            (n in 3..4) -> PluralForm.FEW
            (n in 5..6) -> PluralForm.MANY
            else -> PluralForm.OTHER
        }
    }
}

class Fy : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}

class Zu : Locale {
    override fun getOrdinal(n: Int): PluralForm {
        return PluralForm.OTHER
    }
}
