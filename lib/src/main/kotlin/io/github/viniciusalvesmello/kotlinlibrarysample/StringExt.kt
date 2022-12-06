package io.github.viniciusalvesmello.kotlinlibrarysample

import io.github.viniciusalvesmello.kotlinlibrarysample.constants.Constants.String.EMPTY
import io.github.viniciusalvesmello.kotlinlibrarysample.constants.Constants.String.REGEX_ONLY_LETTERS_NUMBERS
import io.github.viniciusalvesmello.kotlinlibrarysample.constants.Constants.String.REGEX_ONLY_NUMBERS

fun String?.handle(default: String = EMPTY) = this ?: default

fun String.onlyNumbers(replacement: String = EMPTY): String =
    replace(REGEX_ONLY_NUMBERS.toRegex(), replacement)

fun String.onlyLettersAndNumbers(replacement: String = EMPTY): String =
    replace(REGEX_ONLY_LETTERS_NUMBERS.toRegex(), replacement)
