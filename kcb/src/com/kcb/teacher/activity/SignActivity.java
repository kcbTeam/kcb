/**
 * Copyright© 2011-2014 DewMobile USA Inc.All Rights Reserved.
 * @Title: 	SignActivity.java 
 * @Package com.kcb.teacher.activity 
 * @Description: 
 * @author:	Ding 
 * @date:	2015年4月23日 上午11:03:21 
 * @version	V1.0   
 */

package com.kcb.teacher.activity;

import com.kcbTeam.R;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.View.OnClickListener;

/**
 * @className: SignActivity
 * @description: Sign in and Test
 * @author: Ding
 * @date: 2015年4月23日 上午11:03:21
 */
public class SignActivity extends FragmentActivity{
    
    private Fragment[] mFragments;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button buttonexit;
    private RadioGroup radioGroup;
    private RadioButton buttonSign;
    private RadioButton buttonTest;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_begin);
        mFragments=new Fragment[2];
        fragmentManager=getSupportFragmentManager();
        mFragments[0]=fragmentManager.findFragmentById(R.id.sign);
        mFragments[1]=fragmentManager.findFragmentById(R.id.test);
        fragmentTransaction=fragmentManager.beginTransaction().
                hide(mFragments[0]).hide(mFragments[1]);
        fragmentTransaction.show(mFragments[0]).commit();
        setFragmentIndicator();
    }
    
    private void setFragmentIndicator(){
        buttonexit=(Button) findViewById(R.id.exitbtn);
        radioGroup=(RadioGroup) findViewById(R.id.bottomRg);
        buttonSign=(RadioButton) findViewById(R.id.signbtn);
        buttonTest=(RadioButton) findViewById(R.id.testbtn);
        
        buttonexit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                System.exit(0);
            }
        });
        
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId){
                fragmentTransaction=fragmentManager.beginTransaction().
                        hide(mFragments[0]).hide(mFragments[1]);
                switch(checkedId){
                    case R.id.signbtn:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;
                    case R.id.testbtn:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
