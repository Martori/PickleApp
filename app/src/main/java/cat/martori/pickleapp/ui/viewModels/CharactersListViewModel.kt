package cat.martori.pickleapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.martori.pickleapp.domain.GetCharactersListUseCase
import cat.martori.pickleapp.domain.RequestCharactersListUseCase
import cat.martori.pickleapp.ui.composables.CharactersListState
import cat.martori.pickleapp.ui.models.toCharacterItemModel
import kotlinx.coroutines.flow.*

sealed interface CharacterListAction {

    fun transformState(state: CharactersListState): CharactersListState = state

    object NoOp : CharacterListAction

    object DismissError : CharacterListAction {
        override fun transformState(state: CharactersListState) = state.copy(error = null)
    }

    data class RequestMoreCharters(val currentAmount: Int) : CharacterListAction

}

class CharactersListViewModel(
    private val getCharactersList: GetCharactersListUseCase,
    private val requestCharacters: RequestCharactersListUseCase,
) : ViewModel() {

    private val actions = MutableStateFlow<CharacterListAction>(CharacterListAction.RequestMoreCharters(0))
        .onEach {
            when (it) {
                is CharacterListAction.RequestMoreCharters -> requestCharacters(it.currentAmount)
                else -> {}
            }
        }

    val state =
        getCharactersList()
            .runningFold(CharactersListState.DEFAULT) { state, result ->
                state.copy(characters = result.map { it.toCharacterItemModel() }.getOrElse { state.characters }, error = result.exceptionOrNull())
            }
            .combine(actions) { state, action ->
                action.transformState(state)
            }
            .stateIn(viewModelScope, SharingStarted.Lazily, CharactersListState.DEFAULT)

}