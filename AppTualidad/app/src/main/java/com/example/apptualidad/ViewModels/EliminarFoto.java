package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class EliminarFoto extends ViewModel {
    private final MutableLiveData<String> deleteFoto = new MutableLiveData<>();

    public void selectedAplicar(String deleteF){
        deleteFoto.setValue(deleteF);
    }

    public MutableLiveData<String> getAll(){
        return deleteFoto;
    }
}
