package com.kcb.teacher.activity;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class CutPictureActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "CutPictureActivity";
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    Bitmap mBitMap;
    Bitmap croppedImage;
    private CropImageView cropImageView;

    private ButtonFlat rotateButton;
    private ButtonFlat completeButton;
    private ButtonFlat cancelButton;

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(ASPECT_RATIO_X, mAspectRatioX);
        bundle.putInt(ASPECT_RATIO_Y, mAspectRatioY);
    }

    // Restores the state upon rotating the screen/restarting the activity
    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        mAspectRatioX = bundle.getInt(ASPECT_RATIO_X);
        mAspectRatioY = bundle.getInt(ASPECT_RATIO_Y);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tch_activity_cutpicture);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setImageBitmap(mBitMap);
        rotateButton = (ButtonFlat) findViewById(R.id.Button_rotate);
        rotateButton.setOnClickListener(this);

        completeButton = (ButtonFlat) findViewById(R.id.button_complete);
        completeButton.setOnClickListener(this);

        cancelButton = (ButtonFlat) findViewById(R.id.button_back);
        cancelButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Uri uri = (Uri) getIntent().getParcelableExtra("PICTURE");
        try {
            mBitMap = Media.getBitmap(getContentResolver(), uri);
            mBitMap = ResizeBitmap(mBitMap, 1000);
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Button_rotate:
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
                break;
            case R.id.button_complete:
                croppedImage = cropImageView.getCroppedImage();
                Intent intent = new Intent();
                intent.putExtra("CUTTED_PICTURE", Uri.parse(MediaStore.Images.Media.insertImage(
                        getContentResolver(), croppedImage, null, null)));
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }

    private Bitmap ResizeBitmap(Bitmap bitmap, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float temp = ((float) height) / ((float) width);
        int newHeight = (int) ((newWidth) * temp);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }
}
