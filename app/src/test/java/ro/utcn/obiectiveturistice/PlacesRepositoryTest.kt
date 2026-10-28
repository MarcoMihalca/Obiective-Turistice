package ro.utcn.obiectiveturistice

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import ro.utcn.obiectiveturistice.data.PlaceCategory
import ro.utcn.obiectiveturistice.data.PlacesRepository

class PlacesRepositoryTest {

    @Test
    fun repository_hasAtLeastNinePlacesAndThreePerCategory() {
        val allPlaces = PlacesRepository.getAll()
        assertTrue("Trebuie să existe cel puțin 9 obiective", allPlaces.size >= 9)

        PlaceCategory.entries.forEach { category ->
            val placesInCategory = PlacesRepository.getByCategory(category)
            assertTrue(
                "Categoria ${category.label} trebuie să conțină cel puțin 3 obiective",
                placesInCategory.size >= 3
            )
        }
    }

    @Test
    fun repository_getByIdAndFavoritesWorkCorrectly() {
        val firstPlace = PlacesRepository.getById(1)
        assertNotNull(firstPlace)
        assertEquals("Muzeul Satului Maramureșean", firstPlace?.name)

        val favorites = PlacesRepository.getFavorites(setOf(1, 5))
        assertEquals(2, favorites.size)
    }
}
