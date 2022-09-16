package com.apolloagriculture.android.takehomeassignment.ui.viewmodel

import org.junit.Rule
import org.junit.Test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk


internal class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mockk<Repository>()
    private val viewModel = MainViewModel(repository)

    @Test
    fun `when view model is created, then it should fetch the data`() {
        coEvery { repository.fetchData() } returns Unit

        viewModel.onCreate()

        coVerify { repository.fetchData() }
    }


}