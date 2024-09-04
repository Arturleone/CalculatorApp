package com.example.simplecalculator

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MassaConverter(
    private val context: Context,
    private val tvDisplay: TextView,
    private val tvResult: TextView,
    private val spinnerFrom: Spinner,
    private val spinnerTo: Spinner
) {
    private var currentAmount: String = ""

    fun setupSpinners() {
        val masses = arrayOf("Kilograma", "Tonelada", "Miligrama")
        val adapter = ArrayAdapter(context, R.layout.spinner, masses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        val onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                massaConvert()
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        spinnerFrom.onItemSelectedListener = onItemSelectedListener
        spinnerTo.onItemSelectedListener = onItemSelectedListener
    }

    fun setupButtons(ids: List<Int>) {
        for (id in ids) {
            val button = (context as? android.app.Activity)?.findViewById<android.widget.Button>(id)
            button?.setOnClickListener { appendToAmount(button.text.toString()) }
        }

        (context as? android.app.Activity)?.findViewById<android.widget.Button>(R.id.buttonDelete2)?.setOnClickListener {
            removeLastDigit()
        }

        (context as? android.app.Activity)?.findViewById<android.widget.Button>(R.id.buttonClear)?.setOnClickListener {
            clearAmount()
        }
    }

    private fun appendToAmount(value: String) {
        if (value == "." && currentAmount.contains(".")) {
            return
        }
        currentAmount += value
        updateDisplay()
        massaConvert()
    }

    private fun removeLastDigit() {
        if (currentAmount.isNotEmpty()) {
            currentAmount = currentAmount.dropLast(1)
            updateDisplay()
            massaConvert()
        }
    }

    private fun clearAmount() {
        currentAmount = ""
        updateDisplay()
        massaConvert()
    }

    private fun updateDisplay() {
        tvDisplay.text = if (currentAmount.isEmpty()) "0" else currentAmount
    }

    private fun massaConvert() {
        if (currentAmount.isEmpty() || currentAmount == ".") {
            tvResult.text = "0"
            return
        }

        val amount = currentAmount.toDouble()
        val fromMass = spinnerFrom.selectedItem.toString()
        val toMass = spinnerTo.selectedItem.toString()
        val convertedAmount = convert(amount, fromMass, toMass)

        tvResult.text = convertedAmount.toString()
    }

    private fun convert(amount: Double, fromMass: String, toMass: String): Double {
        val conversionRates = mapOf(
            "Kilograma" to 1.0,
            "Tonelada" to 0.001,
            "Miligrama" to 1000000.0
        )

        val rateFrom = conversionRates[fromMass] ?: 1.0
        val rateTo = conversionRates[toMass] ?: 1.0

        return amount * (rateTo / rateFrom)
    }
}
