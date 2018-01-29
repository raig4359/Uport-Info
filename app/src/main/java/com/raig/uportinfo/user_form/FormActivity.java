package com.raig.uportinfo.user_form;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;
import com.raig.uportinfo.FileUtility;
import com.raig.uportinfo.R;
import com.raig.uportinfo.SharedPrefUtils;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.CameraPhotoEvent;
import com.raig.uportinfo.data.CreateResponse;
import com.raig.uportinfo.data.DriverInfoEvent;
import com.raig.uportinfo.data.DriverModel;
import com.raig.uportinfo.data.GalleryBitmapEvent;
import com.raig.uportinfo.data.UserProfileEvent;
import com.raig.uportinfo.data.UserProfileModel;
import com.raig.uportinfo.data.VehicleInfoEvent;
import com.raig.uportinfo.data.VehicleType;
import com.raig.uportinfo.login.LoginActivity;
import com.raig.uportinfo.network.RestClient;
import com.raig.uportinfo.rest_resource_model.AutoVariant;
import com.raig.uportinfo.rest_resource_model.AutoVariantResponse;
import com.raig.uportinfo.ui_components.CustomProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends PermisoActivity implements OnFormInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    CustomProgressDialog progressDialog;
    UIFunctions uiFunctions;

    ArrayList<AutoVariant> variantList;
    ArrayList<VehicleType> vehicleTypeList;
    ArrayList<DriverModel> driverList;
    UserProfileModel userProfileModel;

    String USER_FORM = "UserDetailFrag";
    String VEHICLE_FORM = "VehicleDetailFrag";
    String DRIVER_FORM = "DriverDetailFrag";
    String PREVIEW_FORM = "PreviewFrag";
    private String TAG = "FormActivity";

    String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 111;
    private static final int REQUEST_IMAGE_GALLERY = 222;

    static int MODE = 0;
    static final int CREATE_MODE = 1;
    static final int EDIT_MODE = 2;

    String userIdForEdit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(this);
        MODE = CREATE_MODE;
        init();
        getTruckTypes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ops, menu);
        return true;
    }

    private void init() {
        setSupportActionBar(toolbar);
        progressDialog = new CustomProgressDialog(this, false);
        uiFunctions = new UIFunctions();
        /*creating a empty user model*/
        userProfileModel = new UserProfileModel();
        vehicleTypeList = new ArrayList<>();
        /*adding a single empty vehicle type model*/
        vehicleTypeList.add(new VehicleType("", "", ""));
        driverList = new ArrayList<>();
        /*adding a single empty driver model*/
        driverList.add(new DriverModel("", "", "", "", ""));
        variantList = new ArrayList<>();
    }

    private void getTruckTypes() {
        RestClient.getRestClient().getUportService()
                .getTruckTypes().enqueue(new Callback<AutoVariantResponse>() {
            @Override
            public void onResponse(Call<AutoVariantResponse> call, Response<AutoVariantResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());

                AutoVariant variant1 = new AutoVariant();
                variant1.setId("-1");
                variant1.setTruckTypeName("Select Vehicle Type");

                variantList.add(variant1);
                variantList.addAll(response.body().getAutoVariant());

                progressBar.setVisibility(View.GONE);
                showUserInfoForm();
            }

            @Override
            public void onFailure(Call<AutoVariantResponse> call, Throwable t) {
                t.printStackTrace();
                uiFunctions.showMessage(rlRoot, "Something went wrong!", Snackbar.LENGTH_LONG);
            }
        });
    }

    public void showUserInfoForm() {
        setTitle("User Information");
        FragmentManager manager = getSupportFragmentManager();
        UserDetailFragment userDetailFragment = UserDetailFragment.newInstance(userProfileModel);
        manager.beginTransaction()
                .replace(R.id.frame, userDetailFragment, USER_FORM)
                .addToBackStack(USER_FORM)
                .commit();
    }

    @Override
    public void showVehicleInfoForm() {
        setTitle("Vehicle Information");
        FragmentManager manager = getSupportFragmentManager();
        VehicleDetailFragment vehicleDetailFragment = VehicleDetailFragment.newInstance(vehicleTypeList, variantList);
        manager.beginTransaction()
                .replace(R.id.frame, vehicleDetailFragment, VEHICLE_FORM)
                .addToBackStack(VEHICLE_FORM)
                .commit();
    }

    @Override
    public void showDriverDetailsForm() {
        setTitle("Driver Information");
        FragmentManager manager = getSupportFragmentManager();
        DriverDetailFragment driverDetailFragment = DriverDetailFragment.newInstance(driverList);
        manager.beginTransaction()
                .replace(R.id.frame, driverDetailFragment, DRIVER_FORM)
                .addToBackStack(DRIVER_FORM)
                .commit();
    }

    @Override
    public void onEditClick() {
        MODE = EDIT_MODE;
        FragmentManager manager = getSupportFragmentManager();
        manager.popBackStack(USER_FORM, 0);
    }

    @Override
    public void addNew() {
        MODE = CREATE_MODE;
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); ++i) {
            manager.popBackStack();
        }
        /*creating a empty user model*/
        userProfileModel = new UserProfileModel();

        vehicleTypeList.clear();
        /*adding a single empty vehicle type model*/
        vehicleTypeList.add(new VehicleType("", "", ""));

        driverList.clear();
        /*adding a single empty driver model*/
        driverList.add(new DriverModel("", "", "", "", ""));
        showUserInfoForm();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVehicleInfoEvent(VehicleInfoEvent vehicleInfoEvent) {
//        vehicleTypeList.clear();
//        vehicleTypeList.addAll(vehicleInfoEvent.getVehicleTypes());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDriverInfoEvent(DriverInfoEvent driverInfoEvent) {
//        driverList.clear();
//        driverList.addAll(driverInfoEvent.getDriverModels());
        if (MODE == CREATE_MODE) {
            uploadInformation();
        } else if (MODE == EDIT_MODE) {
            updateInformation();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProfileInfoEvent(UserProfileEvent profileEvent) {
//        userProfileModel = profileEvent.getProfileModel();
    }

    private void uploadInformation() {

        Toast.makeText(this, "Uploading your data, please wait!", Toast.LENGTH_SHORT).show();
        RequestBody requestBody;
        progressDialog.show();

        if (userProfileModel.getImagePath().isEmpty()) {
            requestBody = RequestBody.create(null, new byte[0]);
        } else {
            MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
            File file = new File(userProfileModel.getImagePath());
            requestBody = RequestBody.create(MEDIA_TYPE_JPG, file);
        }

        String userData = getUserDataInJsonSchema();

        RequestBody userDataBody =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, userData);

        RestClient.getRestClient()
                .getUportService()
                .uploadData(userDataBody, requestBody)
                .enqueue(new Callback<CreateResponse>() {
                    @Override
                    public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                        Log.e(TAG, " Retrofit --- onResponse: " + response.body().toString());
                        userIdForEdit = response.body().getUserDataInfo().get(0).getUserInfo().getUserId();
                        progressDialog.dismiss();
                        showPreviewForm();
                        Toast.makeText(FormActivity.this, "Information Uploaded Successfully!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<CreateResponse> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        uiFunctions.showMessage(rlRoot, "Something went wrong!", Snackbar.LENGTH_LONG);
                    }
                });
    }

    private void updateInformation() {
        Toast.makeText(this, "Uploading your data, please wait!", Toast.LENGTH_SHORT).show();
        RequestBody requestBody;
        progressDialog.show();

        if (userProfileModel.getImagePath().isEmpty()) {
            requestBody = RequestBody.create(null, new byte[0]);
        } else {
            MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
            File file = new File(userProfileModel.getImagePath());
            requestBody = RequestBody.create(MEDIA_TYPE_JPG, file);
        }

        String userData = getUserDataInJsonSchema();

        RequestBody userDataBody =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, userData);

        RestClient.getRestClient()
                .getUportService()
                .updateData(userDataBody, requestBody)
                .enqueue(new Callback<CreateResponse>() {
                    @Override
                    public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                        Log.e(TAG, " Retrofit --- onResponse: " + response.body().toString());
                        MODE = CREATE_MODE;
                        userIdForEdit = response.body().getUserDataInfo().get(0).getUserInfo().getUserId();
                        progressDialog.dismiss();
                        showPreviewForm();
                        Toast.makeText(FormActivity.this, "Information Uploaded Successfully!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<CreateResponse> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        uiFunctions.showMessage(rlRoot, "Something went wrong!", Snackbar.LENGTH_LONG);
                    }
                });
    }

    private String getUserDataInJsonSchema() {
        try {
            JSONObject userData = new JSONObject();

//            <------------ user info ----------------->
            JSONObject userInfo = new JSONObject();
            userInfo.put("name", userProfileModel.getName());
            userInfo.put("email", userProfileModel.getEmail());
            userInfo.put("mobile", userProfileModel.getMobileNo());
            userInfo.put("company_name", userProfileModel.getCompanyName());
            userInfo.put("address", userProfileModel.getAddress());
            userInfo.put("pincode", userProfileModel.getPincode());
            userInfo.put("city", userProfileModel.getCity());
            userInfo.put("state", userProfileModel.getState());
            userInfo.put("country", userProfileModel.getCountry());
            if (!userIdForEdit.isEmpty())
                userInfo.put("user_id", userIdForEdit);

//            <--------------- vehicle info --------------->
            JSONArray vehicleList = new JSONArray();
            for (VehicleType type : vehicleTypeList) {
                JSONObject vehicleObj = new JSONObject();
                vehicleObj.put("truck_type", type.getVehicleTypeName());
                vehicleObj.put("model", type.getVehicleModel());
                vehicleList.put(vehicleObj);
            }

//            <----------------- Driver info ----------------->
            JSONArray driverList = new JSONArray();
            for (DriverModel driverModel : this.driverList) {
                JSONObject driverObj = new JSONObject();
                driverObj.put("name", driverModel.getName());
                driverObj.put("email", driverModel.getEmail());
                driverObj.put("mobile", driverModel.getMobileNo());
                driverObj.put("experience", driverModel.getExperience());
                driverObj.put("truck_driven", driverModel.getVehiclesDriven());
                driverList.put(driverObj);
            }

            userData.put("userInfo", userInfo);
            userData.put("VehicleInfo", vehicleList);
            userData.put("DriverInfo", driverList);

            return userData.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showPreviewForm() {
        setTitle("Preview Information");
        FragmentManager manager = getSupportFragmentManager();
        PreviewFragment previewFragment = PreviewFragment.newInstance(userProfileModel, vehicleTypeList, driverList);
        manager.beginTransaction()
                .replace(R.id.frame, previewFragment, PREVIEW_FORM)
                .addToBackStack(PREVIEW_FORM)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                SharedPrefUtils.logOut(getApplicationContext());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void requestPermission() {
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {

                boolean isExternalStoragePermGranted = resultSet.isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean isCameraPermGranted = resultSet.isPermissionGranted(Manifest.permission.CAMERA);

                if (isExternalStoragePermGranted && isCameraPermGranted) {
                    //both permission granted
                    selectImage();

                } else if (isExternalStoragePermGranted) {
                    // Gallery permission granted!
                    galleryIntent();

                } else if (isCameraPermGranted) {
                    // Camera permission granted!
                    cameraIntent();

                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog("Title", "Message", null, callback);
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {

                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY);

    }

    private void cameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = FileUtility.createImageFile(getApplicationContext());
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.raig.uportinfo",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                EventBus.getDefault().post(new CameraPhotoEvent(mCurrentPhotoPath));
//                setPic(iv.getWidth(), ivContentImage.getHeight());
            } else if (requestCode == REQUEST_IMAGE_GALLERY) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    EventBus.getDefault().post(new GalleryBitmapEvent(bitmap, mCurrentPhotoPath));
//                    writeBitmapIntoFile(bitmap, ivContentImage.getWidth(), ivContentImage.getHeight());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
