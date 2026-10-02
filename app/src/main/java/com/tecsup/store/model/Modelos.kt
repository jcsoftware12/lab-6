package com.tecsup.store.model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val seccion: String,
    val descripcion: String
) {
    fun precioTexto(): String = "S/ ${"%.2f".format(precio)}"
}

data class Pedido(
    val codigo: String,
    val fecha: String,
    val estado: String,
    val total: Double,
    val detalle: String
) {
    fun totalTexto(): String = "S/ ${"%.2f".format(total)}"
}

data class UsuarioDemo(
    val nombre: String,
    val correo: String,
    val codigo: String,
    val carrera: String,
    val iniciales: String
)

data class SeccionTienda(
    val titulo: String,
    val productos: List<Producto>
)
