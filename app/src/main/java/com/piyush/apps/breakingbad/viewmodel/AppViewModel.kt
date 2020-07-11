package com.piyush.apps.breakingbad.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piyush.apps.breakingbad.R
import com.piyush.apps.breakingbad.helper.InternetHelper
import com.piyush.apps.breakingbad.helper.Provider
import com.piyush.apps.breakingbad.model.Character
import com.piyush.apps.breakingbad.model.repository.AppRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

/**
 * Created By Piyush Pandey on 08-07-2020
 * Apps@Piyush
 */
class AppViewModel @ViewModelInject constructor(@ApplicationContext private val context: Context, private val repository: AppRepository, private val internetHelper: InternetHelper): ViewModel() {

    private val _characters = MutableLiveData<Provider<List<Character>>>()
    val characters : LiveData<Provider<List<Character>>>
        get() = _characters

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _characters.postValue(Provider.loading(null))
            if (internetHelper.isInternetConnected()) {
                repository.getCharacters().let {
                    if (it.isSuccessful) {
                        _characters.postValue(Provider.success(it.body()!!))
                    } else {
                        _characters.postValue(Provider.error(null, it.errorBody().toString()))
                    }
                }
            } else {
                _characters.postValue(Provider.error(null, context.getString(R.string.msg_no_internet)))
            }
        }
    }

}