package com.kcb.teacher.activity.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
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
import com.kcb.common.listener.DelayClickListener;
import com.kcb.library.view.PaperButton;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class CropPictureActivity extends BaseActivity {

    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    private Bitmap mBitMap;
    private Bitmap croppedImage;
    private CropImageView cropImageView;

    private ButtonFlat backButton;
    private ButtonFlat rotateButton;
    private PaperButton completeButton;

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
        setContentView(R.layout.tch_activity_crop_picture);

        initData();
        initView();
    }

    @Override
    protected void initView() {
        backButton = (ButtonFlat) findViewById(R.id.button_back);
        backButton.setOnClickListener(this);

        rotateButton = (ButtonFlat) findViewById(R.id.button_rotate);
        rotateButton.setOnClickListener(this);

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setImageBitmap(mBitMap);

        completeButton = (PaperButton) findViewById(R.id.button_complete);
        completeButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {
        Uri uri = (Uri) getIntent().getParcelableExtra(DATA_PICTURE);
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
            case R.id.button_back:
                finish();
                break;
            case R.id.button_rotate:
                cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
                break;
            default:
                break;
        }
    }

    private DelayClickListener mClickListener = new DelayClickListener(
            DelayClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            switch (v.getId()) {
                case R.id.button_back:
                    finish();
                    break;
                case R.id.button_rotate:
                    cropImageView.rotateImage(ROTATE_NINETY_DEGREES);
                    break;
                case R.id.button_complete:
                    croppedImage = cropImageView.getCroppedImage();
                    Intent intent = new Intent();
                    intent.putExtra(DATA_PICTURE, Uri.parse(MediaStore.Images.Media.insertImage(
                            getContentResolver(), croppedImage, null, null)));
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

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

    /**
     * start
     */
    public static final String DATA_PICTURE = "data_picture";

    public static final int REQUEST_CUTPHOTO = 1;

    public static void startForResult(Context context, Uri uri, int requestCode) {
        Intent intent = new Intent(context, CropPictureActivity.class);
        intent.putExtra(DATA_PICTURE, uri);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
