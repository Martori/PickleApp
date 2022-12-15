package cat.martori.pickleapp.ui.viewModels

import cat.martori.pickleapp.MainDispatcherRule
import cat.martori.pickleapp.domain.entities.Status
import cat.martori.pickleapp.domain.usecases.GetCharactersListUseCase
import cat.martori.pickleapp.domain.usecases.RequestCharactersListUseCase
import cat.martori.pickleapp.ui.models.CharacterItemModel
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getCharactersListUseCase = mockk<GetCharactersListUseCase>(relaxed = true)
    private val requestCharactersListUseCase = mockk<RequestCharactersListUseCase>(relaxed = true)
    private val navigator = mockk<Navigator<CharacterDestination>>(relaxed = true)

    private lateinit var sut: CharactersListViewModel

    @Before
    fun setUp() {
        sut = CharactersListViewModel(getCharactersListUseCase, requestCharactersListUseCase, navigator)
    }

    @Test
    fun `OpenCharacterDetails action should trigger navigation with that character id`() {
        val id = 1
        val characterItemModel = CharacterItemModel(id, "name", "species", "url", Status.Alive)

        sut.act(CharacterListAction.OpenCharacterDetails(characterItemModel))

        verify {
            navigator.navigate(CharacterDestination.Details(id))
        }
    }
}