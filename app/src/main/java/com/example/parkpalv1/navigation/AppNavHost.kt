package com.example.parkpalv1.navigation

import TempScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.parkpalv1.data.repository.OpenRouterRepository
import com.example.parkpalv1.data.repository.ParkRepository
import com.example.parkpalv1.data.repository.VisitRepository
import com.example.parkpalv1.screens.ParkDetailScreen
import com.example.parkpalv1.ui.screens.HomeScreen
import com.example.parkpalv1.ui.screens.LeaderboardScreen
import com.example.parkpalv1.ui.screens.LoginScreen
import com.example.parkpalv1.ui.screens.NewsScreen
import com.example.parkpalv1.ui.screens.PlanScreen
import com.example.parkpalv1.ui.screens.RegisterScreen
import com.example.parkpalv1.ui.screens.SearchScreen
import com.example.parkpalv1.ui.screens.VisitedParksScreen
import com.example.parkpalv1.ui.screens.WelcomeScreen
import com.example.parkpalv1.ui.viewmodel.NewsViewModel
import com.example.parkpalv1.ui.viewmodel.ParkSearchViewModel
import com.example.parkpalv1.ui.viewmodel.PlanViewModel
import com.example.parkpalv1.ui.viewmodel.VisitViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Plan : Screen("plan")
    object News : Screen("news")
    object Leaderboard : Screen("leaderboard")
    object Visited : Screen("visited")
    object Search : Screen("search")
    object ParkDetail : Screen("park/{parkId}") {
        fun createRoute(parkId: String) = "park/$parkId"
    }

}

@Composable
fun AppNavHost(
    navController: NavHostController,
    auth: FirebaseAuth,
    modifier: Modifier = Modifier
) {

    val parkRepository = remember { ParkRepository.getInstance() }
    val visitRepository = remember { VisitRepository.getInstance(parkRepository) }
    val openRouterRepository = remember { OpenRouterRepository.getInstance() }

    val parkSearchViewModel: ParkSearchViewModel = viewModel(
        factory = ParkSearchViewModel.Factory(repository = parkRepository)
    )

    val visitViewModel: VisitViewModel = viewModel(
        factory = VisitViewModel.Factory(
            visitRepository = visitRepository,
            parkSearchViewModel = parkSearchViewModel
        )
    )

    val planViewModel: PlanViewModel = viewModel(
        factory = PlanViewModel.Factory(openRouterRepository = openRouterRepository)
    )

    val newsViewModel: NewsViewModel = viewModel(
        factory = NewsViewModel.Factory(parkRepository = parkRepository)
    )

    NavHost(
        navController = navController,
        startDestination = if (auth.currentUser != null) Screen.Home.route else Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                auth = auth,
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true}
                        CoroutineScope(Dispatchers.Main).launch {
                            visitRepository.fetchVisitedParks()

                        }
                    }
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                auth = auth,
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true}
                        CoroutineScope(Dispatchers.Main).launch {
                            visitRepository.fetchVisitedParks()

                        }
                    }
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                auth = auth,
                onPlanClick = {
                    navController.navigate(Screen.Plan.route)
                    Log.d("Test", "Clicked Another Screen")
                },
                onNewsClick = {
                    navController.navigate(Screen.News.route)
                },
                onLeaderboardClick = {
                    navController.navigate(Screen.Leaderboard.route)
                },
                onVisitedClick = {
                    navController.navigate(Screen.Visited.route)
                },
                onSearchClick = {
                    Log.d("Test", "Clicked Search Screen")
                    navController.navigate(Screen.Search.route)

                },
                onLogout = {
                    auth.signOut()
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                parkSearchViewModel = parkSearchViewModel,
                visitViewModel = visitViewModel,
                onParkSelected = { parkId ->
                    navController.navigate(Screen.ParkDetail.createRoute(parkId))
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = Screen.ParkDetail.route,
            arguments = listOf(navArgument("parkId") { type = NavType.StringType })
        ) { backStackEntry ->
            val parkId = backStackEntry.arguments?.getString("parkId") ?: ""
            ParkDetailScreen(
                parkSearchViewModel = parkSearchViewModel,
                visitViewModel = visitViewModel,
                parkId = parkId,
                onBackClick = {
                    navController.navigateUp()
                }

            )
        }
        composable(Screen.Plan.route) {
            PlanScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                planViewModel = planViewModel,
            )
        }

        composable(Screen.News.route) {
            NewsScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                newsViewModel = newsViewModel,
            )
        }

        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                onBackClick = {
                    navController.navigateUp()
                },
                onParkSelected = { parkId ->
                    navController.navigate(Screen.ParkDetail.createRoute(parkId))
                },
                onUserSelected = { userId ->

                },
                visitViewModel = visitViewModel
            )
        }

        composable(Screen.Visited.route) {
           VisitedParksScreen(
               onParkSelected = { parkId ->
                   navController.navigate(Screen.ParkDetail.createRoute(parkId))
               },
               onBackClick = {
                   navController.navigateUp()
               },
               onAddNewPark = {
                   navController.navigate(Screen.Search.route)
               },
               onEditClick = {

               },
               visitViewModel = visitViewModel
           )
        }
    }
}