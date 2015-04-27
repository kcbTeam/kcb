package com.kcb.student.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kcb.common.base.BaseFragmentActivity;
import com.kcbTeam.R;

/**
 * @className: HomePageActivity
 * @description: Sign in and Test
 * @author: Ding
 * @date: 2015年4月23日 上午11:03:21
 */
public class HomeActivity extends BaseFragmentActivity {

    //TODO change define sort
    private final int INDEX_CHECKIN = 0;
    private final int INDEX_TEST = 1;

    private Fragment[] mFragments;
    private FragmentManager fragmentManager;
    
    //TODO change to temp
    private FragmentTransaction fragmentTransaction;
    //TODO rename to exitButton
    private Button buttonexit;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO delete it
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stu_activity_home);

        initView();
    }

    @Override
    protected void initView() {
        //TODO rename exitbtn to button_exit
        //rename bottomRg to radiogroup
        buttonexit = (Button) findViewById(R.id.exitbtn);
        radioGroup = (RadioGroup) findViewById(R.id.bottomRg);

        mFragments = new Fragment[2];
        fragmentManager = getSupportFragmentManager();
        
        //TODO rename sign to fragment_sign
        //rename test to fragment_test
        mFragments[INDEX_CHECKIN] = fragmentManager.findFragmentById(R.id.sign);
        mFragments[INDEX_TEST] = fragmentManager.findFragmentById(R.id.test);
        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[INDEX_TEST]);

        buttonexit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 
                //show dialog
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction =
                        fragmentManager.beginTransaction().hide(mFragments[INDEX_CHECKIN])
                                .hide(mFragments[INDEX_TEST]);
                switch (checkedId) {
                    case R.id.signbtn:
                        fragmentTransaction.show(mFragments[INDEX_CHECKIN]).commit();
                        break;
                    case R.id.testbtn:
                        fragmentTransaction.show(mFragments[INDEX_TEST]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {}
}
