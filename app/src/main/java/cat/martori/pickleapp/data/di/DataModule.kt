package cat.martori.pickleapp.data.di

import cat.martori.pickleapp.data.RetrofitCharactersRepository
import cat.martori.pickleapp.domain.CharactersRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::RetrofitCharactersRepository).bind<CharactersRepository>()
}