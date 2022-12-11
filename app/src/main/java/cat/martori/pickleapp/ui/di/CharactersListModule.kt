package cat.martori.pickleapp.ui.di

import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val uiModule = module {
    viewModelOf(::CharactersListViewModel)
}