/**
 * Copyright© 2011-2014 DewMobile USA Inc.All Rights Reserved.
 * 
 * @Title: TestFragment.java
 * @Package com.kcb.common.base
 * @Description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 * @version V1.0
 */

package com.kcb.student.fragment;

import com.kcbTeam.R;

import android.view.View.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * @className: TestFragment
 * @description: Test fragment
 * @author: Ding
 * @date: 2015年4月23日 上午10:17:44
 */
public class TestFragment extends Fragment implements OnClickListener {

    private Button buttonstart;
    private Button buttoncheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonstart = (Button) getView().findViewById(R.id.startTest);
        buttoncheck = (Button) getView().findViewById(R.id.checkTest);

        buttonstart.setOnClickListener(this);
        buttoncheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startTest:
                // TODO
                break;
            case R.id.checkTest:
                // TODO
                break;
            default:
                break;
        }
    }
}
