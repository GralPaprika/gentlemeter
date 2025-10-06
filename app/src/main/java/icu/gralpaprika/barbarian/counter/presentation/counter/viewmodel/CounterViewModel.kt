package icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.BuildConfig
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.usecase.DecreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.GetBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.IncreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncDataUseCase
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.CounterScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val getBarbarianLevelUseCase: GetBarbarianLevelUseCase,
    private val increaseBarbarianLevelUseCase: IncreaseBarbarianLevelUseCase,
    private val decreaseBarbarianLevelUseCase: DecreaseBarbarianLevelUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _uiState = MutableLiveData<CounterScreenState>(CounterScreenState.Loading)
    val uiState: LiveData<CounterScreenState> = _uiState
    
    init {
        syncData()
    }
    
    fun onBarbarianButtonClicked() {
        viewModelScope.launch(dispatcher) {
            increaseBarbarianLevelUseCase()
            updateBarbarianState(getBarbarianLevelUseCase())
        }
    }
    
    fun onGentlemanButtonClicked() {
        viewModelScope.launch(dispatcher) {
            decreaseBarbarianLevelUseCase()
            updateBarbarianState(getBarbarianLevelUseCase())
        }
    }

    fun syncData() {
        viewModelScope.launch(dispatcher) {
            withContext(Dispatchers.Main) {
                _uiState.value = CounterScreenState.Loading
            }
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

    private suspend fun updateBarbarianState(level: Int) = withContext(Dispatchers.Main) {
        _uiState.value = if (level < BARBARIAN_MIN_LEVEL)
            CounterScreenState.Counter(barbarianLevel = level)
        else
            CounterScreenState.CavemanScreen
    }

    private companion object {
        const val TAG = "CounterViewModel"
        private const val BARBARIAN_MIN_LEVEL = BuildConfig.BARBARIAN_MIN_LEVEL
    }
}
