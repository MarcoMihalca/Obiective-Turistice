package ro.utcn.obiectiveturistice.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Model pentru un element din bara de navigare inferioară (Bottom Navigation Bar).
 */
data class BottomNavItem<T : Any>(
    val label: String,
    val route: T,
    val icon: ImageVector
)

/**
 * Bara de navigare inferioară cu cele 3 tab-uri obligatorii: Categorii, Favorite, Despre.
 * - În ecranul de detaliu rămâne marcat tab-ul Categorii (prin verificarea ierarhiei grafului imbricat CategoriesGraph).
 * - Fiecare tab își păstrează starea (saveState / restoreState).
 * - O apăsare repetată pe tab-ul curent nu adaugă o copie nouă a ecranului (launchSingleTop).
 */
@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            label = "Categorii",
            route = CategoriesGraph,
            icon = Icons.Default.Place
        ),
        BottomNavItem(
            label = "Favorite",
            route = Favorites,
            icon = Icons.Default.Favorite
        ),
        BottomNavItem(
            label = "Despre",
            route = About,
            icon = Icons.Default.Info
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { destination ->
                destination.hasRoute(item.route::class)
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo<CategoriesGraph> {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}
