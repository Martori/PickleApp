package cat.martori.pickleapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.martori.pickleapp.domain.usecases.GetCharactersListUseCase
import cat.martori.pickleapp.domain.usecases.RequestCharactersListUseCase
import cat.martori.pickleapp.ui.composables.CharactersListState
import cat.martori.pickleapp.ui.models.CharacterItemModel
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface CharacterListAction {

    fun transformState(state: CharactersListState): CharactersListState = state

    object DismissError : CharacterListAction {
        override fun transformState(state: CharactersListState) = state.copy(error = null)
    }

    data class RequestMoreCharters(val currentAmount: Int) : CharacterListAction

    data class OpenCharacterDetails(val model: CharacterItemModel) : CharacterListAction

}

class CharactersListViewModel(
    getCharactersList: GetCharactersListUseCase,
    private val requestCharacters: RequestCharactersListUseCase,
    private val navigator: Navigator<CharacterDestination>,
) : ViewModel() {

    private val _actions = MutableSharedFlow<CharacterListAction>()

    private val actions = _actions
        .onStart { emit(CharacterListAction.RequestMoreCharters(0)) }
        .onEach { processAction(it) }
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val state = getCharactersList()
        .runningFold(CharactersListState.DEFAULT, CharactersListState::applyResult)
        .combine(actions) { state, action ->
            action.transformState(state)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), CharactersListState.DEFAULT)

    private suspend fun processAction(action: CharacterListAction) = when (action) {
        is CharacterListAction.RequestMoreCharters -> requestCharacters(action.currentAmount)
        is CharacterListAction.OpenCharacterDetails -> navigator.navigate(CharacterDestination.Details(action.model.id))
        else -> {}
    }

    fun act(action: CharacterListAction) = viewModelScope.launch {
        _actions.emit(action)
    }

}