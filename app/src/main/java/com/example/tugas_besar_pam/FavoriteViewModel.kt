package com.example.tugas_besar_pam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val db by lazy { FavoriteDatabase(application) }

    fun deleteFavorite(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            db.favoriteDao().deleteFavorite(favorite)
        }
    }
}
