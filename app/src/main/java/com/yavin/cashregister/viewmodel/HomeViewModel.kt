package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.yavin.cashregister.extensions.formatToLocaleAndCurrency
import com.yavin.cashregister.service.repository.TransactionRepository
import com.yavin.macewindu.logging.ILogger
import com.yavin.macewindu.utils.extensions.addCharAtIndex
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val transactionRepository: TransactionRepository,
) : AndroidViewModel(application) {

    @Inject
    lateinit var logger: ILogger

    private val logName = this::class.java.simpleName
    private val decimalSeparator: String by lazy {
        val numberFormat = NumberFormat.getCurrencyInstance() as? DecimalFormat
        numberFormat?.decimalFormatSymbols?.decimalSeparator?.toString() ?: ","
    }

    private var _validateReferenceLiveData: MutableLiveData<String> = MutableLiveData()
    val validateReferenceLiveData: LiveData<String> = _validateReferenceLiveData

    private var _transactionCountLiveData: MutableLiveData<Long> = MutableLiveData()

    fun setValidateReferenceValue(value: String) {
        _validateReferenceLiveData.value = value
    }

    fun getValidateReference(): String = _validateReferenceLiveData.value ?: ""

    fun getTransactionCount() = _transactionCountLiveData.value


    var sumValueDouble: Double = 0.0
    private var sumValueString: String = formatToLocaleAndCurrency(sumValueDouble)

    private var _sumValueFormattedData: MutableLiveData<String> = MutableLiveData(sumValueString)
    val sumValueFormattedData: LiveData<String> = _sumValueFormattedData


    fun handleUserAddNumber(value: Int) {
        try {
            var strSum = String.format(Locale.getDefault(), "%.2f", sumValueDouble)
            strSum = strSum.replace(decimalSeparator, "")

            if (strSum.length < 8) {
                strSum = "$strSum$value"
                sumValueDouble = strSum.addCharAtIndex('.', strSum.length - 2).toDouble()
                sumValueString = formatToLocaleAndCurrency(amount = sumValueDouble)
                _sumValueFormattedData.postValue(sumValueString)
            }
        } catch (exception: Exception) {
            logger.e(
                logName,
                "handleUserAddNumber() thrown an exception : ${exception.stackTraceToString()}"
            )
            exception.printStackTrace()
        }
    }

    fun handleUserDeleteNumber() {
        try {
            var strSum = String.format(Locale.getDefault(), "%.2f", sumValueDouble)
            strSum = strSum.replace(decimalSeparator, "")
            strSum = strSum.substring(0, strSum.length - 1)
            strSum = strSum.padStart(3, '0')
            strSum = strSum.substring(0, strSum.length - 2) +
                decimalSeparator + strSum.substring(strSum.length - 2)
            sumValueDouble = strSum.replace(decimalSeparator, ".").toDouble()
            sumValueString = formatToLocaleAndCurrency(amount = sumValueDouble)
            _sumValueFormattedData.postValue(sumValueString)
        } catch (exception: Exception) {
            logger.e(
                logName,
                "handleUserDeleteNumber thrown an exception : ${exception.stackTraceToString()}"
            )
            exception.printStackTrace()
        }
    }


    fun handleUserClearAmount() {
        try {
            sumValueDouble = 0.0
            sumValueString = formatToLocaleAndCurrency(amount = sumValueDouble)
            _sumValueFormattedData.postValue(sumValueString)
        } catch (exception: Exception) {
            logger.e(
                logName,
                "handleUserClearAmount() thrown an exception : ${exception.stackTraceToString()}"
            )
            exception.printStackTrace()
        }


    }

    fun getSumValueForPaymentActivity(): String {
        var result = ""

        try {
            result =
                String.format(Locale.getDefault(), "%.2f", sumValueDouble)
                    .replace(decimalSeparator, "")
        } catch (e: Exception) {
            logger.e(
                logName,
                "getSumValueForPaymentActivity() threw an exception: ${e.stackTraceToString()}"
            )
            e.printStackTrace()
        }

        return result
    }

}

