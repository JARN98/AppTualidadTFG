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
import com.example.apptualidad.ComentariosActivity;
import com.example.apptualidad.DashboardActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Generator.UtilUser;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.Photo;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.UploadPhotoUser;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.ViewModels.EliminarFoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyEditPhotoRecyclerViewAdapter extends RecyclerView.Adapter<MyEditPhotoRecyclerViewAdapter.ViewHolder> {

    private final List<Photo> mValues;
    private final listaNoticiasInterface mListener;
    private Context context;

    public MyEditPhotoRecyclerViewAdapter(List<Photo> items, listaNoticiasInterface listener, Context cxt) {
        context = cxt;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_editphoto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        sets(position, holder);
    }

    private void sets(final int position, ViewHolder holder) {
        holder.imageView_photo.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(context)
                .load(mValues.get(position).getLink())
                .into(holder.imageView_photo);

        holder.textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage(R.string.dialog_message2)
                        .setTitle(R.string.dialog_title3);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        borrarFoto(position);
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
        });

    }

    private void borrarFoto(int position) {
        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(context), TipoAutenticacion.JWT);

        Call<NoticiaRes> call = service.deletePhoto(mValues.get(position).getId());

        call.enqueue(new Callback<NoticiaRes>() {
            @Override
            public void onResponse(Call<NoticiaRes> call, Response<NoticiaRes> response) {
                if (response.isSuccessful()) {
                    EliminarFoto eliminarFoto = ViewModelProviders.of((FragmentActivity) context).get(EliminarFoto.class);
                    eliminarFoto.selectedAplicar("si");
                } else {
                    Toast.makeText(context, "Fallo al eliminar foto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NoticiaRes> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Photo mItem;
        public final ImageView imageView_photo;
        public final TextView textView4;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView_photo = view.findViewById(R.id.imageView_photo);
            textView4 = view.findViewById(R.id.textView4);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getId() + "'";
        }
    }
}
