package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.lifecycle.*
import com.erykhf.android.ohmebreakingbadtechtest.data.source.Repository
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean> = _spinner

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private val _characters = MutableLiveData<List<BreakingBadCharacterItem>?>()
    val characters: LiveData<List<BreakingBadCharacterItem>?> = _characters

    private var chachedCharacters = listOf<BreakingBadCharacterItem>()
    private var ifFiltering = true


    fun filterSeasons(query: Int){
        val listToSearch = if (ifFiltering){
            _characters.value
        } else {
             chachedCharacters
        }
        viewModelScope.launch {
            if (query == 0){
                _characters.value = chachedCharacters
                return@launch
            }
            val results = listToSearch?.filter {
                it.appearance.any { it == query }
            }
            if (ifFiltering){
                chachedCharacters = _characters.value!!
                ifFiltering = false
            }
            _characters.value = results
        }
    }


    init {
        getAllCharacters()
    }

    fun getAllCharacters() {
        viewModelScope.launch {
            _spinner.value = true
            val result = repository.loadBBCharacters()
            when (result) {
                is Resource.Success -> {
                    _characters.postValue(result.data)
                    _spinner.value = false
                }
                is Resource.Error -> {
                    _errorText.value = result.message
                    _spinner.value = false
                }

            }
        }
    }


    fun onErrorTextShown() {
        _errorText.value = null
    }

}