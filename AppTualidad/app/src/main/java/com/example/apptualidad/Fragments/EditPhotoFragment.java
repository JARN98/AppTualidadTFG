package com.example.apptualidad.Fragments;

import android.app.Activity;
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

import com.example.apptualidad.Adapters.MyEditPhotoRecyclerViewAdapter;
import com.example.apptualidad.Generator.ServiceGenerator;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.Photo;
import com.example.apptualidad.PhotoActivity;
import com.example.apptualidad.R;
import com.example.apptualidad.Responses.GetOneNoticiaResponse;
import com.example.apptualidad.Responses.UploadPhotoUser;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.ViewModels.EliminarFoto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditPhotoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private listaNoticiasInterface mListener;
    private List<Photo> listaFotos;
    private MyEditPhotoRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EditPhotoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EditPhotoFragment newInstance(int columnCount) {
        EditPhotoFragment fragment = new EditPhotoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editphoto_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            final Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            getPhotos(context, recyclerView);

            EliminarFoto eliminarFoto = ViewModelProviders.of((FragmentActivity) getContext()).get(EliminarFoto.class);

            eliminarFoto.getAll().observe(getActivity(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    getPhotos(context, recyclerView);
                }
            });


        }
        return view;
    }

    private void getPhotos(final Context context, final RecyclerView recyclerView) {
        PhotoActivity activity = (PhotoActivity) getActivity();

        listaFotos = new ArrayList<>();

        DashboardService service = ServiceGenerator.createService(DashboardService.class, UtilToken.getToken(context), TipoAutenticacion.JWT);
        Call<GetOneNoticiaResponse> call = service.getOneNoticia(activity.getIdNoticia());

        call.enqueue(new Callback<GetOneNoticiaResponse>() {
            @Override
            public void onResponse(Call<GetOneNoticiaResponse> call, Response<GetOneNoticiaResponse> response) {
                if (response.isSuccessful()) {
                    listaFotos = response.body().getPhotos();

                    adapter = new MyEditPhotoRecyclerViewAdapter(
                            listaFotos,
                            mListener,
                            context
                    );

                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, "Fallo al traer las fotos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetOneNoticiaResponse> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
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
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
