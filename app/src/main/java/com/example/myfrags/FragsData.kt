package com.example.myfrags

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragsData: ViewModel() {
    public val counter: MutableLiveData<String> = MutableLiveData("0")
}
