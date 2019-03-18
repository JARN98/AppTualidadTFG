package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.apptualidad.Model.NoticiaRes;

public class AplicarFiltroViewModel extends ViewModel {
    private final MutableLiveData<String> filtroTitulo = new MutableLiveData<>();

    public void selectedAplicar(String filtrTitulo){
        filtroTitulo.setValue(filtrTitulo);
    }

    public MutableLiveData<String> getAll(){
        return filtroTitulo;
    }
}
