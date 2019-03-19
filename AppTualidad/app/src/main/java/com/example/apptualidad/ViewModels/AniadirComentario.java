package com.example.apptualidad.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class AniadirComentario extends ViewModel {
    private final MutableLiveData<String> addComent = new MutableLiveData<>();

    public void selectedAplicar(String addC){
        addComent.setValue(addC);
    }

    public MutableLiveData<String> getAll(){
        return addComent;
    }
}
