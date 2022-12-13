package cat.martori.pickleapp.ui.navigation.destinations

import cat.martori.pickleapp.ui.navigation.ComposeDestination
import cat.martori.pickleapp.ui.navigation.ComposeRoute

sealed interface CharacterDestination : ComposeDestination {


    object List : CharacterDestination {
        override val route: String = "characterList"
    }

    data class Details(val characterId: Int) : CharacterDestination {
        override val route: String = Details.route
        override val destination: String = route.replace("{$characterIdArg}", characterId.toString())

        companion object : ComposeRoute {
            const val characterIdArg = "sliderId"
            override val route: String = "characterDetails/{$characterIdArg}"
        }
    }
}

