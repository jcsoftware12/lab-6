package com.tecsup.store.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.store.data.Catalogo
import com.tecsup.store.ui.pantallas.DetalleScreen
import com.tecsup.store.ui.pantallas.FavoritosScreen
import com.tecsup.store.ui.pantallas.InicioScreen
import com.tecsup.store.ui.pantallas.PedidosScreen
import com.tecsup.store.ui.pantallas.PerfilScreen

private object Rutas {
    const val Inicio = "inicio"
    const val Pedidos = "pedidos"
    const val Favoritos = "favoritos"
    const val Perfil = "perfil"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavegacion() {
    val nav = rememberNavController()
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun avisar(texto: String) {
        scope.launch { snackbar.showSnackbar(texto) }
    }
    val entrada by nav.currentBackStackEntryAsState()
    val detalleId = entrada?.arguments?.getString("productoId")?.toIntOrNull()
    val productoDetalle = detalleId?.let { Catalogo.producto(it) }
    val titulo = when (entrada?.destination?.route) {
        Rutas.Pedidos -> "Mis pedidos"
        Rutas.Favoritos -> "Favoritos"
        Rutas.Perfil -> "Perfil"
        else -> productoDetalle?.nombre ?: "Inicio"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbar) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    if (productoDetalle != null) {
                        IconButton(onClick = { nav.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Rutas.Inicio,
            modifier = Modifier.padding(padding)
        ) {
            composable(Rutas.Inicio) {
                InicioScreen(
                    onProducto = { nav.navigate("detalle/${it.id}") },
                    onFavorito = { avisar("${it.nombre} marcado en favoritos") },
                    onCompartir = { avisar("Compartiendo ${it.nombre}") },
                    onReportar = { avisar("Reporte enviado: ${it.nombre}") }
                )
            }
            composable(Rutas.Pedidos) { PedidosScreen() }
            composable(Rutas.Favoritos) { FavoritosScreen() }
            composable(Rutas.Perfil) { PerfilScreen() }
            composable(
                route = "detalle/{productoId}",
                arguments = listOf(navArgument("productoId") { type = NavType.StringType })
            ) {
                val producto = productoDetalle
                if (producto == null) {
                    Text("No se encontró el producto")
                } else {
                    DetalleScreen(
                        producto = producto,
                        esFavorito = false,
                        onFavorito = {},
                        onCompartir = {}
                    )
                }
            }
        }
    }
}
