package com.popov.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TaxCalculator() }
    }
}

@Composable
fun TaxCalculator() {
    var amount by remember { mutableStateOf("") }
    var percent by remember { mutableFloatStateOf(0f) }
    val value = amount.toDoubleOrNull()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Bill amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        Slider(
            value = percent,
            onValueChange = { percent = it },
            valueRange = 0f..100f,
        )
        Text(
            text = value?.let {
                val tax = it * percent / 100
                "Bill amount: ${"%.2f".format(it)}$, percent: ${"%.0f".format(percent)}%\nTax amount: ${"%.2f".format(tax)}$"
            } ?: "",
        )
    }
}
