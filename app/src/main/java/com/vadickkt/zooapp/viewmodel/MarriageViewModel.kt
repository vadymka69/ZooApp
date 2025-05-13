package com.vadickkt.zooapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vadickkt.zooapp.database.dao.MarriageDao
import com.vadickkt.zooapp.database.entities.Marriage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarriageViewModel @Inject constructor(
    private val marriageDao: MarriageDao
) : ViewModel() {

    private val _marriages = MutableStateFlow<List<Marriage>>(emptyList())
    val marriages: StateFlow<List<Marriage>> = _marriages

    init {
        loadMarriages()
    }

    private fun loadMarriages() {
        viewModelScope.launch {
            _marriages.value = marriageDao.getAllMarriages()
        }
    }

    fun addMarriage(partner1Id: Int, partner2Id: Int) {
        viewModelScope.launch {
            marriageDao.insertMarriage(
                Marriage(
                    marriageId = 0,
                    partner1Id = partner1Id,
                    partner2Id = partner2Id
                )
            )
            loadMarriages()
        }
    }
}