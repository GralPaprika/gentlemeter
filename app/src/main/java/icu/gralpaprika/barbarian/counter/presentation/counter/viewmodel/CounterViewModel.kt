package icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.CounterScreenState
import icu.gralpaprika.barbarian.counter.domain.usecase.DecreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.GetBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.IncreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncDataUseCase
import icu.gralpaprika.barbarian.counter.presentation.model.SyncStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val getBarbarianLevelUseCase: GetBarbarianLevelUseCase,
    private val increaseBarbarianLevelUseCase: IncreaseBarbarianLevelUseCase,
    private val decreaseBarbarianLevelUseCase: DecreaseBarbarianLevelUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<CounterScreenState>(CounterScreenState.Loading)
    val uiState: StateFlow<CounterScreenState> = _uiState.asStateFlow()
    
    init {
        syncData()
    }
    
    fun onBarbarianButtonClicked() {
        viewModelScope.launch {
            increaseBarbarianLevelUseCase()
            updateBarbarianState(getBarbarianLevelUseCase())
        }
    }
    
    fun onGentlemanButtonClicked() {
        viewModelScope.launch {
            decreaseBarbarianLevelUseCase()
            updateBarbarianState(getBarbarianLevelUseCase())
        }
    }

    fun syncData() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = CounterScreenState.Loading
            syncDataUseCase().let { result ->
                when (result) {
                    is SyncResult.Success -> {
                        updateBarbarianState(getBarbarianLevelUseCase())
                    }
                    is SyncResult.Error -> {
                        // TODO: Maybe should show error message?
                        Log.e(TAG, result.type.description)
                        updateBarbarianState(getBarbarianLevelUseCase())
                    }
                }
            }
        }
    }

    private fun updateBarbarianState(level: Int) {
        _uiState.value = CounterScreenState.Content(barbarianLevel = level)
    }

    private companion object {
        const val TAG = "CounterViewModel"
    }
}
