package com.example.apptualidad.Adapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.apptualidad.ComentariosActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.Comentarios;
import com.example.apptualidad.R;
import com.example.apptualidad.Services.ComentarioService;
import com.example.apptualidad.ViewModels.AniadirComentario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyComentariosRecyclerViewAdapter extends RecyclerView.Adapter<MyComentariosRecyclerViewAdapter.ViewHolder> {

    private final List<Comentarios> mValues;
    private final listaNoticiasInterface mListener;
    private Context cxt;

    public MyComentariosRecyclerViewAdapter(List<Comentarios> items, listaNoticiasInterface listener, Context context) {
        mValues = items;
        mListener = listener;
        cxt = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comentarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        sets(position, holder);

    }

    private void sets(final int position, final ViewHolder holder) {
        holder.textView_contenido.setText(mValues.get(position).getContenido());
        holder.textView_name_autor.setText(mValues.get(position).getNameAutor());

        if (!mValues.get(position).getPictureAutor().isEmpty()) {
            Glide
                    .with(cxt)
                    .load(mValues.get(position).getPictureAutor())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageView_avatar);
        }

        if (!mValues.get(position).getEmailAutor().equals(UtilUser.getEmail(cxt))) {
            holder.imageView_delete.setVisibility(View.INVISIBLE);
        }

        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoEliminarComentario(position, holder);
            }
        });


    }

    private void abrirDialogoEliminarComentario(final int position, final ViewHolder holder) {

        AlertDialog.Builder builder = new AlertDialog.Builder(cxt);

        builder.setMessage(R.string.dialog_message2)
                .setTitle(R.string.dialog_title4);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                eliminarComentario(position, holder);
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

    private void eliminarComentario(final int position, ViewHolder holder) {

        ComentarioService service = ServiceGenerator.createService(ComentarioService.class, UtilToken.getToken(cxt), TipoAutenticacion.JWT);

        Call<Comentarios> call = service.deleteComment(mValues.get(position).getId());

        call.enqueue(new Callback<Comentarios>() {
            @Override
            public void onResponse(Call<Comentarios> call, Response<Comentarios> response) {
                if (response.isSuccessful()) {
                    AniadirComentario aniadirComentario = ViewModelProviders.of((FragmentActivity) cxt).get(AniadirComentario.class);

                    aniadirComentario.selectedAplicar("si");

                    Toast.makeText(cxt, "Comentario borrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(cxt, "No ha sido posible borrar el comentario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comentarios> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(cxt, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Comentarios mItem;
        public final TextView textView_name_autor, textView_contenido;
        public final ImageView imageView_avatar, imageView_delete;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView_name_autor = view.findViewById(R.id.textView_name_autor);
            textView_contenido = view.findViewById(R.id.textView_contenido);
            imageView_avatar = view.findViewById(R.id.imageView_avatar);
            imageView_delete = view.findViewById(R.id.imageView_delete);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getContenido() + "'";
        }
    }
}
