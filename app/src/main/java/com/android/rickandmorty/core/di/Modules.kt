package com.android.rickandmorty.core.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.android.rickandmorty.data.api_client.RetrofitClient
import com.android.rickandmorty.data.character.api.CharacterApi
import com.android.rickandmorty.data.character.mediator.CharacterRemoteMediator
import com.android.rickandmorty.data.character.repository.CharacterRepository
import com.android.rickandmorty.data.character.repository.CharacterRepositoryImpl
import com.android.rickandmorty.data.database.CharacterDatabase
import com.android.rickandmorty.domain.character.use_cases.GetAllCharactersUseCase
import com.android.rickandmorty.domain.character.use_cases.GetDetailCharacterItemByIdUseCase
import com.android.rickandmorty.domain.character.use_cases.GetFavoriteCharacterListItemsUseCase
import com.android.rickandmorty.domain.character.use_cases.SwitchCharacterIsFavoriteUseCase
import com.android.rickandmorty.presentation.all_characters.AllCharactersViewModel
import com.android.rickandmorty.presentation.detail_character.DetailCharacterViewModel
import com.android.rickandmorty.presentation.favorite_characters.FavoriteCharactersViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val retrofitModule = module {
    single<Retrofit> { RetrofitClient.getClient() }
}

val remoteSourceModule = module {
    single<CharacterApi> { get<Retrofit>().create(CharacterApi::class.java) }
}

val repositoryModule = module {
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
}

val localDatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), CharacterDatabase::class.java, CharacterDatabase.CHARACTER_DATABASE_NAME
        ).build()
    }
}

val daoModule = module {
    single { get<CharacterDatabase>().getCharacterDao() }
    single { get<CharacterDatabase>().getRemoteKeyDao() }
}

val pagingDataSourceModule = module {
    single { CharacterRemoteMediator(get(), get()) }
}

val useCaseModule = module {
    single { SwitchCharacterIsFavoriteUseCase(get()) }
    single { GetAllCharactersUseCase(get()) }
    single { GetDetailCharacterItemByIdUseCase(get()) }
    single { GetFavoriteCharacterListItemsUseCase(get()) }
}

val viewModelModule = module {
    viewModel { AllCharactersViewModel(androidContext(), get(), get()) }
    viewModel { FavoriteCharactersViewModel(androidContext(), get(), get()) }
    viewModel { (_: SavedStateHandle) ->
        DetailCharacterViewModel(get(), androidContext(), get(), get())
    }
}