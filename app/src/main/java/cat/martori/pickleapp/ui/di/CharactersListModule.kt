package cat.martori.pickleapp.ui.di

import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val uiModule = module {
    viewModel {
        CharactersListViewModel()
    }
}