package com.zurie.ejemplofirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.zurie.ejemplofirebase.ui.theme.EjemploFirebaseTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            MainScreen()
        }
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(viewModel: UsuarioViewModel = viewModel()) {
        val usuarios by viewModel.usuarios.collectAsState()

        var nombre by remember { mutableStateOf("") }
        var contraseña by remember { mutableStateOf("") }
        var edad by remember { mutableStateOf("") }
        var genero by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Usuarios Firestore") }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                // El formulario para agregar un nuevo usuario
                Text("Agregar Nuevo Usuario", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = genero,
                    onValueChange = { genero = it },
                    label = { Text("Género") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (nombre.isNotBlank() && contraseña.isNotBlank() && edad.isNotBlank() && genero.isNotBlank()) {
                            viewModel.agregarUsuario(
                                Usuario(
                                    nombre = nombre,
                                    contraseña = contraseña,
                                    edad = edad.toIntOrNull() ?: 0,
                                    genero = genero
                                )
                            )

                            nombre = ""
                            contraseña = ""
                            edad = ""
                            genero = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar Usuario")
                }

                Spacer(modifier = Modifier.height(24.dp))

                @Composable
                fun TablaUsuarios(usuarios: List<Usuario>) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        // Encabezados de la tabla
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Nombre", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                            Text("Edad", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                            Text("Género", style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                        }

                        Divider()

                        // Lista de usuarios
                        usuarios.forEach { usuario ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp, horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(usuario.nombre, modifier = Modifier.weight(1f))
                                Text(usuario.edad.toString(), modifier = Modifier.weight(1f))
                                Text(usuario.genero, modifier = Modifier.weight(1f))
                            }
                            Divider()
                        }
                    }
                }


                Text("Lista de Usuarios", style = MaterialTheme.typography.titleMedium)

                TablaUsuarios(usuarios = usuarios)


                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    viewModel.agregarUsuario(
                        Usuario(
                            nombre = "Usuario de prueba",
                            contraseña = "123456",
                            edad = 22,
                            genero = "Otro"
                        )
                    )
                }) {
                    Text("Agregar Usuario")
                }
            }
        }
    }
