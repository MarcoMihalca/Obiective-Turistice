package ro.utcn.obiectiveturistice.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.utcn.obiectiveturistice.data.Place
import ro.utcn.obiectiveturistice.data.PlaceCategory
import ro.utcn.obiectiveturistice.data.PlacesRepository
import ro.utcn.obiectiveturistice.ui.theme.CategoryMuzeeColor
import ro.utcn.obiectiveturistice.ui.theme.CategoryRestauranteColor
import ro.utcn.obiectiveturistice.ui.theme.CategorySportColor
import ro.utcn.obiectiveturistice.ui.theme.ObiectiveTuristiceTheme

/**
 * Returnează culoarea reprezentativă pentru fiecare categorie de obiectiv turistic.
 */
fun categoryColor(category: PlaceCategory): Color = when (category) {
    PlaceCategory.SPORT -> CategorySportColor
    PlaceCategory.MUZEE -> CategoryMuzeeColor
    PlaceCategory.RESTAURANTE -> CategoryRestauranteColor
}

/**
 * Returnează un simbol / monogramă ilustrativă pentru fiecare categorie.
 */
fun categorySymbol(category: PlaceCategory): String = when (category) {
    PlaceCategory.SPORT -> "⛰"
    PlaceCategory.MUZEE -> "🏛"
    PlaceCategory.RESTAURANTE -> "🍽"
}

/**
 * 3.2 Lista locurilor (tab-ul Categorii)
 * - Afișează obiectivele într-o listă (LazyColumn).
 * - Filtru interactiv pe categorii: Toate, Sport, Muzee, Restaurante (cu FilterChip).
 * - Filtrul ales se păstrează la deschiderea unui detaliu și revenire, la schimbarea tab-urilor și la rotirea ecranului.
 * - Ecran decuplat de NavController (state hoisting): emite [onCategorySelected] și [onNavigateToDetail].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListScreen(
    selectedCategory: PlaceCategory?,
    favoriteIds: Set<Int>,
    onCategorySelected: (PlaceCategory?) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredPlaces = PlacesRepository.getByCategory(selectedCategory)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Obiective Turistice",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Ghidul Maramureșului (${filteredPlaces.size} locații)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        // Bara de filtre interactive (FilterChip): Toate, Sport, Muzee, Restaurante
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = selectedCategory == null,
                onClick = { onCategorySelected(null) },
                label = { Text("Toate") },
                leadingIcon = if (selectedCategory == null) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selectat",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null
            )

            PlaceCategory.entries.forEach { category ->
                val isSelected = selectedCategory == category
                FilterChip(
                    selected = isSelected,
                    onClick = { onCategorySelected(category) },
                    label = { Text(category.label) },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selectat",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null
                )
            }
        }

        // Lista obiectivelor turistice (LazyColumn)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = filteredPlaces,
                key = { place -> place.id }
            ) { place ->
                PlaceListItemCard(
                    place = place,
                    isFavorite = place.id in favoriteIds,
                    onClick = { onNavigateToDetail(place.id) }
                )
            }
        }
    }
}

@Composable
fun PlaceListItemCard(
    place: Place,
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val badgeColor = categoryColor(place.category)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(badgeColor.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = categorySymbol(place.category),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = badgeColor.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = place.category.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = badgeColor,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorit",
                            tint = Color(0xFFD32F2F),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Localitate",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = place.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlaceListScreenPreview() {
    ObiectiveTuristiceTheme {
        PlaceListScreen(
            selectedCategory = null,
            favoriteIds = setOf(1, 5),
            onCategorySelected = {},
            onNavigateToDetail = {}
        )
    }
}
