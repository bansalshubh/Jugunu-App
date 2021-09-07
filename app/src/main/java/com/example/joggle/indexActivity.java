package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Looper;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class indexActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
//    private static final int REQUEST_LOCATION = 1;
    Button btn1,btn2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    boolean firsttime = true;
    LocationManager locationManager;
    double longitude,latitude;
    String place;
    int PERMISSION_ID = 44;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        PrefManager prefManager = new PrefManager(getApplicationContext());
        if(prefManager.isFirstTimeLaunch()){
            prefManager.setFirstTimeLaunch(false);
            startActivity(new Intent(indexActivity.this, WelcomeActivity.class));
            finish();
        }
        Intent intent1 = getIntent();
        final String pn = intent1.getStringExtra("message");
//        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(indexActivity.this);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mFusedLocationClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(final Location location) {
                                        if (location != null) {
                                            double latit = location.getLatitude();
                                            double longit = location.getLongitude();
                                            place = getAddress(latit, longit);
                                            Toast.makeText(indexActivity.this, place, Toast.LENGTH_SHORT).show();
                                            final UserHelperClass helperClass = new UserHelperClass(null,null,pn,place);
                                            assert pn != null;
//                                            DbHandler handler = new DbHandler(getApplicationContext(),"userdb",null,1);
//                                            if(handler.addUser(helperClass))
//                                                Toast.makeText(indexActivity.this, "User added Successfully", Toast.LENGTH_SHORT).show();;
                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            reference = firebaseDatabase.getReference("Users");
                                            reference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    // inside the method of on Data change we are setting
                                                    // our object class to our database reference.
                                                    // data base reference will sends data to firebase.
                                                    reference.child(pn).setValue(helperClass);

                                                    // after adding this data we are showing toast message.
//                                                    Toast.makeText(indexActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    // if the data is not added or it is cancelled then
                                                    // we are displaying a failure toast message.
                                                    Toast.makeText(indexActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            Intent intent = new Intent(indexActivity.this, MainActivity.class);
                                            intent.putExtra("place",place);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(indexActivity.this, "Unable to find location", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
                    }
                }
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(indexActivity.this);
                alert.setTitle("Location");
                alert.setMessage("Enter Your Location");
// Set an EditText view to get user input
                final EditText input = new EditText(indexActivity.this);
                alert.setView(input);
                alert.setCancelable(false);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        place = input.getText().toString();
                        Intent sintent = new Intent(indexActivity.this, HomePage.class);
                        sintent.putExtra("place",place);
                        startActivity(sintent);
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                        Toast.makeText(indexActivity.this, "Please give a valid Location ", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });
//        rd1=(RadioButton)findViewById(R.id.rd1);
//        rd2=(RadioButton)findViewById(R.id.rd2);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else 
                            {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
//            requestPermissions();
            getLastLocation();
        }
    }

        @SuppressLint("MissingPermission")
        private void requestNewLocationData() {

            // Initializing LocationRequest
            // object with appropriate methods
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5);
            mLocationRequest.setFastestInterval(0);
            mLocationRequest.setNumUpdates(1);

            // setting LocationRequest
            // on FusedLocationClient
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        }

//        private LocationCallback mLocationCallback = new LocationCallback() {
//
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                Location mLastLocation = locationResult.getLastLocation();
//                latitude = mLastLocation.getLatitude();
//                longitude = mLastLocation.getLongitude();
//            }
//        };

        private boolean checkPermissions() {
            return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            // If we want background location
            // on Android 10.0 and higher,
            // use:
            // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
        }

    private void requestPermissions(String[] strings) {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

        private boolean isLocationEnabled() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        if(firsttime) {
//            if (checkPermissions()&&isLocationEnabled()) {
//                getLastLocation();
//            }
//            firsttime=false;
//        }
//    }

//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//            if (requestCode == PERMISSION_ID) {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getLastLocation();
//                }
//            }
//        }




//        // Check which radio button was clicked
//        switch (view.getId()) {
//            case R.id.rd1:
//                if (checked) {
//                    ActivityCompat.requestPermissions(indexActivity.this,
//                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                        OnGPS();
//                    }
//                    else {
//                        getLocation();
//                    }
//                    Toast.makeText(indexActivity.this, "Location added Successfully", Toast.LENGTH_LONG).show();

//                }
//                break;

//            case R.id.rd2:
//                if (checked) {
//
//                Toast.makeText(indexActivity.this, "Your Location is "+place, Toast.LENGTH_LONG).show();
//                    break;
//                }




//    public void OnGPS()
//    {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
////                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
//            }
//        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        final AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        Toast.makeText(this, "OnGPS end", Toast.LENGTH_SHORT).show();
//    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(indexActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
//            add = add + " " + obj.getCountryName();
//            add = add + " " + obj.getCountryCode();
//            add = add + " " + obj.getAdminArea();
//            add = add + " " + obj.getPostalCode();
//            add = add + " " + obj.getSubAdminArea();
//            add = add + "\t" + obj.getLocality();
//            add = add + "\t" + obj.getSubThoroughfare();
//            System.out.println(place);
            return add;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }

//    public void getLocation()
//    {
//        if(ActivityCompat.checkSelfPermission(
//                indexActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        indexActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
//        }
//        else {
//            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            if (locationGPS != null) {
//                double lat = locationGPS.getLatitude();
//                double longi = locationGPS.getLongitude();
//                place = getAddress(lat,longi);
//                Toast.makeText(this, place, Toast.LENGTH_SHORT).show();
//                latitude = String.valueOf(lat);
//                longitude = String.valueOf(longi);
//                Intent intent = new Intent(indexActivity.this, HomePage.class);
//                intent.putExtra("place",place);
//                startActivity(intent);
//                Toast.makeText(this, "Your Location is "+place, Toast.LENGTH_SHORT).show();
//                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
//            } else {
//                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}

