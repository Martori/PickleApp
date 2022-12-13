package cat.martori.pickleapp.data.di

import cat.martori.pickleapp.data.repositories.RetrofitCharactersRepository
import cat.martori.pickleapp.data.services.CharacterApiService
import cat.martori.pickleapp.domain.repositories.CharactersRepository
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val dataModule = module {
    singleOf(::RetrofitCharactersRepository).bind<CharactersRepository>()

    single {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }).build()

        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(client)
            .build()
    }

    single {
        get<Retrofit>().create<CharacterApiService>()
    }

}