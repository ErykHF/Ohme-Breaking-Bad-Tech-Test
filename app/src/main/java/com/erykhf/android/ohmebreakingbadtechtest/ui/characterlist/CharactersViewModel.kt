package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.lifecycle.*
import com.erykhf.android.ohmebreakingbadtechtest.data.source.Repository
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist.FilterSeasons.*
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

    val season = MutableLiveData<FilterSeasons>()

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText

    private val _characters = MutableLiveData<List<BreakingBadCharacterItem>?>()
    val characters: LiveData<List<BreakingBadCharacterItem>?> =
        _characters.map { seasons ->

            when (season.value) {
                ALL_SEASONS -> {
                    seasons
                }
                SEASON_ONE -> seasons?.filter {
                    it.appearance.any { it == 1 }
                }
                SEASON_TWO -> seasons?.filter {
                    it.appearance.any { it == 2 }
                }
                SEASON_THREE -> seasons?.filter {
                    it.appearance.any { it == 3 }
                }
                SEASON_FOUR -> seasons?.filter {
                    it.appearance.any { it == 4 }
                }
                SEASON_FIVE -> seasons?.filter {
                    it.appearance.any { it == 5 }
                }
                else -> {
                    seasons
                }
            }
        }


    init {
        getAllCharacters()
        season.value = ALL_SEASONS
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