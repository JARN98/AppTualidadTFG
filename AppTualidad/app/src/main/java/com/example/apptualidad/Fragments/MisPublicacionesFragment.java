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

import com.example.apptualidad.Adapters.MyMisPublicacionesRecyclerViewAdapter;
import com.example.apptualidad.Adapters.MyNuevoNoticiasRecyclerViewAdapter;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.ResponseContainer;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.MisPublicacionesResponse;
import com.example.apptualidad.Services.UserService;
import com.example.apptualidad.ViewModels.EliminarNoticia;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MisPublicacionesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private listaNoticiasInterface mListener;
    private List<MisPublicacionesResponse> listaNoticias;
    private MyMisPublicacionesRecyclerViewAdapter adapter;
    private Context cxt;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MisPublicacionesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MisPublicacionesFragment newInstance(int columnCount) {
        MisPublicacionesFragment fragment = new MisPublicacionesFragment();
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
        View view = inflater.inflate(R.layout.fragment_mispublicaciones_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            cxt = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, mColumnCount));
            }
            listaNoticias = new ArrayList<>();

            getPublicaciones(recyclerView);

            EliminarNoticia eliminarNoticia = ViewModelProviders.of((FragmentActivity) getContext()).get(EliminarNoticia.class);

            eliminarNoticia.getAll().observe(getActivity(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    getPublicaciones(recyclerView);
                }
            });



        }
        return view;
    }

    private void getPublicaciones(final RecyclerView recyclerView) {
        UserService service = ServiceGenerator.createService(UserService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);

        Call<ResponseContainer<MisPublicacionesResponse>> call = service.getMisNoticias();

        call.enqueue(new Callback<ResponseContainer<MisPublicacionesResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<MisPublicacionesResponse>> call, Response<ResponseContainer<MisPublicacionesResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCount() == 0) {
                        Toast.makeText(getContext(), "No tienes ninguna noticia publicada", Toast.LENGTH_SHORT).show();
                    } else {
                        listaNoticias = response.body().getRows();

                        adapter = new MyMisPublicacionesRecyclerViewAdapter(
                                listaNoticias,
                                mListener,
                                cxt
                        );
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Fallo al traer mis noticias", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<MisPublicacionesResponse>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof listaNoticiasInterface) {
            mListener = (listaNoticiasInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement listaNoticiasInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
