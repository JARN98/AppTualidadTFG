package com.example.apptualidad;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.apptualidad.Adapters.MyNuevoNoticiasRecyclerViewAdapter;
import com.example.apptualidad.Generator.ServiceGeneratorNear;
import com.example.apptualidad.Generator.TipoAutenticacion;
import com.example.apptualidad.Generator.UtilToken;
import com.example.apptualidad.Model.NoticiaRes;
import com.example.apptualidad.Model.ResponseContainer;
import com.example.apptualidad.Services.DashboardService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locManager;
    private Location loc;
    private FusedLocationProviderClient fusedLocationClient;
    private List<NoticiaRes> noticiaResList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            loc = location;
                            LatLng casa = new LatLng(loc.getLatitude(), loc.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casa, 13));

                            getNoticiasCercanas(loc);
                        }
                    }
                });
    }

    private void getNoticiasCercanas(Location loc) {
        String lat = loc.getLatitude() + "";
        String lon = loc.getLongitude() + "";
        final Map<String, String> data = new HashMap<>();
        DashboardService service = ServiceGeneratorNear.createService(DashboardService.class, UtilToken.getToken(MapsActivity.this), TipoAutenticacion.JWT);
        Call<ResponseContainer<NoticiaRes>> call = service.getNoticiasGeo(lat, lon, "1000000", data);

        call.enqueue(new Callback<ResponseContainer<NoticiaRes>>() {
            @Override
            public void onResponse(Call<ResponseContainer<NoticiaRes>> call, Response<ResponseContainer<NoticiaRes>> response) {
                if (response.isSuccessful()) {
                    noticiaResList = response.body().getRows();

                    aniadirMarkers(noticiaResList);

                } else {
                    Toast.makeText(MapsActivity.this, "No se han podido traer las noticias con éxito", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<NoticiaRes>> call, Throwable t) {
                Log.e("NetworkFailure", t.getMessage());
                Toast.makeText(MapsActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aniadirMarkers(List<NoticiaRes> noticiaResList) {
        for (int i = 0; i < this.noticiaResList.size(); i++) {
            String[] parts = this.noticiaResList.get(i).getLocalizacion().split(",");
            double lat = Double.valueOf(parts[0]);
            double lon = Double.valueOf(parts[1]);

            mMap.addMarker(new MarkerOptions().position(new LatLng(lon, lat)).title(this.noticiaResList.get(i).getTitle()));

        }
    }
}
