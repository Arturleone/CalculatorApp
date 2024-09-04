package com.example.simplecalculator

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ComprimentoConverter(
    private val context: Context,
    private val tvDisplay: TextView,
    private val tvResult: TextView,
    private val spinnerFrom: Spinner,
    private val spinnerTo: Spinner
) {
    private val activity = context as? AppCompatActivity
    private var currentAmount: String = ""

    fun setupSpinners() {
        val lengths = arrayOf("Metro", "Centímetro", "Quilômetro")
        val adapter = ArrayAdapter(context, R.layout.spinner, lengths)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                comprimentoConvert()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerFrom.onItemSelectedListener = onItemSelectedListener
        spinnerTo.onItemSelectedListener = onItemSelectedListener
    }

    fun setupButtons(ids: List<Int>) {
        for (id in ids) {
            val button = activity?.findViewById<Button>(id)
            button?.setOnClickListener { appendToAmount(button.text.toString()) }
        }

        activity?.findViewById<Button>(R.id.buttonDelete2)?.setOnClickListener {
            removeLastDigit()
        }

        activity?.findViewById<Button>(R.id.buttonClear)?.setOnClickListener {
            clearAmount()
        }
    }

    private fun appendToAmount(value: String) {
        if (value == "." && currentAmount.contains(".")) {
            return
        }
        currentAmount += value
        updateDisplay()
        comprimentoConvert()
    }

    private fun removeLastDigit() {
        if (currentAmount.isNotEmpty()) {
            currentAmount = currentAmount.dropLast(1)
            updateDisplay()
            comprimentoConvert()
        }
    }

    private fun clearAmount() {
        currentAmount = ""
        updateDisplay()
        comprimentoConvert()
    }

    private fun updateDisplay() {
        tvDisplay.text = if (currentAmount.isEmpty()) "0" else currentAmount
    }

    private fun comprimentoConvert() {
        if (currentAmount.isEmpty() || currentAmount == ".") {
            tvResult.text = "0"
            return
        }

        val amount = currentAmount.toDouble()
        val fromLength = spinnerFrom.selectedItem.toString()
        val toLength = spinnerTo.selectedItem.toString()
        val convertedAmount = convert(amount, fromLength, toLength)

        tvResult.text = convertedAmount.toString()
    }

    private fun convert(amount: Double, fromLength: String, toLength: String): Double {
        val conversionRates = mapOf(
            "Metro" to 1.0,
            "Centímetro" to 100.0,
            "Quilômetro" to 0.001
        )

        val rateFrom = conversionRates[fromLength] ?: 1.0
        val rateTo = conversionRates[toLength] ?: 1.0

        return amount * (rateTo / rateFrom)
    }
}
