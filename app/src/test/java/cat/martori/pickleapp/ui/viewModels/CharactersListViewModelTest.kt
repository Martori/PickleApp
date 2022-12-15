package cat.martori.pickleapp.ui.viewModels

import cat.martori.pickleapp.MainDispatcherRule
import cat.martori.pickleapp.domain.entities.*
import cat.martori.pickleapp.domain.usecases.GetCharactersListUseCase
import cat.martori.pickleapp.domain.usecases.RequestCharactersListUseCase
import cat.martori.pickleapp.ui.composables.CharactersListState
import cat.martori.pickleapp.ui.models.CharacterItemModel
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val characterListFlow = MutableSharedFlow<Result<CharacterList>>()

    private val getCharactersListUseCase = mockk<GetCharactersListUseCase>(relaxed = true) {
        every { this@mockk.invoke() } returns characterListFlow
    }
    private val requestCharactersListUseCase = mockk<RequestCharactersListUseCase>(relaxed = true)
    private val navigator = mockk<Navigator<CharacterDestination>>(relaxed = true)

    private lateinit var sut: CharactersListViewModel

    private val locationSummary = LocationSummary(1, "fake", "fake/1")
    private val episodeSummary = EpisodeSummary(1, "fake/1")
    private val testCharacter = CharacterSummary(
        id = 0,
        name = "fake",
        imageUrl = "fake",
        status = Status.Alive,
        species = "fake",
        type = "fake",
        gender = "fake",
        origin = locationSummary,
        currentLocation = locationSummary,
        firstEpisode = episodeSummary,
    )
    private val characterItemModel = CharacterItemModel(
        id = 0,
        name = "fake",
        species = "fake",
        avatarUrl = "fake",
        status = Status.Alive,
    )

    @Before
    fun setUp() {
        sut = CharactersListViewModel(getCharactersListUseCase, requestCharactersListUseCase, navigator)
    }

    @Test
    fun `the characters list should be requested initially with a quantity of 0`() {

        coVerify {
            requestCharactersListUseCase(0)
        }

    }

    @Test
    fun `if the result is a failure the state should have an error`() = runTest {

        assertWithStateFlow { state ->

            characterListFlow.emit(Result.failure(Error("test")))

            assertNotNull(state.value.error)

        }

    }

    @Test
    fun `if the result is succeeds and the size is smaller than the count state should be loading`() = runTest {
        assertWithStateFlow { state ->
            characterListFlow.emit(Result.success(CharacterList(emptyList(), 2)))

            assert(state.value.loading)
        }
    }

    @Test
    fun `if the result is succeeds and the size is the same as the count state should not be loading`() = runTest {
        assertWithStateFlow { state ->
            characterListFlow.emit(Result.success(CharacterList(listOf(testCharacter), 1)))

            assertFalse(state.value.loading)
        }
    }

    @Test
    fun `if result succeds it should map to the character view model`() = runTest {
        assertWithStateFlow { state ->

            characterListFlow.emit(Result.success(CharacterList(listOf(testCharacter), 1)))

            val result = state.value

            assertEquals(characterItemModel, result.characters.first())

        }
    }

    @Test
    fun `if clear error is received and there is no error it should have no effect`() = runTest {
        assertWithStateFlow { state ->

            val expected = state.value

            sut.act(CharacterListAction.DismissError)

            val result = state.value

            assertEquals(expected, result)
        }
    }

    @Test
    fun `if clear error is received and there is and error it should clear the error`() = runTest {
        assertWithStateFlow { state ->

            characterListFlow.emit(Result.failure(Error("test error")))

            assertNotNull(state.value.error)

            sut.act(CharacterListAction.DismissError)

            assertNull(state.value.error)
        }
    }

    @Test
    fun `when more characters are requested the request use case should be called with the current amount`() {
        val amount = 20
        sut.act(CharacterListAction.RequestMoreCharters(amount))

        coVerify {
            requestCharactersListUseCase(amount)
        }
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


    private inline fun TestScope.assertWithStateFlow(assertWithFlow: (StateFlow<CharactersListState>) -> Unit) {
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect()
        }

        assertWithFlow(sut.state)

        collectJob.cancel()
    }

}