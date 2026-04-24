package com.popov.calculator.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.popov.calculator.R
import com.popov.calculator.domain.model.Coffee
import com.popov.calculator.domain.model.MachineStatus
import com.popov.calculator.domain.model.Resources
import com.popov.calculator.mvp.CoffeePresenter
import com.popov.calculator.mvp.CoffeeView

@Composable
fun CoffeeScreen(presenter: CoffeePresenter) {
    var machineStatus by remember { mutableStateOf<MachineStatus?>(null) }
    var statusMessage by remember { mutableStateOf("") }

    val view =
        remember {
            object : CoffeeView {
                override fun renderStatus(status: MachineStatus) {
                    machineStatus = status
                }

                override fun renderMessage(message: String) {
                    statusMessage = message
                }
            }
        }
    DisposableEffect(presenter) {
        presenter.attach(view)
        onDispose { presenter.detach() }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
        )

        Group("Info") {
            Text(statusMessage, style = MaterialTheme.typography.bodyLarge)
            val r = machineStatus?.resources
            Text("Water: ${r?.water ?: 0} ml")
            Text("Milk: ${r?.milk ?: 0} ml")
            Text("Beans: ${r?.beans ?: 0} g")
            Text("Cups: ${r?.cups ?: 0}")
            Text("Money: ${machineStatus?.money ?: 0} UAH")
        }

        Group("Order coffee") {
            var selected by remember { mutableStateOf(Coffee.ESPRESSO) }
            Coffee.entries.forEach { c ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { selected = c },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = selected == c, onClick = { selected = c })
                    Text(c.displayName)
                }
            }
            Button(onClick = { presenter.onBuy(selected) }) { Text("Buy") }
        }

        Group("Fill resources") {
            var r by remember { mutableStateOf(Resources()) }
            NumberField("Water (ml)", r.water.toString()) { r = r.copy(water = it.toIntOrNull() ?: 0) }
            NumberField("Milk (ml)", r.milk.toString()) { r = r.copy(milk = it.toIntOrNull() ?: 0) }
            NumberField("Beans (g)", r.beans.toString()) { r = r.copy(beans = it.toIntOrNull() ?: 0) }
            NumberField("Cups", r.cups.toString()) { r = r.copy(cups = it.toIntOrNull() ?: 0) }
            Button(onClick = {
                presenter.onFill(r)
                r = Resources()
            }) { Text("Fill") }
        }

        Group("Take money") {
            Button(onClick = presenter::onTakeMoney) { Text("Take") }
        }
    }
}

@Composable
private fun Group(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
private fun NumberField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it.filter(Char::isDigit)) },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}
