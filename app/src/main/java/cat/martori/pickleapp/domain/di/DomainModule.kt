package cat.martori.pickleapp.domain.di

import cat.martori.pickleapp.domain.usecases.GetCharactersListUseCase
import cat.martori.pickleapp.domain.usecases.RequestCharactersListUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetCharactersListUseCase)
    singleOf(::RequestCharactersListUseCase)
}