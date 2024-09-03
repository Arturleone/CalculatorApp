package com.example.simplecalculator

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class ComprimentoConverter (
    private val context: Context,
    private val tvDisplay: TextView,
    private val tvResult: TextView,
    private val spinnerFrom: Spinner,
    private val spinnerTo: Spinner
) {
    private var currentAmount: String = ""
    fun setupSpinners() {
        val currencies = arrayOf("METRO", "CENTÍMENTRO", "QUILOMETRO")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
    }
    fun setupButtons() {

    }

}