package cat.martori.pickleapp

import cat.martori.pickleapp.data.di.dataModule
import cat.martori.pickleapp.domain.di.domainModule
import cat.martori.pickleapp.ui.di.uiModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.koin.test.check.checkKoinModules

@OptIn(ExperimentalCoroutinesApi::class)
class DITest {

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun verifyDependencyGraph() {
        checkKoinModules(listOf(uiModule, dataModule, domainModule)) {
            withInstance(0) //Ids
        }
    }

}