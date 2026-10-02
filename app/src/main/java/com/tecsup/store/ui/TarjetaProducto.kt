package com.tecsup.store.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.tecsup.store.model.Producto

private fun colorDe(categoria: String): Color = when (categoria) {
    "Polos" -> Color(0xFF8E1B2C)
    "Hoodies" -> Color(0xFF3E4A3A)
    "Accesorios" -> Color(0xFF8A5A12)
    else -> Color(0xFF2F4A6E)
}

@Composable
fun TarjetaProducto(
    producto: Producto,
    onClick: () -> Unit,
    onFavorito: () -> Unit,
    onCompartir: () -> Unit,
    onReportar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuAbierto by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colorDe(producto.categoria)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = producto.nombre.take(1),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = producto.precioTexto(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Box {
                IconButton(onClick = { menuAbierto = !menuAbierto }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "Opciones de ${producto.nombre}"
                    )
                }
                DropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = { menuAbierto = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Favoritos") },
                        onClick = {
                            menuAbierto = false
                            onFavorito()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Compartir") },
                        onClick = {
                            menuAbierto = false
                            onCompartir()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Reportar") },
                        onClick = {
                            menuAbierto = false
                            onReportar()
                        }
                    )
                }
            }
        }
    }
}
