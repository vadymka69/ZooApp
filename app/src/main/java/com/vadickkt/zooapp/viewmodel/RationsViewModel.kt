package com.vadickkt.zooapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.DietDao
import com.vadickkt.zooapp.database.entities.Diet
import com.vadickkt.zooapp.database.entities.DietType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RationsViewModel @Inject constructor(
    private val dietDao: DietDao
) : ViewModel() {

    private val _rations = MutableStateFlow<List<Diet>>(emptyList())
    val rations: StateFlow<List<Diet>> = _rations

    fun loadRations() {
        viewModelScope.launch(Dispatchers.IO) {
            _rations.value = dietDao.getAllDiets()
        }
    }

    fun addRation(name: String, type: DietType, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val ration = Diet(
                name = name,
                type = type,
                description = description
            )
            dietDao.insertDiet(ration)
            _rations.value = dietDao.getAllDiets()
        }
    }
} 