package com.example.simplecalculator

import android.content.Context
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class CurrencyConverter(
    private val context: Context,
    private val tvDisplay: TextView,
    private val tvResult: TextView,
    private val spinnerFrom: Spinner,
    private val spinnerTo: Spinner
) {

    private var currentAmount: String = ""

    fun setupSpinners() {
        val currencies = arrayOf("USD", "EUR", "BRL")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun setupButtons(buttonIds: List<Int>) {
        for (id in buttonIds) {
            val button = (context as? AppCompatActivity)?.findViewById<Button>(id)
            button?.setOnClickListener { appendToAmount(button.text.toString()) }
        }

        val buttonDelete = (context as? AppCompatActivity)?.findViewById<Button>(R.id.buttonDelete2)
        buttonDelete?.setOnClickListener {
            removeLastDigit()
        }

        val buttonClear = (context as? AppCompatActivity)?.findViewById<Button>(R.id.buttonClear)
        buttonClear?.setOnClickListener {
            clearAmount()
        }
    }

    private fun appendToAmount(value: String) {
        if (value == "." && currentAmount.contains(".")) {
            return
        }
        currentAmount += value
        updateDisplay()
        convertCurrency()
    }

    private fun removeLastDigit() {
        if (currentAmount.isNotEmpty()) {
            currentAmount = currentAmount.dropLast(1)
            updateDisplay()
            convertCurrency()
        }
    }

    private fun clearAmount() {
        currentAmount = ""
        updateDisplay()
        convertCurrency()
    }

    private fun updateDisplay() {
        tvDisplay.text = if (currentAmount.isEmpty()) "0" else currentAmount
    }

    private fun convertCurrency() {
        if (currentAmount.isEmpty() || currentAmount == ".") {
            tvResult.text = "0"
            return
        }

        val amount = currentAmount.toDouble()
        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()
        val convertedAmount = convert(amount, fromCurrency, toCurrency)

        val formattedAmount = DecimalFormat("#,##0.00").format(convertedAmount)
        tvResult.text = formattedAmount
    }

    private fun convert(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val exchangeRates = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "BRL" to 5.25
        )

        val rateFrom = exchangeRates[fromCurrency] ?: 1.0
        val rateTo = exchangeRates[toCurrency] ?: 1.0

        return amount * (rateTo / rateFrom)
    }
}
