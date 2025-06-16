package com.booklog.booklog.ui.addbook

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.booklog.booklog.ui.home.Book
import com.booklog.booklog.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddBookScreen(
    onBookAdded: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var totalPages by remember { mutableStateOf("") }
    var pagesRead by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var finishDate by remember { mutableStateOf("") }
    var isFavorite by remember { mutableStateOf(false) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showFinishDatePicker by remember { mutableStateOf(false) }

    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("Adicionar Livro", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título*") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = totalPages,
            onValueChange = { totalPages = it.filter { char -> char.isDigit() } },
            label = { Text("Total de Páginas*") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = pagesRead,
            onValueChange = { pagesRead = it.filter { char -> char.isDigit() } },
            label = { Text("Páginas Lidas*") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = startDate,
            onValueChange = { },
            label = { Text("Data de Início*") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showStartDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data de Início")
                }
            },
            readOnly = true
        )

        OutlinedTextField(
            value = finishDate,
            onValueChange = { },
            label = { Text("Data de Fim") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showFinishDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Selecionar Data de Fim")
                }
            },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = isFavorite,
                onCheckedChange = { isFavorite = it }
            )
            Text("Livro Favorito")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current
        Row {
            Button(
                onClick = {
                    if (title.isNotBlank() && totalPages.isNotBlank() && pagesRead.isNotBlank() && startDate.isNotBlank()) {
                        val book = Book(
                            title = title,
                            imageResId = R.drawable.ic_launcher_foreground,
                            totalPages = totalPages.toInt(),
                            pagesRead = pagesRead.toInt(),
                            startDate = startDate,
                            finishDate = finishDate,
                            isFavorite = isFavorite
                        )
                        onBookAdded(book)
                    } else {
                        val toast = Toast.makeText(
                            context,
                            "Preencha os campos obrigatórios (*)",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            ) {
                Text("Salvar")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    }

    if (showStartDatePicker) {
        DatePickerModal(
            onDateSelected = { millis ->
                millis?.let {
                    startDate = dateFormat.format(Date(it))
                }
                showStartDatePicker = false
            },
            onDismiss = { showStartDatePicker = false }
        )
    }

    if (showFinishDatePicker) {
        DatePickerModal(
            onDateSelected = { millis ->
                millis?.let {
                    finishDate = dateFormat.format(Date(it))
                }
                showFinishDatePicker = false
            },
            onDismiss = { showFinishDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}