package com.example.myimages.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myimages.room.ImageDatabase
import com.example.myimages.models.ModelImages
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedsViewModel(application: Application) : AndroidViewModel(application) {

    private val _feeds = MutableLiveData<List<ModelImages>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            val apiUrl = "https://acharyaprashant.org/api/v2/content/misc/media-coverages?limit=100"
            val (request, response, result) = apiUrl
                .httpGet()
                .responseString()

            when (result) {
                is Result.Failure -> {
                    val ex = result.getException()
                    println(ex)
                }

                is Result.Success -> {
                    val data = result.get()
                    val modelImagesList: List<ModelImages> = parseData(data)

                    viewModelScope.launch(Dispatchers.IO) {
                        val dao = ImageDatabase.getDatabase(application.applicationContext)
                            .modelImagesDao()
                        val cachedList = dao.getAllModelImages()

                        if (cachedList.isEmpty() || !isSameList(cachedList, modelImagesList)) {
                            dao.insertAll(modelImagesList)
                            withContext(Dispatchers.Main) {
                                value = modelImagesList
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                value = cachedList
                            }
                        }
                    }

                }
            }
        }
    }
    val feeds: LiveData<List<ModelImages>> = _feeds

    private fun parseData(data: String): List<ModelImages> {
        val gson = Gson()
        return gson.fromJson(data, object : TypeToken<List<ModelImages>>() {}.type)
    }

    private fun isSameList(list1: List<ModelImages>, list2: List<ModelImages>): Boolean {
        if (list1.size != list2.size) return false

        for (i in list1.indices) {
            if (list1[i] != list2[i]) return false
        }

        return true
    }
}