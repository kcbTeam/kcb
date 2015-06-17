package com.kcb.teacher.activity.test;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.edmodo.cropper.CropImageView;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.StatusBarUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcbTeam.R;

public class CropPictureActivity extends BaseActivity {

    private static final int DEFAULT_ASPECT_RATIO_VALUES = 10;
    private static final int ROTATE_NINETY_DEGREES = 90;
    private static final String ASPECT_RATIO_X = "ASPECT_RATIO_X";
    private static final String ASPECT_RATIO_Y = "ASPECT_RATIO_Y";

    private int mAspectRatioX = DEFAULT_ASPECT_RATIO_VALUES;
    private int mAspectRatioY = DEFAULT_ASPECT_RATIO_VALUES;

    private Bitmap mBitmap;
    private Bitmap mCropBitmap;
    private CropImageView cropImageView;

    private ButtonFlat backButton;
    private ButtonFlat rotateButton;
    private ButtonFlat finishButton;

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
        StatusBarUtil.setTchStatusBarColor(this);

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
        cropImageView.setImageBitmap(mBitmap);

        finishButton = (ButtonFlat) findViewById(R.id.button_finish);
        finishButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String path = getIntent().getStringExtra(DATA_PATH);
        mBitmap = BitmapFactory.decodeFile(path);
        new File(path).delete();
        mBitmap = ResizeBitmap(mBitmap, 1000);
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
            case R.id.button_finish:
                mCropBitmap = cropImageView.getCroppedImage();
                Uri uri =
                        Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                mCropBitmap, null, null));

                Intent intent = new Intent();
                intent.putExtra(DATA_PATH, uri);
                setResult(RESULT_OK, intent);
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

    /**
     * start
     */
    public static final String DATA_PATH = "data_path";

    public static final int REQUEST_CROPPHOTO = 1;

    public static void startForResult(Context context, String uri, int requestCode) {
        Intent intent = new Intent(context, CropPictureActivity.class);
        intent.putExtra(DATA_PATH, uri);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
