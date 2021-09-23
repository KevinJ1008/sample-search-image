package com.kevinj1008.samplesearchimage.viewmodel

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.DisplayMode
import com.kevinj1008.samplesearchimage.usecase.input.InputUseCase
import com.kevinj1008.testcore.BaseTest
import com.kevinj1008.testcore.utils.observeForTesting
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*

import org.junit.Test

class InputViewModelTest : BaseTest() {
    private val useCaseTest = mockk<InputUseCase>()
    private val viewModelTest = InputViewModel(useCaseTest)

    @Test
    fun getDisplayMode_alwaysReturnResultSuccess_returnDisplayMode() {
        //Arrange
        val displayMode = mockk<DisplayMode>()
        val result = Result.Success(displayMode)
        coEvery { useCaseTest.getDisplayMode() } returns result

        //Act
        viewModelTest.getDisplayMode()

        //Assert
        viewModelTest.displayMode.observeForTesting {
            assertEquals(it.value, displayMode)
        }
        coVerify {
            useCaseTest.getDisplayMode()
        }
    }

    @Test
    fun getSearchHistory_alwaysReturnResultSuccess_returnSearchHistory() {
        //Arrange
        val searchHistory = mockk<List<String>>(relaxed = true)
        val result = Result.Success(searchHistory)
        coEvery { useCaseTest.getSearchHistory() } returns result

        //Act
        viewModelTest.getSearchHistory()

        //Assert
        viewModelTest.searchHistory.observeForTesting {
            assertEquals(it.value, searchHistory)
        }
    }

    @Test
    fun saveSearchHistory() {
        //Arrange
        val keyword = ""
        coEvery { useCaseTest.saveSearchHistory(keyword) } just Runs

        //Act
        viewModelTest.saveSearchHistory(keyword)

        //Assert
        coVerify {
            useCaseTest.saveSearchHistory(keyword)
        }
    }
}