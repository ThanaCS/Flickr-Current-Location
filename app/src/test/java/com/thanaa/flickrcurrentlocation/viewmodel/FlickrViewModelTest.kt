package com.thanaa.flickrcurrentlocation.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FlickrViewModelTest {
    private lateinit var viewModel: FlickrViewModel

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = FlickrViewModel()
    }

    @Test
    fun `insert valid lan and long , returns result `() {
        viewModel.getPhotos("24.8539523", "46.7133915")
        val value = viewModel.photosLiveData.getOrAwaitValue()
        print(value)
        assertThat(value.isNotEmpty())
    }

    @Test
    fun `insert valid lan and long test2, returns result `() {
        viewModel.getPhotos("0.0", "0.0")
        val value = viewModel.photosLiveData.getOrAwaitValue()
        print(value)
        assertThat(value.isNotEmpty())
    }

    @Test
    fun `insert empty lan and empty long, returns null `() {
        viewModel.getPhotos("", "46.7133915")
        val value = viewModel.photosLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert valid lan and empty long, returns null `() {
        viewModel.getPhotos("46.7133915", "")
        val value = viewModel.photosLiveData.getOrAwaitValue()
        assertThat(value).isEqualTo(null)
    }

    @Test
    fun `insert empty lan and valid long, returns null `() {
        viewModel.getPhotos("", "")
        val value = viewModel.photosLiveData.getOrAwaitValue()
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

    @Test
    fun `valid id in getGeoLocation(), returns result `() {
        viewModel.getGeoLocation("50680435646")
        val value = viewModel.locationLiveData.getOrAwaitValue()
        print(value)
        assertThat(value.region._content.isNotEmpty())
    }

}

