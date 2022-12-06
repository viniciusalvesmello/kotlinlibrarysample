package io.github.viniciusalvesmello.kotlinlibrarysample

import org.junit.Assert
import org.junit.Test

class StringExtTest {
    @Test
    fun `given string is null When execute handle without parameter Then check result equal default value of parameter`() {
        val nullString: String? = null

        val result = nullString.handle()

        Assert.assertEquals(result, "")
    }

    @Test
    fun `given string is null When execute handle Then check result equal default parameter`() {
        val nullString: String? = null

        val result = nullString.handle(default = "NewString")

        Assert.assertEquals(result, "NewString")
    }

    @Test
    fun `given string is not null When execute handle Then check result equal initial value`() {
        val notNullString = "InitialString"

        val result = notNullString.handle(default = "NewString")

        Assert.assertEquals(result, "InitialString")
    }

    @Test
    fun `given string with chars and numbers When execute onlyNumbers with default values Then check result value contains only numbers`() {
        val stringWithCharsAndNumbers = "[%#@!*&Ab99Tx88RcYi11"

        val result = stringWithCharsAndNumbers.onlyNumbers()

        Assert.assertEquals("998811", result)
    }

    @Test
    fun `given string with chars and numbers When execute onlyNumbers Then check result value contains only numbers and replacement value`() {
        val stringWithCharsAndNumbers = "[%#@!*&Ab99Tx88RcYi11"

        val result = stringWithCharsAndNumbers.onlyNumbers(replacement = "#")

        Assert.assertEquals("#########99##88####11", result)
    }

    @Test
    fun `given string with chars and numbers When execute onlyLettersAndNumbers with default values Then check result value contains only letters and numbers`() {
        val stringWithCharsAndNumbers = "[%#@!*&Ab99Tx88RcYi11----$"

        val result = stringWithCharsAndNumbers.onlyLettersAndNumbers()

        Assert.assertEquals("Ab99Tx88RcYi11", result)
    }

    @Test
    fun `given string with chars and numbers When execute onlyLettersAndNumbers Then check result value contains only only letters, numbers and replacement value`() {
        val stringWithCharsAndNumbers = "[%#@!*&Ab99Tx88RcYi11----$"

        val result = stringWithCharsAndNumbers.onlyLettersAndNumbers(replacement = "#")

        Assert.assertEquals("#######Ab99Tx88RcYi11#####", result)
    }
}
