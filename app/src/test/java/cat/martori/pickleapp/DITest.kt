package cat.martori.pickleapp

import cat.martori.pickleapp.data.di.dataModule
import cat.martori.pickleapp.domain.di.domainModule
import cat.martori.pickleapp.ui.di.uiModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.koin.test.check.checkKoinModules

@OptIn(ExperimentalCoroutinesApi::class)
class DITest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun verifyDependencyGraph() {
        checkKoinModules(listOf(uiModule, dataModule, domainModule)) {
            withInstance(0) //Ids
        }
    }

}