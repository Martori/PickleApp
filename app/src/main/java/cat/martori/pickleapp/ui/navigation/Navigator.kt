package cat.martori.pickleapp.ui.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.flow.*


interface ComposeRoute {
    val route: String
}

interface ComposeDestination : ComposeRoute {
    val destination: String
        get() = route
}

sealed class ComposeAction<T : ComposeDestination> {
    class Navigate<T : ComposeDestination>(val destination: T) : ComposeAction<T>()
    class BackTo<T : ComposeDestination>(val destination: T?) : ComposeAction<T>()
}

interface Navigator<T : ComposeDestination> {
    fun navigate(navAction: T)
    fun backTo(destination: T? = null)
    val navActions: Flow<ComposeAction<T>?>
    fun onNavigated()
}


class FlowNavigator<T : ComposeDestination> : Navigator<T> {
    private val _navActions: MutableStateFlow<ComposeAction<T>?> by lazy {
        MutableStateFlow(null)
    }
    override val navActions = _navActions.asStateFlow()

    override fun navigate(navAction: T) {
        _navActions.value = ComposeAction.Navigate(navAction)
    }

    override fun backTo(destination: T?) {
        _navActions.value = ComposeAction.BackTo(destination)
    }

    override fun onNavigated() {
        _navActions.value = null
    }
}

@Composable
fun <T> Flow<T>.asLifecycleAwareState(lifecycleOwner: LifecycleOwner, initialState: T) =
    lifecycleAwareState(lifecycleOwner, this, initialState)

@Composable
fun <T> lifecycleAwareState(
    lifecycleOwner: LifecycleOwner,
    flow: Flow<T>,
    initialState: T,
): State<T> {
    val lifecycleAwareStateFlow = remember(flow, lifecycleOwner) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    return lifecycleAwareStateFlow.collectAsState(initialState)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun <T : ComposeDestination> Navigator<T>.rememberNavHostController(): NavHostController {
    val navigatorState by navActions.asLifecycleAwareState(lifecycleOwner = LocalLifecycleOwner.current, initialState = null)
    val navController = rememberAnimatedNavController()
    LaunchedEffect(navigatorState) {
        when (val state = navigatorState) {
            is ComposeAction.BackTo -> state.destination?.route?.let { navController.popBackStack(it, false) } ?: navController.navigateUp()
            is ComposeAction.Navigate -> navController.navigate(state.destination.destination)
            else -> {}
        }
        onNavigated()
    }

    return navController
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: ComposeRoute,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition) = enterTransition,
    popExitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition) = exitTransition,
    builder: NavGraphBuilder.() -> Unit,
) = com.google.accompanist.navigation.animation.AnimatedNavHost(navController, startDestination.route, modifier, contentAlignment, route, enterTransition, exitTransition, popEnterTransition, popExitTransition, builder)

@ExperimentalAnimationApi
fun NavGraphBuilder.composable(
    destination: ComposeRoute,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (
    AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
    )? = enterTransition,
    popExitTransition: (
    AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
    )? = exitTransition,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) = composable(destination.route, arguments, deepLinks, enterTransition, exitTransition, popEnterTransition, popExitTransition, content)
