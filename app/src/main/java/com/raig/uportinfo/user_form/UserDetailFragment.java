package com.raig.uportinfo.user_form;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.raig.uportinfo.FileUtility;
import com.raig.uportinfo.R;
import com.raig.uportinfo.UIFunctions;
import com.raig.uportinfo.data.CameraPhotoEvent;
import com.raig.uportinfo.data.GalleryBitmapEvent;
import com.raig.uportinfo.data.UserProfileEvent;
import com.raig.uportinfo.data.UserProfileModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailFragment extends Fragment {

    View view;

    @BindView(R.id.iv_user)
    ImageView ivUser;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_pin_code)
    EditText etPincode;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.et_state)
    EditText etState;
    @BindView(R.id.et_country)
    EditText etCountry;

    UIFunctions uiFunctions;
    UserProfileModel profileModel;

    private OnFormInteractionListener mListener;
    private final static String USER_PROFILE_PARAM = "userProfileModel";

    String mCurrentPhotoPath;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(UserProfileModel model) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_PROFILE_PARAM, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileModel = getArguments().getParcelable(USER_PROFILE_PARAM);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("User Information");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uiFunctions = new UIFunctions();
        if (mCurrentPhotoPath != null) {
            float scale = getResources().getDisplayMetrics().density;
            int dpWidthInPx = (int) (110 * scale);
            int dpHeightInPx = (int) (110 * scale);
            setPic(dpWidthInPx, dpHeightInPx);
        }
    }

    @OnClick(R.id.bt_next)
    public void showVehicleDetailScreen() {
        if (validateUserInfoForm()) {
            setData();
            EventBus.getDefault().post(new UserProfileEvent(profileModel));
            Toast.makeText(getActivity(), "User Information saved!", Toast.LENGTH_SHORT).show();
            mListener.showVehicleInfoForm();
        }
    }

    private void setData() {
        profileModel.setName(etName.getText().toString().trim());
        profileModel.setEmail(etEmail.getText().toString().trim());
        profileModel.setMobileNo(etMobile.getText().toString().trim());
        profileModel.setCompanyName(etCompanyName.getText().toString().trim());
        profileModel.setAddress(etAddress.getText().toString().trim());
        profileModel.setPincode(etPincode.getText().toString().trim());
        profileModel.setCountry(etCountry.getText().toString().trim());
        profileModel.setCity(etCity.getText().toString().trim());
        profileModel.setState(etState.getText().toString().trim());
    }

    private boolean validateUserInfoForm() {

        if (uiFunctions.getTextValues(etName).isEmpty() || uiFunctions.getTextValues(etEmail).isEmpty() ||
                uiFunctions.getTextValues(etMobile).isEmpty() || uiFunctions.getTextValues(etCompanyName).isEmpty() ||
                uiFunctions.getTextValues(etPincode).isEmpty() || uiFunctions.getTextValues(etAddress).isEmpty() ||
                uiFunctions.getTextValues(etCity).isEmpty() || uiFunctions.getTextValues(etState).isEmpty() ||
                uiFunctions.getTextValues(etCountry).isEmpty()) {

            uiFunctions.showMessage(view, "All fields are mandatory!", Snackbar.LENGTH_LONG);

            return false;

        } else {
            if (uiFunctions.isEmailValid(etEmail.getText().toString().trim())) {
                return true;
            } else {
                uiFunctions.showMessage(view, "Enter a valid email id!", Snackbar.LENGTH_LONG);
                return false;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormInteractionListener) {
            mListener = (OnFormInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFormInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.iv_user)
    public void onImageClick() {
        mListener.requestPermission();
    }

    @SuppressWarnings("ALL")
    private void setPic(final int targetW, final int targetH) {

        new AsyncTask<Void, Void, Bitmap>() {

            @Override
            protected Bitmap doInBackground(Void... params) {

                // Get the dimensions of the bitmap
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;

                // Determine how much to scale down the image
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

                // Decode the image file into a Bitmap sized to fill the View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                ivUser.setImageBitmap(bitmap);
            }

        }.execute();

    }

    @SuppressWarnings("ALL")
    private void writeBitmapIntoFile(Bitmap bitmap, final int targetW, final int targetH) {
        new AsyncTask<Bitmap, Void, Void>() {

            @Override
            protected Void doInBackground(Bitmap... bitmaps) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 70 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

                    //write the bytes in file
                    FileOutputStream fos = null;
                    File imgFile = FileUtility.createImageFile(getActivity().getApplicationContext());
                    mCurrentPhotoPath = imgFile.getAbsolutePath();
                    fos = new FileOutputStream(imgFile);

                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setPic(targetW, targetH);
            }

        }.execute(bitmap);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSetPicEvent(CameraPhotoEvent cameraPhotoEvent) {
        mCurrentPhotoPath = cameraPhotoEvent.getmCurrentPhotoPath();
        setPic(ivUser.getWidth(), ivUser.getHeight());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGalleryBitmapEvent(GalleryBitmapEvent bitmapEvent) {
        writeBitmapIntoFile(bitmapEvent.getBitmap(), ivUser.getWidth(), ivUser.getHeight());
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
