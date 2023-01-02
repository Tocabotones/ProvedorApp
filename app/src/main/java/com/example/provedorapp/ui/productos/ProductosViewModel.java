package com.example.provedorapp.ui.productos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ProductosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final ArrayList<String> productos;

    public ProductosViewModel() {
        mText = new MutableLiveData<>();
        productos = new ArrayList<>();
    }

    public LiveData<String> getText() {
        return mText;
    }


}