package com.raig.uportinfo.data;

import android.graphics.Bitmap;

/**
 * Created by Gaurav on 18-01-2018.
 */

public class GalleryBitmapEvent {

    Bitmap bitmap;
    String mCurrentPhotoPath;

    public GalleryBitmapEvent(Bitmap bitmap, String mCurrentPhotoPath) {
        this.bitmap = bitmap;
        this.mCurrentPhotoPath = mCurrentPhotoPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getmCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }
}
