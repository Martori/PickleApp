package cat.martori.pickleapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.martori.pickleapp.domain.usecases.GetCharacterDetailsUseCase
import cat.martori.pickleapp.ui.composables.CharacterDetailsState
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface CharacterDetailsAction {

    fun transformState(state: CharacterDetailsState): CharacterDetailsState = state

    object NoOp : CharacterDetailsAction

    object GoBack : CharacterDetailsAction

}

class CharacterDetailsViewModel(
    id: Int,
    getDetails: GetCharacterDetailsUseCase,
    private val navigator: Navigator<CharacterDestination>,
) : ViewModel() {

    private val _actions = MutableSharedFlow<CharacterDetailsAction>()

    private val actions = _actions
        .onStart { emit(CharacterDetailsAction.NoOp) }
        .onEach { processAction(it) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val state = getDetails(id)
        .runningFold(CharacterDetailsState.DEFAULT, CharacterDetailsState::applyResult)
        .combine(actions) { state, action ->
            action.transformState(state)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, CharacterDetailsState.DEFAULT)

    private fun processAction(action: CharacterDetailsAction) = when (action) {
        CharacterDetailsAction.GoBack -> navigator.backTo()
        else -> {}
    }

    fun act(action: CharacterDetailsAction) = viewModelScope.launch {
        _actions.emit(action)
    }

}
