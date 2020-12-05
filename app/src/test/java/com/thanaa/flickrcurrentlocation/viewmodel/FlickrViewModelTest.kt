package com.thanaa.flickrcurrentlocation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations

class FlickrViewModelTest {
    private lateinit var viewModel: FlickrViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        @InjectMocks
        viewModel = FlickrViewModel()
        MockitoAnnotations.initMocks(this)

    }

    @Test
    fun `insert valid lan and long, returns result `() {
        viewModel.getPhotos("24.8539523", "46.7133915")

        val value = viewModel.photosLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert valid lan and long d, returns result `() {
        viewModel.getPhotos("0.0", "0.0")

        val value = viewModel.photosLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert empty lan and long, returns null `() {

        viewModel.getPhotos("", "")
        val value = viewModel.photosLiveData.getOrAwaitValue()
        // assertThat(value).isEqualTo(Experimental.Level.ERROR)
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert empty id in getPhotoInfo(), returns null `() {
        viewModel.getPhotoInfo("")
        val value = viewModel.photoInfoLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert empty id in getGeoLocation(), returns null `() {
        viewModel.getGeoLocation("")
        val value = viewModel.locationLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }


}

