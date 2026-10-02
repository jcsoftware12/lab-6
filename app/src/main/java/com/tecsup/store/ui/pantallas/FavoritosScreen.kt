package com.tecsup.store.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.store.data.Catalogo
import com.tecsup.store.model.Producto
import com.tecsup.store.ui.TarjetaProducto
import com.tecsup.store.ui.TiendaEstado

@Composable
fun FavoritosScreen(
    estado: TiendaEstado,
    onProducto: (Producto) -> Unit,
    onFavorito: (Producto) -> Unit,
    onCompartir: (Producto) -> Unit,
    onReportar: (Producto) -> Unit
) {
    val favoritos = Catalogo.productos.filter { estado.esFavorito(it.id) }

    if (favoritos.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Todavía no marcaste favoritos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Abre los tres puntos de un producto y elige Favoritos. El contador del menú lateral usa esa misma lista.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "${favoritos.size} guardado${if (favoritos.size == 1) "" else "s"}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        items(favoritos, key = { it.id }) { producto ->
            TarjetaProducto(
                producto = producto,
                esFavorito = true,
                onClick = { onProducto(producto) },
                onFavorito = { onFavorito(producto) },
                onCompartir = { onCompartir(producto) },
                onReportar = { onReportar(producto) }
            )
        }
    }
}
