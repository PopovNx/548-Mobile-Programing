package com.popov.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.popov.calculator.data.SqliteCoffeeRepository
import com.popov.calculator.mvp.CoffeePresenter
import com.popov.calculator.mvp.CoffeePresenterImpl
import com.popov.calculator.ui.CoffeeScreen

class MainActivity : ComponentActivity() {
    private val presenter: CoffeePresenter by lazy {
        CoffeePresenterImpl(SqliteCoffeeRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CoffeeScreen(presenter)
            }
        }
    }
}
