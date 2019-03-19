package com.example.apptualidad.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.apptualidad.Adapters.MyComentariosRecyclerViewAdapter;
import com.example.apptualidad.Adapters.MyNuevoNoticiasRecyclerViewAdapter;
import com.example.apptualidad.ComentariosActivity;
import com.example.apptualidad.DetallesActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.Comentarios;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.GetOneNoticiaResponse;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.ViewModels.AniadirComentario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComentariosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private listaNoticiasInterface mListener;
    private List<Comentarios> comentariosList;
    private MyComentariosRecyclerViewAdapter adapter;
    private  Context cxt;


    public ComentariosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ComentariosFragment newInstance(int columnCount) {
        ComentariosFragment fragment = new ComentariosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comentarios_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            cxt = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, mColumnCount));
            }

            getComentarios(recyclerView);

            AniadirComentario aniadirComentario = ViewModelProviders.of((FragmentActivity) getContext()).get(AniadirComentario.class);

            aniadirComentario.getAll().observe(getActivity(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    getComentarios(recyclerView);
                }
            });

        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof listaNoticiasInterface) {
            mListener = (listaNoticiasInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getComentarios(final RecyclerView recyclerView) {

        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);


        ComentariosActivity activity = (ComentariosActivity) getActivity();

        Call<GetOneNoticiaResponse> call = service.getOneNoticia(activity.getIdNoticia());

        call.enqueue(new Callback<GetOneNoticiaResponse>() {
            @Override
            public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                if (response.isSuccessful()) {
                    comentariosList = response.body().comentarios;

                    adapter = new MyComentariosRecyclerViewAdapter  (
                            comentariosList,
                            mListener,
                            cxt

                    );

                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "Fallo al cargar la noticia", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
