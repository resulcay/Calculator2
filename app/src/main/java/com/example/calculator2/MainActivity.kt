package com.example.calculator2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var calcTextView : TextView? = null
    private var lastNumeric : Boolean = true
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calcTextView = findViewById(R.id.calc_text_id)

    }

    fun onDigit(view: View)
    {
       // Toast.makeText(this,"Button Clicked",Toast.LENGTH_LONG).show()
        lastNumeric = true
        calcTextView?.append((view as Button).text)
    }

    fun onClear(view: View)
    {
        calcTextView?.text = ""
        lastDot = false
    }

    fun decimalPoint(view: View)
    {
        if (lastNumeric && !lastDot && calcTextView?.text?.isNotEmpty() == true)
        {
            calcTextView?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View)
    {
      calcTextView?.text?.let {
          if (lastNumeric && !isOperatorAdded(it.toString()) && !it.endsWith("-") && it.isNotEmpty())
          {
              calcTextView?.append((view as Button).text)
              lastNumeric  = false
              lastDot = false
          }
      }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View)
    {
        if (lastNumeric)
        {
            var value = calcTextView?.text.toString()
            var prefix = ""

            try
            {
                if (value.startsWith("-"))
                {
                    prefix = "-"
                    value = value.substring(1)
                }

                if (value.contains("-"))
                {
                    val splitValue = value.split("-")
                    var firstNumber = splitValue[0]
                    val secondNumber = splitValue[1]

                    if (prefix.isNotEmpty())
                    {
                        firstNumber = prefix + firstNumber
                    }

                    val result = (firstNumber.toDouble() - secondNumber.toDouble()).toString()
                    calcTextView?.text = removeDotZero(result)
                }
                else if (value.contains("+"))
                {
                    val splitValue = value.split("+")
                    var firstNumber = splitValue[0]
                    val secondNumber = splitValue[1]

                    if (prefix.isNotEmpty())
                    {
                        firstNumber = prefix + firstNumber
                    }

                    val result = (firstNumber.toDouble() + secondNumber.toDouble()).toString()
                    calcTextView?.text = removeDotZero(result)
                }
                else if (value.contains("*"))
                {
                    val splitValue = value.split("*")
                    var firstNumber = splitValue[0]
                    val secondNumber = splitValue[1]

                    if (prefix.isNotEmpty())
                    {
                        firstNumber = prefix + firstNumber
                    }

                    val result = (firstNumber.toDouble() * secondNumber.toDouble()).toString()
                    calcTextView?.text = removeDotZero(result)
                }
                else if (value.contains("/"))
                {
                    val splitValue = value.split("/")
                    var firstNumber = splitValue[0]
                    val secondNumber = splitValue[1]

                    if (prefix.isNotEmpty())
                    {
                        firstNumber = prefix + firstNumber
                    }

                    val result = (firstNumber.toDouble() / secondNumber.toDouble()).toString()
                    calcTextView?.text = removeDotZero(result)
                }

            }catch (exception: ArithmeticException)
            {
               // print(exception)
                exception.printStackTrace()
            }
        }
    }

    private  fun  removeDotZero(result: String): String
    {
        var value = result

        if (result.contains(".0"))
            value = result.substring(0,result.length - 2)

        return  value
    }

    private fun isOperatorAdded(value: String): Boolean
    {
        return if (value.startsWith("-"))
        {
            false
        }
        else
        {
            value.contains("/") ||
            value.contains("*") ||
            value.contains("+") ||
            value.contains("-")

        }
    }
}