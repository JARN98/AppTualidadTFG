package com.example.apptualidad;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.apptualidad.Fragments.DestacadoNoticiasFragment;
import com.example.apptualidad.Fragments.MisPublicacionesFragment;
import com.example.apptualidad.Fragments.PerfilFragment;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.ViewModels.EditarPerfilViewModel;
import com.example.apptualidad.ViewModels.SubirFoto;

public class PerfilActivity extends AppCompatActivity implements listaNoticiasInterface {
    private Button button_mispublicaciones, button_perfil;
    private FrameLayout perfil_container;
    private FloatingActionButton floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        findids();

        navegation();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.perfil_container, new MisPublicacionesFragment())
                .commit();
        button_mispublicaciones.setBackgroundColor(Color.parseColor("#009484"));
        button_perfil.setBackgroundColor(Color.parseColor("#0BC5B4"));
        floatingActionButton2.hide();

        events();
    }

    private void events() {
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfilViewModel editarPerfilViewModel = ViewModelProviders.of(PerfilActivity.this).get(EditarPerfilViewModel.class);

                editarPerfilViewModel.selectedAplicar("si");
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void navegation() {
        button_mispublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.perfil_container, new MisPublicacionesFragment())
                        .commit();
                button_mispublicaciones.setBackgroundColor(Color.parseColor("#009484"));
                button_perfil.setBackgroundColor(Color.parseColor("#0BC5B4"));
                floatingActionButton2.hide();
            }
        });

        button_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.perfil_container, new PerfilFragment())
                        .commit();
                button_perfil.setBackgroundColor(Color.parseColor("#009484"));
                button_mispublicaciones.setBackgroundColor(Color.parseColor("#0BC5B4"));
                floatingActionButton2.show();
            }
        });
    }

    private void findids() {
        button_mispublicaciones = findViewById(R.id.button_mispublicaciones);
        button_perfil = findViewById(R.id.button_perfil);
        perfil_container = findViewById(R.id.perfil_container);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);

    }

}
