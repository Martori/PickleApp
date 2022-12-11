package cat.martori.pickleapp.characterList.ui.viewModels

import androidx.lifecycle.ViewModel
import cat.martori.pickleapp.characterList.domain.Character
import cat.martori.pickleapp.characterList.domain.Status
import cat.martori.pickleapp.characterList.ui.models.toCharacterItemModel

class CharactersListViewModel : ViewModel() {

    val characters =
        listOf(
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.Dead),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.Unknown),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.ALive),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.Unknown),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.ALive),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.Dead),
            Character("Chris", "Alien", "https://rickandmortyapi.com/api/character/avatar/64.jpeg", Status.ALive),
        ).map { it.toCharacterItemModel() }

}