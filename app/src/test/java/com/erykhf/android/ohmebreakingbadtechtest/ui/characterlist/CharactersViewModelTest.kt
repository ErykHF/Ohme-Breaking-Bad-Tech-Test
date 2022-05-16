package com.erykhf.android.ohmebreakingbadtechtest.ui.characterlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.erykhf.android.ohmebreakingbadtechtest.CoroutineTestRule
import org.mockito.Mockito.`when`
import com.erykhf.android.ohmebreakingbadtechtest.data.source.repositories.Repository
import com.erykhf.android.ohmebreakingbadtechtest.getOrAwaitValue
import com.erykhf.android.ohmebreakingbadtechtest.model.BreakingBadCharacterItem
import com.erykhf.android.ohmebreakingbadtechtest.util.Resource
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CharactersViewModelTest {


    private lateinit var viewModel: CharactersViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    open val coroutineTestRule = CoroutineTestRule()

    var repository = mockk<Repository>()

    private var observer = mockk<Observer<List<BreakingBadCharacterItem>?>>()

    private var characterList = mockk<List<BreakingBadCharacterItem>>()


    @Before
    fun setup() {
        viewModel = CharactersViewModel(repository)
        MockitoAnnotations.initMocks(this)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `This does not work `() {

        coroutineTestRule.dispatcher.runBlockingTest {


            val dummyList = Resource.Success(characterList)
            `when`(dummyList.data?.size).thenReturn(5)
            val characters = MutableLiveData<Resource<List<BreakingBadCharacterItem>>>()
            characters.value = dummyList

            `when`(repository.loadBBCharacters()).thenReturn(characters.value)
            val characterEntities = viewModel.characters.getOrAwaitValue()
            verify(repository).loadBBCharacters()
            assertNotNull(characterEntities)
            assertEquals(5, characterEntities?.equals(5))

            viewModel.characters.observeForever(observer)
            com.nhaarman.mockitokotlin2.verify(observer).onChanged(dummyList.data)

        }
    }

    @Test
    fun `Neither does this`() = coroutineTestRule.dispatcher.runBlockingTest {

        val characters = viewModel.characters.getOrAwaitValue()

        assertEquals("Walter White", characters?.first()?.name)


    }

    @Test
    fun `Or this`() = coroutineTestRule.dispatcher.runBlockingTest {

        val characterlist = viewModel.characters.getOrAwaitValue()
        viewModel.characters.observeForever(observer)

        assertEquals(20, characterlist?.size)
    }

}
