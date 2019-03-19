package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EliminarNoticia extends ViewModel {
    private final MutableLiveData<String> eliminarNoticia = new MutableLiveData<>();

    public void selectedAplicar(String filtrTitulo){
        eliminarNoticia.setValue(filtrTitulo);
    }

    public MutableLiveData<String> getAll(){
        return eliminarNoticia;
    }
}
