package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.ohmebreakingbadtechtest.data.source.BreakingError
import com.erykhf.android.ohmebreakingbadtechtest.data.source.Repository
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean> = _spinner

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private val _characters = MutableLiveData<List<BreakingBadCharacterItem>?>()
    val characters: LiveData<List<BreakingBadCharacterItem>?> = _characters


    init {
        getAllCharacters()
    }

    fun getAllCharacters() = viewModelScope.launch {
        try {
            _spinner.postValue(true)
            val response = repository.loadBBCharacters()
            _characters.postValue(response)
        }catch (error: BreakingError){
            _errorText.postValue(error.message)
        }finally {
            _spinner.postValue(false)
        }
    }

    fun onErrorTextShown() {
        _errorText.value = null
    }

}