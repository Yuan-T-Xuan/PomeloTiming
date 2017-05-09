package cn.edu.fudan.xuan.mytimemanagement;

import android.os.Bundle;

import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapActivity;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

public class XyMapActivity extends MapActivity {
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        double lat = getIntent().getDoubleExtra("cn.edu.fudan.xuan.LAT", 0.0);
        double lon = getIntent().getDoubleExtra("cn.edu.fudan.xuan.LON", 0.0);
        setContentView(R.layout.activity_map);
        mapView = (MapView)findViewById(R.id.mapview) ;
        TencentMap tencentMap = mapView.getMap();
        tencentMap.setCenter(new LatLng(lat, lon));
        tencentMap.setZoom(14);
        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // ...
                return false;
            }
        });
        tencentMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker arg0) {
                // ...
            }
        });

        Marker marker = tencentMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                //.title("2012-09-27 03：56").snippet("xcvghgtrdcvb nhgfcvbh")
                .icon(BitmapDescriptorFactory.defaultMarker()));
    }
}

