package cat.martori.pickleapp.ui.navigation.navGraphs

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cat.martori.pickleapp.ui.composables.CharacterDetailsScreen
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
    AnimatedNavHost(
        navController, CharacterDestination.List, Modifier.fillMaxSize(),
        enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)) },
    ) {
        composable(CharacterDestination.List) {
            BackHandler {
                close()
            }
            CharacterListScreen()
        }
        composable(CharacterDestination.Details) { destination ->
            val id = destination.arguments?.getString(CharacterDestination.Details.characterIdArg)?.toInt() ?: error("Mandatory Character id is missing")
            CharacterDetailsScreen(id)
        }
    }

}