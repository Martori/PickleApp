package cat.martori.pickleapp.ui.viewModels

import androidx.lifecycle.ViewModel
import cat.martori.pickleapp.domain.usecases.GetCharacterDetailsUseCase
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination

sealed interface CharacterDetailsAction {

    object GoBack : CharacterDetailsAction

}

class CharacterDetailsViewModel(
    private val id: Int,
    private val getDetails: GetCharacterDetailsUseCase,
    private val navigator: Navigator<CharacterDestination>,
) : ViewModel() {



    fun act(action: CharacterDetailsAction) {
        when (action) {
            CharacterDetailsAction.GoBack -> navigator.backTo()
            else -> {}
        }
    }

}
