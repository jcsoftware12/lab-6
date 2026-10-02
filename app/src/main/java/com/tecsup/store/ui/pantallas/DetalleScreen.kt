package com.tecsup.store.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.store.model.Producto

@Composable
fun DetalleScreen(
    producto: Producto,
    esFavorito: Boolean,
    onFavorito: () -> Unit,
    onCompartir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF8E1B2C))
        )
        Text(producto.categoria, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Text(producto.nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(
            producto.precioTexto(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onFavorito, modifier = Modifier.fillMaxWidth()) {
            Text(if (esFavorito) "Quitar de favoritos" else "Agregar a favoritos")
        }
        OutlinedButton(onClick = onCompartir, modifier = Modifier.fillMaxWidth()) {
            Text("Compartir")
        }
    }
}
