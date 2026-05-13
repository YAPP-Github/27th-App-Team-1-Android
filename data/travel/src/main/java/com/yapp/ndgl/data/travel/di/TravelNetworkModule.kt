package com.yapp.ndgl.data.travel.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yapp.ndgl.data.core.di.ExchangeRateApiKey
import com.yapp.ndgl.data.core.di.ExchangeRateClient
import com.yapp.ndgl.data.core.di.GeocodingClient
import com.yapp.ndgl.data.core.di.RouteApiKey
import com.yapp.ndgl.data.core.di.RouteBaseUrl
import com.yapp.ndgl.data.core.di.RouteClient
import com.yapp.ndgl.data.core.di.WeatherApiKey
import com.yapp.ndgl.data.core.di.WeatherClient
import com.yapp.ndgl.data.core.di.YoutubeOembedClient
import com.yapp.ndgl.data.travel.BuildConfig
import com.yapp.ndgl.data.travel.api.ExchangeRateApi
import com.yapp.ndgl.data.travel.api.GeocodingApi
import com.yapp.ndgl.data.travel.api.PlaceApi
import com.yapp.ndgl.data.travel.api.RouteApi
import com.yapp.ndgl.data.travel.api.TravelProgramApi
import com.yapp.ndgl.data.travel.api.TravelTemplateApi
import com.yapp.ndgl.data.travel.api.UserTravelApi
import com.yapp.ndgl.data.travel.api.WeatherApi
import com.yapp.ndgl.data.travel.api.YoutubeOembedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class YoutubeOembedClient

@Module
@InstallIn(SingletonComponent::class)
object TravelNetworkModule {
    private const val ROUTES_BASE_URL = "https://routes.googleapis.com/"
    private const val WEATHER_BASE_URL = "https://weather.googleapis.com/"
    private const val GEOCODING_BASE_URL = "https://maps.googleapis.com/"
    private const val EXCHANGE_RATE_BASE_URL = "https://v6.exchangerate-api.com/"
    private const val YOUTUBE_OEMBED_BASE_URL = "https://www.youtube.com/"

    @Provides
    @Singleton
    fun providePlacesClient(
        @ApplicationContext context: Context,
    ): PlacesClient {
        if (!Places.isInitialized()) {
            Places.initializeWithNewPlacesApiEnabled(context, BuildConfig.PLACE_API_KEY)
        }
        return Places.createClient(context)
    }

    @RouteApiKey
    @Provides
    @Singleton
    fun provideRouteApiKey(): String = BuildConfig.ROUTE_API_KEY

    @WeatherApiKey
    @Provides
    @Singleton
    fun provideWeatherApiKey(): String = BuildConfig.WEATHER_API_KEY

    @WeatherClient
    @Provides
    @Singleton
    fun provideWeatherRetrofit(
        @WeatherClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(@WeatherClient retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @GeocodingClient
    @Provides
    @Singleton
    fun provideGeocodingRetrofit(
        @WeatherClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(GEOCODING_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideGeocodingApi(@GeocodingClient retrofit: Retrofit): GeocodingApi =
        retrofit.create(GeocodingApi::class.java)

    @RouteBaseUrl
    @Provides
    @Singleton
    fun provideRouteBaseUrl(): String = ROUTES_BASE_URL

    @Provides
    @Singleton
    fun provideTravelProgramApi(
        retrofit: Retrofit,
    ): TravelProgramApi = retrofit.create(TravelProgramApi::class.java)

    @Provides
    @Singleton
    fun provideTravelTemplateApi(
        retrofit: Retrofit,
    ): TravelTemplateApi = retrofit.create(TravelTemplateApi::class.java)

    @Provides
    @Singleton
    fun provideUserTravelApi(
        retrofit: Retrofit,
    ): UserTravelApi = retrofit.create(UserTravelApi::class.java)

    @Provides
    @Singleton
    fun providePlaceApi(
        retrofit: Retrofit,
    ): PlaceApi = retrofit.create(PlaceApi::class.java)

    @RouteClient
    @Provides
    @Singleton
    fun provideRouteRetrofit(
        @RouteClient okHttpClient: OkHttpClient,
        @RouteBaseUrl baseUrl: String,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideRouteApi(
        @RouteClient retrofit: Retrofit,
    ): RouteApi = retrofit.create(RouteApi::class.java)

    @ExchangeRateApiKey
    @Provides
    @Singleton
    fun provideExchangeRateApiKey(): String = BuildConfig.EXCHANGE_RATE_API_KEY

    @ExchangeRateClient
    @Provides
    @Singleton
    fun provideExchangeRateRetrofit(
        @ExchangeRateClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(EXCHANGE_RATE_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideExchangeRateApi(
        @ExchangeRateClient retrofit: Retrofit,
    ): ExchangeRateApi = retrofit.create(ExchangeRateApi::class.java)

    @YoutubeOembedClient
    @Provides
    @Singleton
    fun provideYoutubeOembedRetrofit(
        @YoutubeOembedClient okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(YOUTUBE_OEMBED_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideYoutubeOembedApi(
        @YoutubeOembedClient retrofit: Retrofit,
    ): YoutubeOembedApi = retrofit.create(YoutubeOembedApi::class.java)
}
