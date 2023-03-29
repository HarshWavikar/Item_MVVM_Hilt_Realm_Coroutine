package com.example.item_realm_hilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.item_realm_hilt.data.Repository
import com.example.item_realm_hilt.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var _items = MutableLiveData<List<Item>>()


    init {
        viewModelScope.launch {
            repository.getItems().collect {
                _items.postValue(it)
            }
        }
    }

    fun insertItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertItem(item)
        }
    }

    fun deleteItem(id: ObjectId) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteItem(id)
        }

    }

}