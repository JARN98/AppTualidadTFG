package com.example.apptualidad.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.Comentarios;
import com.example.apptualidad.R;

import java.util.List;

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

    private void sets(int position, ViewHolder holder) {
        holder.textView_contenido.setText(mValues.get(position).getContenido());
        holder.textView_name_autor.setText(mValues.get(position).getNameAutor());

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
