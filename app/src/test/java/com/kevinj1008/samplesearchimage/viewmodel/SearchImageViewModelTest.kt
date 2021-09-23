package com.kevinj1008.samplesearchimage.viewmodel

import com.kevinj1008.base.utils.Result
import com.kevinj1008.samplesearchimage.entity.ImageEntity
import com.kevinj1008.samplesearchimage.usecase.searchimage.SearchImageUseCase
import com.kevinj1008.testcore.BaseTest
import com.kevinj1008.testcore.utils.getOrAwaitValue
import com.kevinj1008.testcore.utils.observeForTesting
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Assert.*

import org.junit.Test

class SearchImageViewModelTest : BaseTest() {
    private val useCaseTest = mockk<SearchImageUseCase>()
    private val viewModelTest = SearchImageViewModel(useCaseTest)

    @Test
    fun searchImage_getImageSuccess_returnImageList() {
        //Arrange
        val keyword = ""
        val imageList = mockk<List<ImageEntity>>(relaxed = true)
        val result = Result.Success(imageList)
        coEvery { useCaseTest.getImages(keyword) } returns result

        //Act
        viewModelTest.searchImage(keyword)

        //Assert
        coVerify { useCaseTest.getImages(keyword) }
        viewModelTest.imageList.observeForTesting {
            assertEquals(it.value, imageList)
        }
    }

    @Test
    fun searchImage_getImageFailed_returnError() {
        //Arrange
        val keyword = ""
        val throwable = mockk<Throwable>(relaxed = true)
        val result = Result.Error(throwable)
        coEvery { useCaseTest.getImages(keyword) } returns result

        //Act
        viewModelTest.searchImage(keyword)

        //Assert
        coVerify {
            useCaseTest.getImages(keyword)
        }
        assertEquals(
            viewModelTest.loadingError.getOrAwaitValue().peekContent().exception,
            throwable
        )
    }

    @Test
    fun fetchNextPage_fetchSuccess_returnImageList() {
        //Arrange
        val keyword = ""
        val page = ""
        val imageList = mockk<List<ImageEntity>>(relaxed = true)
        val result = Result.Success(imageList)
        coEvery { useCaseTest.fetchNextPage(keyword, page) } returns result

        //Act
        viewModelTest.fetchNextPage(keyword, page)

        //Assert
        coVerify {
            useCaseTest.fetchNextPage(keyword, page)
        }
        viewModelTest.imageList.observeForTesting {
            assertEquals(it.value, imageList)
        }
    }

    @Test
    fun fetchNextPage_fetchFailed_returnError() {
        //Arrange
        val keyword = ""
        val page = ""
        val throwable = mockk<Throwable>(relaxed = true)
        val isFetching = true
        val result = Result.Error(throwable, isFetching)
        coEvery { useCaseTest.fetchNextPage(keyword, page) } returns result

        //Act
        viewModelTest.fetchNextPage(keyword, page)

        //Assert
        coVerify {
            useCaseTest.fetchNextPage(keyword, page)
        }
        assertEquals(
            viewModelTest.loadingError.getOrAwaitValue().peekContent().exception,
            throwable
        )
        assertEquals(
            viewModelTest.loadingError.getOrAwaitValue().peekContent().isFetching,
            isFetching
        )
    }
}