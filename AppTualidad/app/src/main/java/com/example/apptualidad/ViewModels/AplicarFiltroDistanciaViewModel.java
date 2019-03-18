package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AplicarFiltroDistanciaViewModel extends ViewModel {
    private final MutableLiveData<String> distancia = new MutableLiveData<>();

    public void selectedAplicar(String filtrTitulo){
        distancia.setValue(filtrTitulo);
    }

    public MutableLiveData<String> getAll(){
        return distancia;
    }
}
