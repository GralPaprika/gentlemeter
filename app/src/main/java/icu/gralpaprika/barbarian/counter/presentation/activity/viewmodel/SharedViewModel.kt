package icu.gralpaprika.barbarian.counter.presentation.activity.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import icu.gralpaprika.barbarian.counter.domain.model.SyncResult
import icu.gralpaprika.barbarian.counter.domain.usecase.SyncToCloudUseCase
import icu.gralpaprika.barbarian.counter.presentation.model.SyncStatus
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val syncToCloudUseCase: SyncToCloudUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _syncResult = MutableStateFlow<SyncStatus>(SyncStatus.Idle)

    fun syncToCloud() {
        viewModelScope.launch(dispatcher) {
            val result = syncToCloudUseCase()
            _syncResult.value = when (result) {
                is SyncResult.Success -> SyncStatus.Success
                is SyncResult.Error -> SyncStatus.Error(result.message)
            }
        }
    }
}