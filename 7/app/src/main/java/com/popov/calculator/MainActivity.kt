package com.popov.calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {

    private lateinit var amountEdit: EditText
    private lateinit var percentSlider: Slider
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        amountEdit = findViewById(R.id.amountEdit)
        percentSlider = findViewById(R.id.percentSlider)
        resultText = findViewById(R.id.resultText)

        amountEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) = updateResult()
        })
        percentSlider.addOnChangeListener { _, _, _ -> updateResult() }

        updateResult()
    }

    private fun updateResult() {
        val value = amountEdit.text.toString().toDoubleOrNull()
        val percent = percentSlider.value
        resultText.text = value?.let {
            val tax = it * percent / 100
            "Bill amount: ${"%.2f".format(it)}$, percent: ${"%.0f".format(percent)}%\n" +
                "Tax amount: ${"%.2f".format(tax)}$"
        } ?: ""
    }
}
