package cat.martori.pickleapp.data.di

import cat.martori.pickleapp.data.CharacterApiService
import cat.martori.pickleapp.data.RetrofitCharactersRepository
import cat.martori.pickleapp.domain.CharactersRepository
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {
    singleOf(::RetrofitCharactersRepository).bind<CharactersRepository>()

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create<CharacterApiService>()
    }

}