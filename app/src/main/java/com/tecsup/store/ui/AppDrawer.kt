package com.tecsup.store.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class Destino(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
) {
    Inicio("inicio", "Inicio", Icons.Outlined.Home),
    Pedidos("pedidos", "Mis pedidos", Icons.Outlined.ShoppingBag),
    Favoritos("favoritos", "Favoritos", Icons.Outlined.FavoriteBorder),
    Perfil("perfil", "Perfil", Icons.Outlined.Person);

    companion object {
        fun desdeRuta(ruta: String?): Destino? = entries.find { it.ruta == ruta }
    }
}

@Composable
fun AppDrawer(
    onDestino: (Destino) -> Unit,
    onCerrarSesion: () -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(12.dp))
        Text(
            text = "TECSUP Store",
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp)
        )
        Destino.entries.forEach { destino ->
            NavigationDrawerItem(
                label = { Text(destino.titulo) },
                selected = false,
                onClick = { onDestino(destino) },
                icon = { Icon(destino.icono, contentDescription = null) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))
        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            selected = false,
            onClick = onCerrarSesion,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        Spacer(Modifier.height(12.dp))
    }
}
