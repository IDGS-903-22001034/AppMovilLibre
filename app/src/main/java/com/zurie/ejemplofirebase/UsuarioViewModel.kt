package com.zurie.ejemplofirebase

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow

class UsuarioViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios.asStateFlow()

    init {
        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        db.collection("Usuarios")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.map { it.toObject(Usuario::class.java) }
                _usuarios.value = lista
            }
    }

    fun agregarUsuario(usuario: Usuario) {
        db.collection("Usuarios")
            .add(usuario)
            .addOnSuccessListener {
                cargarUsuarios()
            }
    }
}