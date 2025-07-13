package com.gigaprod.gigafilm.ui.custom
import Content
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _content = MutableLiveData<Content>()
    val content: LiveData<Content> = _content

    fun setContent(data: Content) {
        if(content.hasObservers())
            _content.value = data
    }
}