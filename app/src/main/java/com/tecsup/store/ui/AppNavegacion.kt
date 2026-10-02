package com.tecsup.store.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
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
import com.tecsup.store.model.Producto
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
    val estado = remember { TiendaEstado() }
    val nav = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun avisar(texto: String) {
        scope.launch { snackbar.showSnackbar(texto) }
    }
    fun alternarFavorito(producto: Producto) {
        val agregado = estado.alternarFavorito(producto.id)
        avisar(
            if (agregado) "${producto.nombre} se agregó a favoritos"
            else "${producto.nombre} se quitó de favoritos"
        )
    }
    val entrada by nav.currentBackStackEntryAsState()
    val detalleId = entrada?.arguments?.getString("productoId")?.toIntOrNull()
    val productoDetalle = detalleId?.let { Catalogo.producto(it) }
    val destino = Destino.desdeRuta(entrada?.destination?.route)
    val titulo = when (entrada?.destination?.route) {
        Rutas.Pedidos -> "Mis pedidos"
        Rutas.Favoritos -> "Favoritos"
        Rutas.Perfil -> "Perfil"
        else -> productoDetalle?.nombre ?: "Inicio"
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                destinoActual = destino,
                cantidadFavoritos = estado.cantidadFavoritos,
                onDestino = { elegido ->
                    scope.launch { drawerState.close() }
                    nav.navigate(elegido.ruta) {
                        popUpTo(Destino.Inicio.ruta) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onCerrarSesion = {
                    scope.launch { drawerState.close() }
                    avisar("Sesión cerrada")
                }
            )
        }
    ) {
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
                    } else {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Rounded.Menu, contentDescription = "Abrir menú")
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
                    estado = estado,
                    onProducto = { nav.navigate("detalle/${it.id}") },
                    onFavorito = { alternarFavorito(it) },
                    onCompartir = { avisar("Compartiendo ${it.nombre}") },
                    onReportar = { avisar("Reporte enviado: ${it.nombre}") }
                )
            }
            composable(Rutas.Pedidos) { PedidosScreen() }
            composable(Rutas.Favoritos) {
                FavoritosScreen(
                    estado = estado,
                    onProducto = { nav.navigate("detalle/${it.id}") },
                    onFavorito = { alternarFavorito(it) },
                    onCompartir = { avisar("Compartiendo ${it.nombre}") },
                    onReportar = { avisar("Reporte enviado: ${it.nombre}") }
                )
            }
            composable(Rutas.Perfil) { PerfilScreen(estado) }
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
                        esFavorito = estado.esFavorito(producto.id),
                        onFavorito = { alternarFavorito(producto) },
                        onCompartir = { avisar("Compartiendo ${producto.nombre}") }
                    )
                }
            }
        }
    }
    }
}
