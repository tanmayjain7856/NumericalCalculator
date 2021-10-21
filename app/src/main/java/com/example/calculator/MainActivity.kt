package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Represent whether the lastly pressed key is numeric or not
    var lastNumeric: Boolean = false
    // If true, do not allow to add another DOT
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //Appends the numeric button text to the TextView
    fun onDigit(view: View) {
        // text of button is appended to textView
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    //Clear the TextView
    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    //Append . to the TextView
    fun onDecimalPoint(view: View) {
        // If the last appended value is numeric then append(".") or don't.
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    //It is used to check whether any of the operator is used or not.
    private fun isOperatorAdded(value: String): Boolean {
        /*
         * Here first we will check that if the value starts with "-" then will ignore it.
         * As it is the result value and perform further calculation.
         */
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    //Append +,-,*,/ operators to the TextView as per the Button.Text
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    //Calculate the output
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found.
        if (lastNumeric) {
            // Read the textView value
            var value = tvInput.text.toString()
            var prefix = ""
            try {
                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }
                // If the inputValue contains the Division operator
                if (value.contains("/")) {
                    // Will split the inputValue using Division operator
                    val splitedValue = value.split("/")
                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two
                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }
                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (value.contains("*")) {
                    // If the inputValue contains the Multiplication operator
                    // Will split the inputValue using Multiplication operator
                    val splitedValue = value.split("*")
                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two
                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }
                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (value.contains("-")) {
                    // If the inputValue contains the Subtraction operator
                    // We will split the inputValue using Subtraction operator
                    val splitValue = value.split("-")
                    var one = splitValue[0] // Value One
                    val two = splitValue[1] // Value Two
                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }
                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (value.contains("+")) {
                    // If the inputValue contains the Addition operator
                    // Will split the inputValue using Addition operator
                    val splitedValue = value.split("+")
                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two
                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }
                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    //Remove the zero after decimal point
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2) // removes last two digits i.e. .0
        }
        return value
    }

}