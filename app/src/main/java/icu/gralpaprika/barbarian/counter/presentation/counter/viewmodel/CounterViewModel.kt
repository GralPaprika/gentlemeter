package icu.gralpaprika.barbarian.counter.presentation.counter.viewmodel

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import icu.gralpaprika.barbarian.counter.R
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.presentation.counter.screen.model.CounterScreenState
import icu.gralpaprika.barbarian.counter.domain.usecase.DecreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.GetBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.IncreaseBarbarianLevelUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncDataUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getBarbarianLevelUseCase: GetBarbarianLevelUseCase,
    private val increaseBarbarianLevelUseCase: IncreaseBarbarianLevelUseCase,
    private val decreaseBarbarianLevelUseCase: DecreaseBarbarianLevelUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val mediaPlayer = MediaPlayer.create(context, R.raw.caveman_sound)
    
//    private val _uiState = MutableStateFlow<CounterScreenState>(CounterScreenState.Loading)
//    val uiState: StateFlow<CounterScreenState> = _uiState.asStateFlow()
    private val _uiState = MutableLiveData<CounterScreenState>(CounterScreenState.Loading)
    val uiState: LiveData<CounterScreenState> = _uiState
    
    init {
        syncData()
    }

    fun playCavemanSound() {
//        mediaPlayer.start()
    }

    fun stopCavemanSound() {
//        if (mediaPlayer.isPlaying) {
//            mediaPlayer.pause()
//            mediaPlayer.seekTo(PLAYER_RESET_POSITION_MS)
//        }
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
        viewModelScope.launch {
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
        _uiState.value = if (level < 10)
            CounterScreenState.Counter(barbarianLevel = level)
        else
            CounterScreenState.CavemanScreen
    }

    private companion object {
        const val TAG = "CounterViewModel"
        const val PLAYER_RESET_POSITION_MS = 0
    }
}
