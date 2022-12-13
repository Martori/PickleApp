package cat.martori.pickleapp.ui.navigation.navGraphs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cat.martori.pickleapp.ui.composables.CharacterListScreen
import cat.martori.pickleapp.ui.navigation.AnimatedNavHost
import cat.martori.pickleapp.ui.navigation.Navigator
import cat.martori.pickleapp.ui.navigation.composable
import cat.martori.pickleapp.ui.navigation.destinations.CharacterDestination
import cat.martori.pickleapp.ui.navigation.rememberNavHostController
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavGraph(navigator: Navigator<CharacterDestination> = get(named<CharacterDestination>()), close: () -> Unit) {
    val navController = navigator.rememberNavHostController()

    BackHandler {
        navigator.backTo()
    }
    AnimatedNavHost(navController, CharacterDestination.List) {
        composable(CharacterDestination.List) {
            BackHandler {
                close()
            }
            CharacterListScreen()
        }
        composable(CharacterDestination.Details) { destination ->
            val id = destination.arguments?.getString(CharacterDestination.Details.characterIdArg)?.toInt()
            Text("Character id = $id")
        }
    }

}