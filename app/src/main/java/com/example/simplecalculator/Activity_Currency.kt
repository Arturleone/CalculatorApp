package com.example.simplecalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class Activity_Currency : AppCompatActivity() {



    private lateinit var currencyConverter: CurrencyConverter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        // Voltar a tela principal
        findViewById<ImageView>(R.id.backSeta).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // Setup click listener for the TextView to inflate the new layout
        findViewById<TextView>(R.id.conversorMoeda).setOnClickListener {
            inflateCurrencyConverterLayout()
        }

        findViewById<TextView>(R.id.buttonArea).setOnClickListener {
            inflateAreaConverterLayout()
        }

        findViewById<TextView>(R.id.buttonVolume).setOnClickListener {
            inflateVolumeConverterLayout()
        }

        findViewById<TextView>(R.id.buttonComprimento).setOnClickListener {
            inflateComprimentoConverterLayout()

        }

        findViewById<TextView>(R.id.buttonMassa).setOnClickListener {
            inflateMassaConverterLayout()
        }

    }

    private fun inflateMassaConverterLayout() {
        val inflatedLayout = inflateLayout(R.layout.massaconverter)

        val tvDisplay = inflatedLayout.findViewById<TextView>(R.id.tvDisplayMassa)
        val tvResult = inflatedLayout.findViewById<TextView>(R.id.tvResultMassa)
        val spinnerFrom = inflatedLayout.findViewById<Spinner>(R.id.spinnerFromMassa)
        val spinnerTo = inflatedLayout.findViewById<Spinner>(R.id.spinnerToMassa)
        val massaConverter = MassaConverter(this, tvDisplay, tvResult, spinnerFrom, spinnerTo)

        massaConverter.setupSpinners()
        massaConverter.setupButtons(listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        ))
    }

    private fun inflateComprimentoConverterLayout() {
        val inflatedLayout = inflateLayout(R.layout.comprimentoconverter)

        val tvDisplay = inflatedLayout.findViewById<TextView>(R.id.tvDisplayComprimento)
        val tvResult = inflatedLayout.findViewById<TextView>(R.id.tvResultComprimento)
        val spinnerFrom = inflatedLayout.findViewById<Spinner>(R.id.spinnerFromComprimento)
        val spinnerTo = inflatedLayout.findViewById<Spinner>(R.id.spinnerToComprimento)
        val comprimentoConverter = ComprimentoConverter(this, tvDisplay, tvResult, spinnerFrom, spinnerTo)

        comprimentoConverter.setupSpinners()
        comprimentoConverter.setupButtons(listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        ))

    }

    private fun inflateVolumeConverterLayout() {
        val inflatedLayout = inflateLayout(R.layout.volumeconverter)

        val tvDisplay = inflatedLayout.findViewById<TextView>(R.id.tvDisplayVolume)
        val tvResult = inflatedLayout.findViewById<TextView>(R.id.tvResultVolume)
        val spinnerFrom = inflatedLayout.findViewById<Spinner>(R.id.spinnerFromVolume)
        val spinnerTo = inflatedLayout.findViewById<Spinner>(R.id.spinnerToVolume)
        val volumeConverter = VolumeConverter(this, tvDisplay, tvResult, spinnerFrom, spinnerTo)

        volumeConverter.setupSpinners()
        volumeConverter.setupButtons(listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9
        ))

    }

    private fun inflateAreaConverterLayout() {
        val inflatedLayout = inflateLayout(R.layout.areaconverter)

        val tvDisplay = inflatedLayout.findViewById<TextView>(R.id.tvDisplayArea)
        val tvResult = inflatedLayout.findViewById<TextView>(R.id.tvResultArea)
        val spinnerFrom = inflatedLayout.findViewById<Spinner>(R.id.spinnerFromArea)
        val spinnerTo = inflatedLayout.findViewById<Spinner>(R.id.spinnerToArea)
        val areaConverter = AreaConverter(this, tvDisplay, tvResult, spinnerFrom, spinnerTo)

        areaConverter.setupSpinners()
        areaConverter.setupButtons(
            listOf(
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
            )
        )

    }

    private fun inflateCurrencyConverterLayout() {
        val inflatedLayout = inflateLayout(R.layout.currencyconverter)

        // Initialize CurrencyConverter
        val tvDisplay = inflatedLayout.findViewById<TextView>(R.id.tvDisplay)
        val tvResult = inflatedLayout.findViewById<TextView>(R.id.tvResult)
        val spinnerFrom = inflatedLayout.findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = inflatedLayout.findViewById<Spinner>(R.id.spinnerTo)
        val currencyConverter = CurrencyConverter(this, tvDisplay, tvResult, spinnerFrom, spinnerTo)

        // Setup spinners and buttons after inflating the layout
        currencyConverter.setupSpinners()
        currencyConverter.setupButtons(
            listOf(
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9
            )
        )
    }

    private fun inflateLayout(Id: Int): View {
        return findViewById<ConstraintLayout>(R.id.backgroundConverso).apply {
            removeAllViews()
            layoutInflater.inflate(Id, this, true)
        }
    }
}
