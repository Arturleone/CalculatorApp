package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var inputValue1: Double? = 0.0
    private var inputValue2: Double? = null
    private var currentOperator: Operator? = null
    private var result: Double? = null
    private val equation: StringBuilder = StringBuilder().append(zero)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setNightModeIndicator()
    }


    private fun setListeners() {
        for(button in getNumericButtons()) {
            button.setOnClickListener{onNumberClicked(button.text.toString())}
        }
        with(binding) {
            buttonZero.setOnClickListener{onZeroClicked()}
            buttonZeroZero.setOnClickListener{onDoubleZeroClicked()}
            buttonPonto.setOnClickListener{onDecimalPointClicked()}
            buttonAdicao.setOnClickListener{onOperatorClicked(Operator.adicao)}
            buttonSubtracao.setOnClickListener{onOperatorClicked(Operator.subtracao)}
            buttonMultiplicacao.setOnClickListener{onOperatorClicked(Operator.multiplicacao)}
            buttonDivision.setOnClickListener{onOperatorClicked(Operator.divisao)}
            buttonIgual.setOnClickListener{onEqualsClicked()}
            buttonAllClear.setOnClickListener{onAllClearClicked()}
            buttonPlusMinus.setOnClickListener{onPlusMinusClicked()}
            buttonPercentage.setOnClickListener{onPercentageClicked()}
            imageNightMode.setOnClickListener{toggleNightMode()}

        }
    }

    private fun setNightModeIndicator() {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            binding.imageNightMode.setImageResource(R.drawable.contrast)
        } else {
            binding.imageNightMode.setImageResource(R.drawable.halfmoon)
        }
    }

    private fun toggleNightMode() {
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()
    }

    private fun onPercentageClicked() {
        if(inputValue2 == null) {
            val percentage = getInputValue1() / 100
            inputValue1 = percentage
            equation.clear().append(percentage)
            updateInputOnDisplay()
        } else {
            val percentageOfValue1 = (getInputValue1() * getInputValue2()) / 100
            val percentageOfValue2 = getInputValue2() / 100
            result = when(requireNotNull(currentOperator)) {
                Operator.adicao -> getInputValue1() + percentageOfValue1
                Operator.subtracao -> getInputValue1() - percentageOfValue1
                Operator.multiplicacao -> getInputValue1() * percentageOfValue1
                Operator.divisao -> getInputValue1() / percentageOfValue2
            }
            equation.clear().append(zero)
            updateResultOnDisplay(isPorcentage = true)
            inputValue1 = result
            result = null
            inputValue2 = null
            currentOperator = null
        }
    }

    private fun onPlusMinusClicked() {
        if(equation.startsWith(menos)) {
            equation.deleteCharAt(0)
        } else {
            equation.insert(0, menos)
        }
        setInput()
        updateInputOnDisplay()
    }

    private fun onAllClearClicked() {
        inputValue1 = 0.0
        inputValue2 = null
        currentOperator = null
        result = null
        equation.clear().append(zero)
        clearDisplay()
    }

    private fun onOperatorClicked (operator: Operator) {
        onEqualsClicked()
        currentOperator = operator
    }

    private fun onEqualsClicked() {
        if(inputValue2 != null) {
            result = calculate()
            equation.clear().append(zero)
            updateResultOnDisplay()
            inputValue1 = result
            result = null
            inputValue2 = null
            currentOperator = null
        } else {
            equation.clear().append(zero)
        }
    }

    private fun calculate(): Double {
        return when(requireNotNull(currentOperator)) {
            Operator.adicao -> getInputValue1() + getInputValue2()
            Operator.subtracao -> getInputValue1() - getInputValue2()
            Operator.multiplicacao -> getInputValue1() * getInputValue2()
            Operator.divisao -> getInputValue1() / getInputValue2()
        }
    }

    private fun onDecimalPointClicked() {
        if(equation.contains(ponto_decimal)) return
        equation.append(ponto_decimal)
        setInput()
        updateInputOnDisplay()
    }

    private fun onZeroClicked() {
        if (equation.startsWith(zero)) return
        onNumberClicked(zero)
    }

    private fun onDoubleZeroClicked() {
        if (equation.startsWith(zero)) return
        onNumberClicked(double_zero)
    }

    private fun getNumericButtons() = with(binding) {
        arrayOf(
            buttonUm,
            buttonDois,
            buttonTres,
            buttonQuatro,
            buttonCinco,
            buttonSeis,
            buttonSete,
            buttonOito,
            buttonNove,
        )
    }

    private fun onNumberClicked(numberText: String) {
        if(equation.startsWith(zero)) {
            equation.deleteCharAt(0)
        } else if (equation.startsWith("$menos$zero")) {
            equation.deleteCharAt(1)
        }
        equation.append(numberText)
        setInput()
        updateInputOnDisplay()
    }

    private fun setInput() {
        if(currentOperator == null) {
            inputValue1 = equation.toString().toDouble()
        } else {
            inputValue2 = equation.toString().toDouble()
        }
    }

    private fun clearDisplay() {
        with(binding) {
            textInput.text = getFormattedDisplayValue(value = getInputValue1())
            textEquation.text = null
        }
    }

    private fun updateResultOnDisplay(isPorcentage: Boolean = false) {
        binding.textInput.text = getFormattedDisplayValue(value = result)
        var input2Text = getFormattedDisplayValue(value = getInputValue2())
        if(isPorcentage) input2Text = "$input2Text${getString(R.string.percentage)}"
        binding.textEquation.text = String.format(
            "%s %s %s",
            getFormattedDisplayValue(value = getInputValue1()),
            getOperatorSymbol(),
            input2Text
        )
    }

    private fun updateInputOnDisplay(){
        if(result == null) {
            binding.textEquation.text = null
        }
        binding.textInput.text = equation
    }

    private fun getInputValue1() = inputValue1 ?: 0.0
    private fun getInputValue2() = inputValue2 ?: 0.0

    private fun getOperatorSymbol(): String {
        return when(requireNotNull(currentOperator)) {
            Operator.adicao -> getString(R.string.adicao)
            Operator.subtracao -> getString(R.string.subtracao)
            Operator.multiplicacao -> getString(R.string.multiplicacao)
            Operator.divisao -> getString(R.string.division)
        }
    }

    private fun getFormattedDisplayValue(value: Double?): String? {
        val originalValue = value ?: return null
        return if (originalValue % 1 == 0.0) {
            originalValue.toInt().toString()
        } else {
            originalValue.toString()
        }
    }

    enum class Operator {
        adicao, subtracao, multiplicacao, divisao
    }

    private companion object {
        const val ponto_decimal = "."
        const val zero = "0"
        const val double_zero = "00"
        const val menos = "-"
    }
}