package au.net.ab.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import au.net.ab.model.UrlDatabase

class MyViewModelFactory(val app: Application, private val db: UrlDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyViewModel(app, db) as T
    }
}