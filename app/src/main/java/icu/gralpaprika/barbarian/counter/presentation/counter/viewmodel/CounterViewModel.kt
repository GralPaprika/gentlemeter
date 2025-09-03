package icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.CounterScreenState
import icu.gralpaprika.barbarian.counter.domain.usecase.DecreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.GetBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.IncreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncBarbarianDataUseCase
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
    private val syncBarbarianDataUseCase: SyncBarbarianDataUseCase,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CounterScreenState())
    val uiState: StateFlow<CounterScreenState> = _uiState.asStateFlow()
    
    init {
        loadInitialState()
        viewModelScope.launch {
            syncBarbarianDataUseCase()
        }
    }
    
    private fun loadInitialState() {
        viewModelScope.launch {
            updateBarbarianState(getBarbarianLevelUseCase())
        }
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

    private fun updateBarbarianState(level: Int) {
        _uiState.value = CounterScreenState(barbarianLevel = level)
    }
}
