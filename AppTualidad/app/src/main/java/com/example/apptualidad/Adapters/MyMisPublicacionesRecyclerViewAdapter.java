package com.example.apptualidad.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptualidad.AddNoticia;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.User;
import com.example.apptualidad.PerfilActivity;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.LoginResponse;
import com.example.apptualidad.Responses.MisPublicacionesResponse;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.Services.UserService;
import com.example.apptualidad.ViewModels.EliminarNoticia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyMisPublicacionesRecyclerViewAdapter extends RecyclerView.Adapter<MyMisPublicacionesRecyclerViewAdapter.ViewHolder> {
    private final List<MisPublicacionesResponse> mValues;
    private final listaNoticiasInterface mListener;
    private Context cxt;
    private boolean fav = false;

    public MyMisPublicacionesRecyclerViewAdapter(List<MisPublicacionesResponse> items, listaNoticiasInterface listener, Context context) {
        mValues = items;
        mListener = listener;
        cxt = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mispublicaciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        eventsMisPublicaciones(holder, position);
    }

    private void eventsMisPublicaciones(ViewHolder holder, int position) {
        fav = false;
        holder.textView_likes_mp.setText(mValues.get(position).getLikes());
        holder.textView_title_mp.setText(mValues.get(position).getTitle());

        if (!mValues.get(position).getFirstPhoto().equals("")) {
            holder.imageView_imagen_mp.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Glide
                    .with(cxt)
                    .load(mValues.get(position).getFirstPhoto())
                    .into(holder.imageView_imagen_mp);
        }


        pintarSiFav(position, holder);

        darLike(position, holder);

        eliminarNoticia(position, holder);

        editarNoticia(position, holder);
    }

    private void editarNoticia(final int position, ViewHolder holder) {
        holder.imageView_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cxt, AddNoticia.class);
                i.putExtra("id", mValues.get(position).getId());
                cxt.startActivity(i);
                PerfilActivity activity = (PerfilActivity) cxt;
                activity.finish();
            }
        });
    }

    private void eliminarNoticia(final int position, ViewHolder holder) {

        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(cxt), TipoAutenticacion.JWT);
                Call<LoginResponse> call = service.deleteNoticia(mValues.get(position).getId());

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(cxt, "Noticia eliminada con éxito", Toast.LENGTH_SHORT).show();
                            EliminarNoticia eliminarNoticia = ViewModelProviders.of((FragmentActivity) cxt).get(EliminarNoticia.class);

                            eliminarNoticia.selectedAplicar("si");
                        } else {
                            Toast.makeText(cxt, "No ha sido posible eliminar la noticia", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(cxt, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MisPublicacionesResponse mItem;
        public final ImageView imageView_imagen_mp, imageView_favourite_mp, imageView_edit, imageView_delete;
        public final TextView textView_title_mp, textView_likes_mp;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView_imagen_mp = view.findViewById(R.id.imageView_imagen_mp);
            imageView_favourite_mp = view.findViewById(R.id.imageView_favourite_mp);
            textView_title_mp = view.findViewById(R.id.textView_title_mp);
            textView_likes_mp = view.findViewById(R.id.textView_likes_mp);
            imageView_edit = view.findViewById(R.id.imageView_edit);
            imageView_delete = view.findViewById(R.id.imageView_delete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getId() + "'";
        }
    }

    private boolean esFav(int position) {

        List<String> listaFavs;
        listaFavs = new ArrayList<>(Arrays.asList(UtilUser.getFavs(cxt)));
        int index = 0;
        boolean encontrado = false;
        while (!encontrado && index < listaFavs.size()) {
            if (listaFavs.get(index).equals(mValues.get(position).getId())){
                encontrado = true;
            } else {
                index++;
            }
        }

        return encontrado;
    }

    private void pintarSiFav(int position, MyMisPublicacionesRecyclerViewAdapter.ViewHolder holder) {

        List<String> listaFavs;
        listaFavs = new ArrayList<>(Arrays.asList(UtilUser.getFavs(cxt)));
        int index = 0;
        boolean encontrado = false;
        while (!encontrado && index < listaFavs.size()) {
            if (listaFavs.get(index).equals(mValues.get(position).getId())){
                encontrado = true;
            } else {
                index++;
            }
        }

        if (encontrado) {
            holder.imageView_favourite_mp.setImageResource(R.drawable.ic_favorite_white_24dp);
            fav = true;
        }
    }

    private void darLike(final int position, final ViewHolder holder) {
        holder.imageView_favourite_mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(cxt), TipoAutenticacion.JWT);
                Call<User> call = service.addFav(mValues.get(position).getId());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {

                            boolean esfav = esFav(position);
                            if (esfav) {
                                int nuevosLikes = Integer.valueOf(holder.textView_likes_mp.getText().toString()) - 1;
                                holder.textView_likes_mp.setText(String.valueOf(nuevosLikes));
                                holder.imageView_favourite_mp.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                            } else {
                                int nuevosLikes = Integer.valueOf(holder.textView_likes_mp.getText().toString()) + 1;
                                holder.textView_likes_mp.setText(String.valueOf(nuevosLikes));
                                holder.imageView_favourite_mp.setImageResource(R.drawable.ic_favorite_white_24dp);
                            }

                            UtilUser.setUserInfo(cxt, response.body());

                        } else {
                            Toast.makeText(cxt, "Fallo a dar like", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(cxt, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
