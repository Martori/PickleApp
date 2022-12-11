package cat.martori.pickleapp.characterList.di

import cat.martori.pickleapp.characterList.ui.viewModels.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterListModule = module {
    viewModel {
        CharactersListViewModel()
    }
}