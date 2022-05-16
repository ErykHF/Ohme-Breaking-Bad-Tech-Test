package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.lifecycle.*
import com.erykhf.android.ohmebreakingbadtechtest.EspressoTestingIdlingResource
import com.erykhf.android.ohmebreakingbadtechtest.data.source.repositories.Repository
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

    private var cachedCharacters = listOf<BreakingBadCharacterItem>()
    private var ifFiltering = true


    fun filterSeasons(query: Int) {
        val listToSearch = if (ifFiltering) {
            _characters.value
        } else {
            cachedCharacters
        }
        viewModelScope.launch {
            if (query == 0) {
                _characters.value = cachedCharacters
                return@launch
            }
            val results = listToSearch?.filter {
                it.appearance.any { season -> season == query }
            }
            if (ifFiltering) {
                cachedCharacters = _characters.value!!
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
            //Espresso for testing recyclerview
            _spinner.value = true
//            EspressoTestingIdlingResource.increment()
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
//            EspressoTestingIdlingResource.decrement()
        }
    }


    fun onErrorTextShown() {
        _errorText.value = null
    }

}