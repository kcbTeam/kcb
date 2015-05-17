package com.kcb.teacher.activity;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.edmodo.cropper.CropImageView;
import com.kcb.common.base.BaseActivity;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class CutPictureActivity extends BaseActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "CutPictureActivity";
    // Static final constants
    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

    // Instance variables
    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    Bitmap originalImage;
    Bitmap croppedImage;
    private CropImageView cropImageView;
    private ButtonFlat rotateButton;
    private ButtonFlat cropButton;

    // Saves the state upon rotating the screen/restarting the activity
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

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tch_activity_cutpicture);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setImageBitmap(originalImage);
        rotateButton = (ButtonFlat) findViewById(R.id.Button_rotate);
        rotateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
            }
        });


        cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);


        cropButton = (ButtonFlat) findViewById(R.id.Button_crop);
        cropButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                croppedImage = cropImageView.getCroppedImage();
                ImageView croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
                croppedImageView.setImageBitmap(croppedImage);
            }
        });
    }

    @Override
    protected void initData() {
        Uri uri = (Uri) getIntent().getParcelableExtra("PICTURE");
        try {
            originalImage = Media.getBitmap(getContentResolver(), uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
