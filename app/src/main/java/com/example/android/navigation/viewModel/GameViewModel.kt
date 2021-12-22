package com.example.android.navigation.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.formatNumber
import com.example.android.navigation.database.Number
import com.example.android.navigation.database.NumberDatabaseDao
import kotlinx.coroutines.launch

class GameViewModel(dataSource: NumberDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    val database = dataSource

    private var currentNumber = MutableLiveData<Number?>()

    val numbers = database.getAllNumber()

    val numbersString = Transformations.map(numbers) { numbers ->
        formatNumber(numbers, application.resources)
    }

    private val _generatedNumber = MutableLiveData<Int>()
    val generatedNumber: LiveData<Int>
        get() = _generatedNumber

    init {
        initializeCurrentNumber()
        _generatedNumber.value = 0
    }

    private fun initializeCurrentNumber() {
        viewModelScope.launch {
            currentNumber.value = getCurrentNumberFromDatabase()
        }
    }

    private suspend fun getCurrentNumberFromDatabase(): Number? {
        var number = database.getLastNumber()
        if (number?.numberValue == -1) {
            number = null
        }
        return number
    }

    private suspend fun insert(number: Number) {
        database.insert(number)
    }

    private suspend fun update(number: Number) {
        database.update(number)
    }

    private suspend fun clear() {
        database.clear()
    }

    fun clearDatabase() {
        viewModelScope.launch {
            clear()
        }
    }

    private val _submitButtonVisible = MutableLiveData<Boolean?>()
    val submitButtonVisible: LiveData<Boolean?>
        get() = _submitButtonVisible

    fun enableSubmitButton() {
        _submitButtonVisible.value = true
    }

    fun disableSubmitButton() {
        _submitButtonVisible.value = null
    }

    fun setGeneratedNumber(random: Int) {
        _generatedNumber.value = random
    }

    val visibleButton = Transformations.map(submitButtonVisible) {
        null != it
    }

    fun saveNumber() {
        viewModelScope.launch {
            val newNumber = Number()
            newNumber.numberValue = _generatedNumber.value?:-1
            insert(newNumber)
            currentNumber.value = getCurrentNumberFromDatabase()
        }
    }

}