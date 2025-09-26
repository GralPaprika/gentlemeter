package icu.gralpaprika.barbarian.counter.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.usecase.IsUserSignedInUseCase
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncDataUseCase
import icu.gralpaprika.barbarian.counter.presentation.model.SyncStatus
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val isUserSignedInUseCase: IsUserSignedInUseCase,
    private val syncDataUseCase: SyncDataUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _syncResult = MutableStateFlow<SyncStatus>(SyncStatus.Idle)

    fun syncData() {
        if (isUserSignedInUseCase()) {
            viewModelScope.launch(dispatcher) {
                val result = syncDataUseCase()
                _syncResult.value = when (result) {
                    is SyncResult.Success -> SyncStatus.Success
                    is SyncResult.Error -> SyncStatus.Error(result.message)
                }
            }
        }
    }
}