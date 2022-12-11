package cat.martori.pickleapp.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.martori.pickleapp.domain.GetCharactersListUseCase
import cat.martori.pickleapp.domain.RequestCharactersListUseCase
import cat.martori.pickleapp.ui.models.toCharacterItemModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CharactersListViewModel(
    private val getCharactersList: GetCharactersListUseCase,
    private val requestCharacters: RequestCharactersListUseCase,
) : ViewModel() {

    val characters =
        getCharactersList()
            .map { result ->
                result.fold({
                    it
                }, {
                    Log.e("CharactersListViewModel", "error: ", it)
                    emptyList()
                })
            }
            .map { list -> list.map { it.toCharacterItemModel() } }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            requestCharacters()
        }
    }

}