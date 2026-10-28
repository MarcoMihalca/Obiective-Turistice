package ro.utcn.obiectiveturistice.data

/**
 * Categoriile obligatorii pentru obiectivele turistice: Sport, Muzee, Restaurante.
 */
enum class PlaceCategory(val label: String) {
    SPORT("Sport"),
    MUZEE("Muzee"),
    RESTAURANTE("Restaurante")
}

/**
 * Modelul de date pentru un obiectiv turistic.
 * Conține câmpurile obligatorii: id, nume, categorie, localitate și o scurtă descriere.
 */
data class Place(
    val id: Int,
    val name: String,
    val category: PlaceCategory,
    val location: String,
    val description: String
)
