package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EditarPerfilViewModel extends ViewModel {
    private final MutableLiveData<String> editarPerfil = new MutableLiveData<>();

    public void selectedAplicar(String filtrTitulo){
        editarPerfil.setValue(filtrTitulo);
    }

    public MutableLiveData<String> getAll(){
        return editarPerfil;
    }
}
