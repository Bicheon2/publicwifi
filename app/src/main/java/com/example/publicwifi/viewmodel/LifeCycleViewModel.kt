package com.example.publicwifi.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.publicwifi.observer.AppLifecycleObserver
import com.example.publicwifi.share.ShareApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LifeCycleViewModel @Inject constructor(application: Application): AndroidViewModel(application) {
    private val _isAppInForeground = MutableStateFlow(false)
    val isAppInForeground: StateFlow<Boolean> = _isAppInForeground

    init {
        // 앱 라이프사이클 이벤트를 감지하고 isAppInForeground 값을 업데이트합니다.
        viewModelScope.launch {
            AppLifecycleObserver().lifecycleState.collect { isForeground ->
                _isAppInForeground.value = isForeground
            }
        }
    }
}