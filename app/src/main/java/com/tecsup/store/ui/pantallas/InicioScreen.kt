package com.tecsup.store.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.store.data.Catalogo
import com.tecsup.store.model.Producto
import com.tecsup.store.ui.TarjetaProducto
import com.tecsup.store.ui.TiendaEstado

@Composable
fun InicioScreen(
    estado: TiendaEstado,
    onProducto: (Producto) -> Unit,
    onFavorito: (Producto) -> Unit,
    onCompartir: (Producto) -> Unit,
    onReportar: (Producto) -> Unit
) {
    var categoria by rememberSaveable { mutableStateOf<String?>(null) }
    val secciones = Catalogo.secciones(categoria)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Hola, ${Catalogo.usuario.nombre.split(" ").first()}",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Merch y útiles del campus",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = categoria == null,
                        onClick = { categoria = null },
                        label = { Text("Todos") }
                    )
                }
                items(Catalogo.categorias) { nombre ->
                    FilterChip(
                        selected = categoria == nombre,
                        onClick = { categoria = nombre },
                        label = { Text(nombre) }
                    )
                }
            }
        }
        secciones.forEach { seccion ->
            item(key = "titulo-${seccion.titulo}-${categoria ?: "todos"}") {
                Text(
                    text = seccion.titulo,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(seccion.productos, key = { it.id }) { producto ->
                TarjetaProducto(
                    producto = producto,
                    esFavorito = estado.esFavorito(producto.id),
                    onClick = { onProducto(producto) },
                    onFavorito = { onFavorito(producto) },
                    onCompartir = { onCompartir(producto) },
                    onReportar = { onReportar(producto) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
