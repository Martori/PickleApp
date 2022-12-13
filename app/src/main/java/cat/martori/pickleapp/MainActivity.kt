package cat.martori.pickleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cat.martori.pickleapp.ui.navigation.navGraphs.MainNavGraph
import cat.martori.pickleapp.ui.theme.PickleAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickleAppTheme {
                MainNavGraph {
                    finish()
                }
            }
        }
    }
}


