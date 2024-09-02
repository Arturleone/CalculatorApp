package com.example.simplecalculator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.DecimalFormat

class activity_currency : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private lateinit var tvResult: TextView
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private var currentAmount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        findViewById<ImageView>(R.id.backSeta).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Setup click listener for the TextView to inflate the new layout
        findViewById<TextView>(R.id.conversorMoeda).setOnClickListener {
            inflateCurrencyConverterLayout()
        }
    }

    private fun inflateCurrencyConverterLayout() {
        val layoutInterno = findViewById<ConstraintLayout>(R.id.backgroundConverso)
        layoutInterno.removeAllViews()  // Clear any existing views
        val inflatedLayout = layoutInflater.inflate(R.layout.currencyconverter, layoutInterno, true)

        // Initialize views from the inflated layout
        tvDisplay = inflatedLayout.findViewById(R.id.tvDisplay)
        tvResult = inflatedLayout.findViewById(R.id.tvResult)
        spinnerFrom = inflatedLayout.findViewById(R.id.spinnerFrom)
        spinnerTo = inflatedLayout.findViewById(R.id.spinnerTo)

        // Setup spinners and buttons after inflating the layout
        setupSpinners()
        setupButtons()

    }

    private fun setupSpinners() {
        val currencies = arrayOf("USD", "EUR", "BRL")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
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

    private fun setupButtons() {
        val buttonIds = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { button ->
                appendToAmount((button as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            removeLastDigit()
        }

        findViewById<Button>(R.id.buttonClear).setOnClickListener {
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
