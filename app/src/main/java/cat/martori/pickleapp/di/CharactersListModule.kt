package cat.martori.pickleapp.di

import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterListModule = module {
    viewModel {
        CharactersListViewModel()
    }
}