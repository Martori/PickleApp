package cat.martori.pickleapp.ui.di

import cat.martori.pickleapp.ui.navigation.FlowNavigator
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import cat.martori.pickleapp.ui.viewModels.CharactersListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val uiModule = module {
    viewModel {
        CharactersListViewModel(get(), get(), get(named<CharacterDestination>()))
    }
    single<Navigator<CharacterDestination>>(named<CharacterDestination>()) { FlowNavigator() }
}