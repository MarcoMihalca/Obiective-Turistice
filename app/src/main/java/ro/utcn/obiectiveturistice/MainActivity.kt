package ro.utcn.obiectiveturistice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ro.utcn.obiectiveturistice.data.PlaceCategory
import ro.utcn.obiectiveturistice.navigation.About
import ro.utcn.obiectiveturistice.navigation.BottomNavBar
import ro.utcn.obiectiveturistice.navigation.CategoriesGraph
import ro.utcn.obiectiveturistice.navigation.Favorites
import ro.utcn.obiectiveturistice.navigation.Login
import ro.utcn.obiectiveturistice.navigation.PlaceDetail
import ro.utcn.obiectiveturistice.navigation.PlaceList
import ro.utcn.obiectiveturistice.ui.screens.AboutScreen
import ro.utcn.obiectiveturistice.ui.screens.FavoritesScreen
import ro.utcn.obiectiveturistice.ui.screens.LoginScreen
import ro.utcn.obiectiveturistice.ui.screens.PlaceDetailScreen
import ro.utcn.obiectiveturistice.ui.screens.PlaceListScreen
import ro.utcn.obiectiveturistice.ui.theme.ObiectiveTuristiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ObiectiveTuristiceTheme {
                TouristGuideApp()
            }
        }
    }
}

/**
 * Componenta rădăcină a aplicației care găzduiește NavController, Scaffold, BottomNavBar și NavHost.
 * Respectă arhitectura recomandată (state hoisting): NavController apare doar aici și în BottomNavBar,
 * iar ecranele sunt complet decuplate de componenta de navigare.
 */
@Composable
fun TouristGuideApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bara de navigare inferioară este vizibilă doar după autentificare (nu apare pe Login)
    val showBottomBar = currentDestination != null && !currentDestination.hasRoute<Login>()

    // Filtrul de categorie păstrat la navigare între ecrane, schimbarea tab-urilor și rotirea ecranului
    var selectedCategoryName by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedCategory = selectedCategoryName?.let { PlaceCategory.valueOf(it) }

    // Lista de ID-uri favorite păstrată la schimbarea tab-urilor și rotirea ecranului
    var favoriteIdsList by rememberSaveable { mutableStateOf(listOf<Int>()) }
    val favoriteIds = favoriteIdsList.toSet()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Ecranul de autentificare (Login)
            composable<Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(CategoriesGraph) {
                            // Curățăm stiva astfel încât apăsarea pe Back să închidă aplicația, nu să revină la Login
                            popUpTo<Login> { inclusive = true }
                        }
                    }
                )
            }

            // 2. Graf imbricat pentru tab-ul Categorii (Lista locurilor + Detaliul unui loc)
            navigation<CategoriesGraph>(startDestination = PlaceList) {
                composable<PlaceList> {
                    PlaceListScreen(
                        selectedCategory = selectedCategory,
                        favoriteIds = favoriteIds,
                        onCategorySelected = { category ->
                            selectedCategoryName = category?.name
                        },
                        onNavigateToDetail = { placeId ->
                            navController.navigate(PlaceDetail(id = placeId))
                        }
                    )
                }

                composable<PlaceDetail> { backStackEntry ->
                    // Extragem argumentul de navigare (doar id-ul locului) cu toRoute<PlaceDetail>()
                    val detailRoute = backStackEntry.toRoute<PlaceDetail>()
                    PlaceDetailScreen(
                        placeId = detailRoute.id,
                        isFavorite = detailRoute.id in favoriteIds,
                        onToggleFavorite = { id ->
                            favoriteIdsList = if (id in favoriteIdsList) {
                                favoriteIdsList - id
                            } else {
                                favoriteIdsList + id
                            }
                        },
                        onBack = {
                            navController.navigateUp()
                        }
                    )
                }
            }

            // 3. Tab-ul Favorite
            composable<Favorites> {
                FavoritesScreen(
                    favoriteIds = favoriteIds,
                    onRemoveFavorite = { id ->
                        favoriteIdsList = favoriteIdsList - id
                    }
                )
            }

            // 4. Tab-ul Despre aplicație
            composable<About> {
                AboutScreen()
            }
        }
    }
}
