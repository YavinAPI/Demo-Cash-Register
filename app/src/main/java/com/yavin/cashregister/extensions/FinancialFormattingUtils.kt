package com.yavin.cashregister.extensions

import java.text.NumberFormat
import java.util.*

fun formatToLocaleAndCurrency(
        amount: Double,
        minFractionDigits: Int = 2,
        maxFractionDigits: Int = 2,
        locale: Locale = Locale.getDefault(),
        currency: Currency = Currency.getInstance("EUR")
): String {

    var result = ""

    try {
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.currency = Currency.getInstance("EUR")
        numberFormat.minimumFractionDigits = minFractionDigits
        numberFormat.maximumFractionDigits = maxFractionDigits

        result = numberFormat.format(amount)
        val currencySymbolIsInFront = result.indexOf(currency.symbol) == 0
        val hasEnoughSpace = result.length > currency.symbol.length
        val spaceBetweenCurrencyAndAmountExists = hasEnoughSpace && result[currency.symbol.length + 1].isWhitespace()

        if (currencySymbolIsInFront && hasEnoughSpace && !spaceBetweenCurrencyAndAmountExists) {
            result = result.replace(currency.symbol, "${currency.symbol} ", true)
        }
    } catch (exception: Exception) {
        exception.printStackTrace()
    }

    return result
}