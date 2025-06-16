package com.booklog.booklog.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color


data class Book(
    val title: String,
    val imageResId: Int,
    val totalPages: Int,
    val pagesRead: Int,
    val startDate: String,
    val finishDate: String,
    val isFavorite: Boolean = false
)

@Composable
fun HomeScreen(
    books: List<Book>,
    onBookClick: (Book) -> Unit = {},
    onAddBookClick: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBookClick) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text("Meus Livros", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            if (books.isEmpty()) {
                Text("Nenhum livro adicionado. Clique no + para adicionar.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(books) { book ->
                        BookItem(book = book, onClick = { onBookClick(book) })
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = book.imageResId),
            contentDescription = "Capa do livro ${book.title}",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(book.title,
                color = if (book.isFavorite) Color(0xFFFFD700) else MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            val progress = if (book.totalPages > 0) book.pagesRead.toFloat() / book.totalPages else 0f
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text("Páginas: ${book.pagesRead} / ${book.totalPages}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("Início: ${book.startDate}")

            if (book.finishDate.isBlank()) {
                Text("Fim: Ainda não finalizado.")
            } else {
                Text("Fim: ${book.finishDate}")
            }
        }
    }
}