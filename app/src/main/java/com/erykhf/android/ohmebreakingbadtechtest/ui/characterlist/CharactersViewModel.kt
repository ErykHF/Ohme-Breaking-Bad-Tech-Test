package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.lifecycle.*
import com.erykhf.android.ohmebreakingbadtechtest.data.source.BreakingError
import com.erykhf.android.ohmebreakingbadtechtest.data.source.Repository
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
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


    init {
        getAllCharacters()
    }


    private fun getAllCharacters() = viewModelScope.launch {
        try {
            _spinner.postValue(true)
            val response = repository.loadBBCharacters()
            _characters.postValue(response)
        } catch (error: BreakingError) {
            _errorText.postValue(error.message)
        } finally {
            _spinner.postValue(false)
        }
    }

    fun onErrorTextShown() {
        _errorText.value = null
    }


    fun filterSeasons(
        filterSeasons: FilterSeasons
    ): List<BreakingBadCharacterItem> {
        // filter the seasons
        val season = characters
        val filteredTasks = if (filterSeasons == FilterSeasons.ALL_SEASONS) {
            season.value!!
        } else {
            season.value!!
        }

        return when (filterSeasons) {

            FilterSeasons.ALL_SEASONS -> filteredTasks.filter { characters -> characters.appearance.any { it == 1 || it == 2 || it == 3 || it == 4 || it == 5 } }
            FilterSeasons.SEASON_ONE -> filteredTasks.filter { characters -> characters.appearance.any { it == 1 } }
            FilterSeasons.SEASON_TWO -> filteredTasks.filter { characters -> characters.appearance.any { it == 2 } }
            FilterSeasons.SEASON_THREE -> filteredTasks.filter { characters -> characters.appearance.any { it == 3 } }
            FilterSeasons.SEASON_FOUR -> filteredTasks.filter { characters -> characters.appearance.any { it == 4 } }
            FilterSeasons.SEASON_FIVE -> filteredTasks.filter { character -> character.appearance.any { it == 5 } }
        }
    }
}
