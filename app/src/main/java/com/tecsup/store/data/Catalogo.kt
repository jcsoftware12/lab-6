package com.tecsup.store.data

import com.tecsup.store.model.Pedido
import com.tecsup.store.model.Producto
import com.tecsup.store.model.SeccionTienda
import com.tecsup.store.model.UsuarioDemo

object Catalogo {
    val usuario = UsuarioDemo(
        nombre = "Camila Rojas",
        correo = "crojas@tecsup.edu.pe",
        codigo = "C24-0142",
        carrera = "Diseño y Desarrollo de Software",
        iniciales = "CR"
    )

    val categorias = listOf("Polos", "Hoodies", "Accesorios", "Útiles")

    val productos = listOf(
        Producto(
            id = 1,
            nombre = "Polo institucional",
            precio = 45.00,
            categoria = "Polos",
            seccion = "Destacados",
            descripcion = "Polo de algodón con el escudo de TECSUP bordado. Talla unisex."
        ),
        Producto(
            id = 2,
            nombre = "Polo deportivo",
            precio = 39.90,
            categoria = "Polos",
            seccion = "Ofertas",
            descripcion = "Tela dry-fit para talleres y actividades del campus."
        ),
        Producto(
            id = 3,
            nombre = "Polo oversize",
            precio = 55.00,
            categoria = "Polos",
            seccion = "Nuevos",
            descripcion = "Corte holgado, color vino, con etiqueta de la tienda del campus."
        ),
        Producto(
            id = 4,
            nombre = "Hoodie de ingeniería",
            precio = 120.00,
            categoria = "Hoodies",
            seccion = "Destacados",
            descripcion = "Hoodie con capucha y bolsillo canguro. Interior afelpado."
        ),
        Producto(
            id = 5,
            nombre = "Casaca rompevientos",
            precio = 149.90,
            categoria = "Hoodies",
            seccion = "Nuevos",
            descripcion = "Casaca liviana para el clima de Lima, con cierre y capucha."
        ),
        Producto(
            id = 6,
            nombre = "Taza del campus",
            precio = 25.00,
            categoria = "Accesorios",
            seccion = "Destacados",
            descripcion = "Cerámica de 320 ml. Apta para microondas."
        ),
        Producto(
            id = 7,
            nombre = "Mochila TECSUP",
            precio = 89.90,
            categoria = "Accesorios",
            seccion = "Nuevos",
            descripcion = "Compartimento para laptop de 15 pulgadas y bolsillo para botella."
        ),
        Producto(
            id = 8,
            nombre = "Gorra bordada",
            precio = 35.00,
            categoria = "Accesorios",
            seccion = "Destacados",
            descripcion = "Gorra de algodón con visera curva y bordado frontal."
        ),
        Producto(
            id = 9,
            nombre = "Cuaderno A4",
            precio = 12.50,
            categoria = "Útiles",
            seccion = "Ofertas",
            descripcion = "100 hojas rayadas, tapa dura con el nombre de la tienda."
        ),
        Producto(
            id = 10,
            nombre = "Set de lapiceros",
            precio = 8.90,
            categoria = "Útiles",
            seccion = "Ofertas",
            descripcion = "Tres lapiceros de tinta negra, azul y roja."
        )
    )

    val pedidos = listOf(
        Pedido(
            codigo = "PED-1042",
            fecha = "28/09/2026",
            estado = "Entregado",
            total = 45.00,
            detalle = "Polo institucional"
        ),
        Pedido(
            codigo = "PED-1058",
            fecha = "01/10/2026",
            estado = "En camino",
            total = 37.50,
            detalle = "Taza del campus + Cuaderno A4"
        )
    )

    fun producto(id: Int): Producto? = productos.find { it.id == id }

    fun secciones(categoria: String?): List<SeccionTienda> {
        val base = if (categoria == null) {
            productos
        } else {
            productos.filter { it.categoria == categoria }
        }
        return listOf("Destacados", "Nuevos", "Ofertas").mapNotNull { titulo ->
            val items = base.filter { it.seccion == titulo }
            if (items.isEmpty()) null else SeccionTienda(titulo, items)
        }
    }
}
