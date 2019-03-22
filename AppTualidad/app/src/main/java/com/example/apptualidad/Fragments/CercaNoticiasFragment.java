package com.example.apptualidad.Fragments;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import com.example.apptualidad.Generator.ServiceGeneratorNear;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Listener.listaNoticiasInterface;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.ResponseContainer;
import com.example.apptualidad.R;
import com.example.apptualidad.Services.DashboardService;
import com.example.apptualidad.ViewModels.AplicarFiltroDistanciaViewModel;
import com.example.apptualidad.ViewModels.AplicarFiltroViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CercaNoticiasFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private listaNoticiasInterface mListener;
    private FusedLocationProviderClient fusedLocationClient;
    Location loc;
    private List<NoticiaRes> listaNoticias;
    private MyNuevoNoticiasRecyclerViewAdapter adapter;
    private LocationManager locManager;
    private Context cxt;
    String lat, lon, maxDistance = "1000";

    public CercaNoticiasFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CercaNoticiasFragment newInstance(int columnCount) {
        CercaNoticiasFragment fragment = new CercaNoticiasFragment();
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
        View view = inflater.inflate(R.layout.fragment_nuevonoticias_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            cxt = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(cxt));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(cxt, mColumnCount));
            }
            locManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
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

            AplicarFiltroDistanciaViewModel aplicarFiltroDistanciaViewModel = ViewModelProviders.of((DashboardActivity) cxt).get(AplicarFiltroDistanciaViewModel.class);

            aplicarFiltroDistanciaViewModel.getAll().observe(getActivity(), new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    maxDistance = s + "000";
                }
            });
            getNoticiasAutorizacionLocalizacion(data, recyclerView);

        }
        return view;
    }

    private void getNoticiasAutorizacionLocalizacion(final Map<String, String> data, final RecyclerView recyclerView) {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        loc = location;
                        getNoticiasCercaDeMi(data, recyclerView);
                    }
                });
    }

    private void getNoticiasCercaDeMi(Map<String, String> data, final RecyclerView recyclerView) {
        lat = loc.getLatitude() + "";
        lon = loc.getLongitude() + "";

        DashboardService service = ServiceGeneratorNear.createService(DashboardService.class, UtilToken.getToken(getContext()), TipoAutenticacion.JWT);
        Call<ResponseContainer<NoticiaRes>> call = service.getNoticiasGeo(lat, lon, maxDistance, data);

        call.enqueue(new Callback<ResponseContainer<NoticiaRes>>() {
            @Override
            public void onResponse(Call<ResponseContainer<NoticiaRes>> call, Response<ResponseContainer<NoticiaRes>> response) {
                if (response.isSuccessful()) {
                    listaNoticias = response.body().getRows();

                    if (listaNoticias.size() < 1) {
                        Toast.makeText(cxt, "No hay ninguna respuesta, debe indicar la distancia máxima haciendo click en el filtro", Toast.LENGTH_LONG).show();
                    }

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
