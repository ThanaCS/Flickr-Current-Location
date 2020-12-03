package com.thanaa.flickrcurrentlocation.di

import com.thanaa.flickrcurrentlocation.api.FlickrService
import com.thanaa.flickrcurrentlocation.viewmodel.FlickrViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: FlickrService)
    fun inject(viewModel: FlickrViewModel)
}