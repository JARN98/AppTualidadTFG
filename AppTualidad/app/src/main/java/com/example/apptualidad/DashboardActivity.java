package com.example.apptualidad;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptualidad.Fragments.CercaNoticiasFragment;
import com.example.apptualidad.Fragments.NuevoNoticiasFragment;
import com.example.apptualidad.Fragments.DestacadoNoticiasFragment;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.ViewModels.AplicarFiltroDistanciaViewModel;
import com.example.apptualidad.ViewModels.AplicarFiltroViewModel;

public class DashboardActivity extends AppCompatActivity implements listaNoticiasInterface {
    private Button button_nuevo, button_destacado, button_cerca;
    private FrameLayout contenedor_listas;
    private EditText editText_buscar;
    private ImageView imageView_filter;
    private int queFragmentoEsta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findids();

        navigation();

        events();
    }

    private void events() {
        editText_buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                AplicarFiltroViewModel aplicarFiltroViewModel = ViewModelProviders.of(DashboardActivity.this).get(AplicarFiltroViewModel.class);

                aplicarFiltroViewModel.selectedAplicar(s + "");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_listas, new DestacadoNoticiasFragment())
                        .commit();

                irAEsteFragmento(queFragmentoEsta);
            }
        });

        imageView_filter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
               crearDialogo();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);

        LayoutInflater inflater = DashboardActivity.this.getLayoutInflater();




        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);

        View layout = inflater.inflate(R.layout.filter_dialog, (ViewGroup) findViewById(R.id.contraint_dialog));
        builder.setView(layout);


        SeekBar seekBar = (SeekBar)layout.findViewById(R.id.seekBar);
        final TextView textView_km = layout.findViewById(R.id.textView_km);
        final String distancia;

        /*seekBar.getThumb().setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);*/
        seekBar.getProgressDrawable().setColorFilter(R.color.colorPrimary, PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                textView_km.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AplicarFiltroDistanciaViewModel aplicarFiltroDistanciaViewModel = ViewModelProviders.of(DashboardActivity.this).get(AplicarFiltroDistanciaViewModel.class);
                button_cerca.setBackgroundColor(Color.parseColor("#009484"));
                button_destacado.setBackgroundColor(Color.parseColor("#0BC5B4"));
                button_nuevo.setBackgroundColor(Color.parseColor("#0BC5B4"));
                irAEsteFragmento(2);
                aplicarFiltroDistanciaViewModel.selectedAplicar(textView_km.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void irAEsteFragmento(int queFragmentoEsta) {

        switch (queFragmentoEsta) {
            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_listas, new NuevoNoticiasFragment())
                        .commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_listas, new DestacadoNoticiasFragment())
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_listas, new CercaNoticiasFragment())
                        .commit();
                break;
            default:
                Toast.makeText(this, "¿Que has liado?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        queFragmentoEsta = 0;

        irAEsteFragmento(queFragmentoEsta);

        button_nuevo.setBackgroundColor(Color.parseColor("#009484"));
        button_destacado.setBackgroundColor(Color.parseColor("#0BC5B4"));
        button_cerca.setBackgroundColor(Color.parseColor("#0BC5B4"));
    }

    private void navigation() {
        button_nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_nuevo.setBackgroundColor(Color.parseColor("#009484"));
                button_destacado.setBackgroundColor(Color.parseColor("#0BC5B4"));
                button_cerca.setBackgroundColor(Color.parseColor("#0BC5B4"));
                queFragmentoEsta = 0;

                irAEsteFragmento(queFragmentoEsta);
            }
        });
        button_destacado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_destacado.setBackgroundColor(Color.parseColor("#009484"));
                button_nuevo.setBackgroundColor(Color.parseColor("#0BC5B4"));
                button_cerca.setBackgroundColor(Color.parseColor("#0BC5B4"));
                queFragmentoEsta = 1;

                irAEsteFragmento(queFragmentoEsta);
            }
        });
        button_cerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_cerca.setBackgroundColor(Color.parseColor("#009484"));
                button_destacado.setBackgroundColor(Color.parseColor("#0BC5B4"));
                button_nuevo.setBackgroundColor(Color.parseColor("#0BC5B4"));
                queFragmentoEsta = 2;

                irAEsteFragmento(queFragmentoEsta);
            }
        });
    }

    private void findids() {
        button_nuevo = findViewById(R.id.button_nuevo);
        button_destacado = findViewById(R.id.button_destacado);
        button_cerca = findViewById(R.id.button_cerca);
        contenedor_listas = findViewById(R.id.contenedor_listas);
        editText_buscar = findViewById(R.id.editText_buscar);
        imageView_filter = findViewById(R.id.imageView_filter);
    }

}
