/**
 * Copyright© 2011-2014 DewMobile USA Inc.All Rights Reserved.
 * @Title: 	SignFragment.java 
 * @Package com.kcb.common.base 
 * @Description: 
 * @author:	Ding 
 * @date:	2015年4月23日 上午10:17:01 
 * @version	V1.0   
 */

package com.kcb.common.base;

import com.kcbTeam.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @className: SignFragment
 * @description: Sign in fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:01
 */
public class SignFragment extends Fragment implements OnClickListener{
    
    private Button buttonstart;
    private Button buttoncheck;
    
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstaceState){
        return inflater.inflate(R.layout.fragment_sign, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        buttonstart=(Button) getView().findViewById(R.id.startSign);
        buttoncheck=(Button) getView().findViewById(R.id.checkSign);
        
        buttonstart.setOnClickListener(this);
        buttoncheck.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.startSign:
                //TODO
                break;
            case R.id.checkSign:
                //TODO
                break;
            default:
                break;
        }
    }
}
