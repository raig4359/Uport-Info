package com.raig.uportinfo.data;

/**
 * Created by Gaurav on 18-01-2018.
 */

public class CameraPhotoEvent {

    String mCurrentPhotoPath;

    public CameraPhotoEvent(String mCurrentPhotoPath) {
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
}
