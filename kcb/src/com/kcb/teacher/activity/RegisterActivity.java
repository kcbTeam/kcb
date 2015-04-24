package com.kcb.teacher.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.kcbTeam.R;

/**
 * 
 * @className: RegisterActivity
 * @description:
 * @author: LJX
 * @date:
 */
public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO
        // define in manifest.xml, use android:theme
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.teacher_activity_register);
    }


}
