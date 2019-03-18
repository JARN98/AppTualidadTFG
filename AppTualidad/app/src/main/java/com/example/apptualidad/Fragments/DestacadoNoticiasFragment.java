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

import com.example.apptualidad.Adapters.MyNuevoNoticiasRecyclerViewAdapter;
import com.example.apptualidad.DashboardActivity;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.ResponseContainer;
import com.example.apptualidad.R;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.ViewModels.AplicarFiltroViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DestacadoNoticiasFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private listaNoticiasInterface mListener;
    private List<NoticiaRes> listaNoticias;
    private MyNuevoNoticiasRecyclerViewAdapter adapter;
    private Context cxt;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DestacadoNoticiasFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static DestacadoNoticiasFragment newInstance(int columnCount) {
        DestacadoNoticiasFragment fragment = new DestacadoNoticiasFragment();
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
        View view = inflater.inflate(R.layout.fragment_nuevonoticias_list, container, false);

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
            final Map<String, String> data = new HashMap<>();

            AplicarFiltroViewModel aplicarFiltroViewModel = ViewModelProviders.of((DashboardActivity) cxt).get(AplicarFiltroViewModel.class);

            aplicarFiltroViewModel.getAll().observe(getActivity(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    data.put("title", s);
                    if (s == "") {
                        data.remove("title");
                    }
                }
            });

            getNoticiasDestacadas(recyclerView, data);
        }
        return view;
    }

    private void getNoticiasDestacadas(final RecyclerView recyclerView, Map<String, String> data) {
        data.put("sort", "-likes");
        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);
        Call<ResponseContainer<NoticiaRes>> call = service.getNoticias(data);

        call.enqueue(new Callback<ResponseContainer<NoticiaRes>>() {
            @Override
            public void onResponse(Call<ResponseContainer<NoticiaRes>> call, Response<ResponseContainer<NoticiaRes>> response) {
                if (response.isSuccessful()) {
                    listaNoticias = response.body().getRows();

                    adapter = new MyNuevoNoticiasRecyclerViewAdapter(
                            cxt,
                            listaNoticias,
                            mListener
                    );
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "No se han podido traer las noticias con éxito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<NoticiaRes>> call, Throwable t) {
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
