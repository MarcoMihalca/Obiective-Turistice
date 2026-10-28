package ro.utcn.obiectiveturistice.navigation

import kotlinx.serialization.Serializable

/**
 * Toate rutele aplicației sunt definite type-safe folosind @Serializable,
 * conform cerințelor din laborator (Nav2 cu rute type-safe, fără șiruri de caractere).
 */

/** Ruta pentru ecranul de autentificare (Login). */
@Serializable
object Login

/** Ruta pentru graful imbricat al tab-ului Categorii (include lista și detaliul). */
@Serializable
object CategoriesGraph

/** Ruta pentru ecranul cu lista obiectivelor turistice și filtrarea pe categorii. */
@Serializable
object PlaceList

/**
 * Ruta pentru ecranul de detaliu al unui obiectiv turistic.
 * Transmite ca argument de navigare DOAR id-ul locului.
 */
@Serializable
data class PlaceDetail(val id: Int)

/** Ruta pentru tab-ul Favorite. */
@Serializable
object Favorites

/** Ruta pentru tab-ul Despre aplicație. */
@Serializable
object About
