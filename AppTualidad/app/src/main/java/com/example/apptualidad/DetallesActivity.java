package com.example.apptualidad;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptualidad.Adapters.ImageAdapter;
import com.example.apptualidad.Adapters.MyMisPublicacionesRecyclerViewAdapter;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Model.User;
import com.example.apptualidad.Responses.GetOneNoticiaResponse;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.Services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallesActivity extends AppCompatActivity {
    ViewPager viewPager;
    private TextView textView_title, textView_descripcion, textView_lugar, textView_likes;
    private ImageView imageView_comments, imageView_like;
    private List<String> imagenes;
    private String id;
    private GetOneNoticiaResponse noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        findids();


    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(DetallesActivity.this), TipoAutenticacion.JWT);

        Call<GetOneNoticiaResponse> call = service.getOneNoticia(id);

        call.enqueue(new Callback<GetOneNoticiaResponse>() {
            @Override
            public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                if (response.isSuccessful()) {
                    noticia = response.body();

                    sets();
                } else {
                    Toast.makeText(DetallesActivity.this, "Fallo al cargar la noticia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(DetallesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sets() {
        textView_descripcion.setText(noticia.getDescription());
        textView_title.setText(noticia.getTitle());
        textView_likes.setText(noticia.getLikes());

        imagenes = noticia.getPhotoLink();

        ImageAdapter imageAdapter = new ImageAdapter(DetallesActivity.this, imagenes);


        viewPager.setAdapter(imageAdapter);

        pintarSiFav();

        darLike();

        imageView_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetallesActivity.this, ComentariosActivity.class);
                i.putExtra("id", noticia.getId());
                startActivity(i);
            }
        });
    }

    private void findids() {
        textView_likes = findViewById(R.id.textView_likes);
        viewPager = findViewById(R.id.viewPager);
        textView_title = findViewById(R.id.textView_title);
        textView_descripcion = findViewById(R.id.textView_descripcion);
        textView_lugar = findViewById(R.id.textView_lugar);
        imageView_comments = findViewById(R.id.imageView_comments);
        imageView_like = findViewById(R.id.imageView_like);
    }

    private void pintarSiFav() {

        List<String> listaFavs;
        listaFavs = new ArrayList<>(Arrays.asList(UtilUser.getFavs(DetallesActivity.this)));
        int index = 0;
        boolean encontrado = false;
        while (!encontrado && index < listaFavs.size()) {
            if (listaFavs.get(index).equals(noticia.getId())) {
                encontrado = true;
            } else {
                index++;
            }
        }

        if (encontrado) {
            imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    private void darLike() {
        imageView_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(DetallesActivity.this), TipoAutenticacion.JWT);
                Call<User> call = service.addFav(noticia.getId());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {

                            boolean esfav = esFav();
                            if (esfav) {
                                int nuevosLikes = Integer.valueOf(textView_likes.getText().toString()) - 1;
                                textView_likes.setText(String.valueOf(nuevosLikes));
                                imageView_like.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            } else {
                                int nuevosLikes = Integer.valueOf(textView_likes.getText().toString()) + 1;
                                textView_likes.setText(String.valueOf(nuevosLikes));
                                imageView_like.setImageResource(R.drawable.ic_favorite_black_24dp);
                            }

                            UtilUser.setUserInfo(DetallesActivity.this, response.body());

                        } else {
                            Toast.makeText(DetallesActivity.this, "Fallo a dar like", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(DetallesActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean esFav() {

        List<String> listaFavs;
        listaFavs = new ArrayList<>(Arrays.asList(UtilUser.getFavs(DetallesActivity.this)));
        int index = 0;
        boolean encontrado = false;
        while (!encontrado && index < listaFavs.size()) {
            if (listaFavs.get(index).equals(noticia.getId())) {
                encontrado = true;
            } else {
                index++;
            }
        }

        return encontrado;
    }
}
