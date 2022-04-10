package com.app.acromine.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.app.acromine.model.AbbreviationModel
import com.app.acromine.repository.AbbreviationRepository

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AbbreviationListViewModelTest : TestCase() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiRepo: AbbreviationRepository

    @Mock
    private lateinit var apiObserver: Observer<List<AbbreviationModel>>

    lateinit var viewModel : AbbreviationListViewModel


    @Before
    public override fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = AbbreviationListViewModel(apiRepo)

    }

    @Test
    fun getAllAbbriviationData_Success() {
        testCoroutineRule.runBlockingTest {
            doReturn(emptyList<AbbreviationModel>())
                .`when`(apiRepo)
                .getAllAbbreviations("HMM","Hidden Markov Model")
            viewModel.getAbbreviationData().observeForever(apiObserver)
            verify(apiRepo).getAllAbbreviations("HMM","HMM")
            verify(apiObserver).onChanged(emptyList())
            viewModel.getAbbreviationData().removeObserver(apiObserver)
        }
    }

    @Test
    fun getAllAbbriviationData_Failure() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiRepo)
                .getAllAbbreviations("","")
            viewModel.getAbbreviationData().observeForever(apiObserver)
            verify(apiRepo).getAllAbbreviations("","")
            verify(apiObserver).onChanged(null)
            viewModel.getAbbreviationData().removeObserver(apiObserver)
        }
    }
}