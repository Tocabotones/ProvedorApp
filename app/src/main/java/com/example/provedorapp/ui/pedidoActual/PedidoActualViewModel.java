package com.example.provedorapp.ui.pedidoActual;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PedidoActualViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PedidoActualViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}