package com.example.simplecalculator

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class VolumeConverter(
    private val context: Context,
    private val tvDisplay: TextView,
    private val tvResult: TextView,
    private val spinnerFrom: Spinner,
    private val spinnerTo: Spinner
) {
    private var currentAmount: String = ""

    fun setupSpinners() {
        val volumes = arrayOf("Litro", "Mililitro", "Metro Cúbico")
        val adapter = ArrayAdapter(context, R.layout.spinner, volumes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        val onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                volumeConvert()
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
        volumeConvert()
    }

    private fun removeLastDigit() {
        if (currentAmount.isNotEmpty()) {
            currentAmount = currentAmount.dropLast(1)
            updateDisplay()
            volumeConvert()
        }
    }

    private fun clearAmount() {
        currentAmount = ""
        updateDisplay()
        volumeConvert()
    }

    private fun updateDisplay() {
        tvDisplay.text = if (currentAmount.isEmpty()) "0" else currentAmount
    }

    private fun volumeConvert() {
        if (currentAmount.isEmpty() || currentAmount == ".") {
            tvResult.text = "0"
            return
        }

        val amount = currentAmount.toDouble()
        val fromVolume = spinnerFrom.selectedItem.toString()
        val toVolume = spinnerTo.selectedItem.toString()
        val convertedAmount = convert(amount, fromVolume, toVolume)

        tvResult.text = convertedAmount.toString()
    }

    private fun convert(amount: Double, fromVolume: String, toVolume: String): Double {
        val conversionRates = mapOf(
            "Litro" to 1.0,
            "Mililitro" to 1000.0,
            "Metro Cúbico" to 0.001
        )

        val rateFrom = conversionRates[fromVolume] ?: 1.0
        val rateTo = conversionRates[toVolume] ?: 1.0

        return amount * (rateTo / rateFrom)
    }
}
