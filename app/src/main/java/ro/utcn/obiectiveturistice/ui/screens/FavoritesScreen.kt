package ro.utcn.obiectiveturistice.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ro.utcn.obiectiveturistice.data.Place
import ro.utcn.obiectiveturistice.data.PlacesRepository
import ro.utcn.obiectiveturistice.ui.theme.ObiectiveTuristiceTheme

/**
 * 3.4 Favorite
 * - Afișează locurile marcate ca favorite din ecranul de detaliu; un loc poate fi eliminat direct din listă.
 * - Când lista este goală, afișează un mesaj explicativ.
 * - Ecran decuplat de NavController (state hoisting).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteIds: Set<Int>,
    onRemoveFavorite: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritePlaces = PlacesRepository.getFavorites(favoriteIds)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Obiective Favorite",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${favoritePlaces.size} obiective salvate",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        if (favoritePlaces.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Fără favorite",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Lista de favorite este goală",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Nu ai adăugat încă niciun obiectiv turistic la favorite. Deschide un obiectiv din tab-ul Categorii și apasă pe butonul „Adaugă la favorite”.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = favoritePlaces,
                    key = { place -> place.id }
                ) { place ->
                    FavoritePlaceCard(
                        place = place,
                        onRemove = { onRemoveFavorite(place.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritePlaceCard(
    place: Place,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    val badgeColor = categoryColor(place.category)

    Card(
        modifier = modifier.fillMaxWidth(),
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
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
                Surface(
                    color = badgeColor.copy(alpha = 0.14f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = place.category.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = badgeColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Localitate",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = place.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Elimină din favorite",
                    tint = Color(0xFFD32F2F)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    ObiectiveTuristiceTheme {
        FavoritesScreen(
            favoriteIds = emptySet(),
            onRemoveFavorite = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoritesScreenPopulatedPreview() {
    ObiectiveTuristiceTheme {
        FavoritesScreen(
            favoriteIds = setOf(1, 5, 10),
            onRemoveFavorite = {}
        )
    }
}
