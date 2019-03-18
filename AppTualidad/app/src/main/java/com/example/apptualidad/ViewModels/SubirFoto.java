package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SubirFoto extends ViewModel {
    private final MutableLiveData<String> subirFoto = new MutableLiveData<>();

    public void selectedAplicar(String filtrTitulo){
        subirFoto.setValue(filtrTitulo);
    }

    public MutableLiveData<String> getAll(){
        return subirFoto;
    }
}
