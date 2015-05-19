package com.kcb.student.activity;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.R.integer;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcb.common.base.BaseActivity;
import com.kcb.common.util.LogUtil;
import com.kcb.library.view.buttonflat.ButtonFlat;
import com.kcb.library.view.checkbox.CheckBox;
import com.kcb.student.util.RedHookDraw;
import com.kcbTeam.R;

public class LookTestResultActivity extends BaseActivity {

    private static final String TAG = "hfdjshng";
    private TextView titleTextView;
    private TextView choiceNumTextView;
    private TextView questionTextView;
    private TextView answerATextView;
    private TextView answerBTextView;
    private TextView answerCTextView;
    private TextView answerDTextView;
    private CheckBox checkboxA;
    private CheckBox checkboxB;
    private CheckBox checkboxC;
    private CheckBox checkboxD;
    private int numQuestion = 3;
    RedHookDraw layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_looktestresult);

        initView();
    }

    @Override
    protected void initView() {
        titleTextView = (TextView) findViewById(R.id.testresult_title);
        choiceNumTextView = (TextView) findViewById(R.id.textview_testresultchioce);
        questionTextView = (TextView) findViewById(R.id.textview_choice_question1);
        answerATextView = (TextView) findViewById(R.id.textview_choice_a1);
        answerBTextView = (TextView) findViewById(R.id.textview_choice_b1);
        answerCTextView = (TextView) findViewById(R.id.textview_choice_c1);
        answerDTextView = (TextView) findViewById(R.id.textview_choice_d1);
        checkboxA = (CheckBox) findViewById(R.id.checkbox_a1);
        checkboxB = (CheckBox) findViewById(R.id.checkbox_b1);
        checkboxC = (CheckBox) findViewById(R.id.checkbox_c1);
        checkboxD = (CheckBox) findViewById(R.id.checkbox_d1);
        XmlPullParser parser = getResources().getXml(R.layout.stu_item_flatbutton);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.buttonflats1);
        ButtonFlat buFlat1 = new ButtonFlat(this, Xml.asAttributeSet(parser));
        ButtonFlat buFlat2 = new ButtonFlat(this, Xml.asAttributeSet(parser));
        ButtonFlat buFlat3 = new ButtonFlat(this, Xml.asAttributeSet(parser));
        ButtonFlat buFlat4 = new ButtonFlat(this, Xml.asAttributeSet(parser));
        linearLayout.addView(buFlat1);
        linearLayout.addView(buFlat2);
        linearLayout.addView(buFlat3);
        linearLayout.addView(buFlat4);
//        layout = (RedHookDraw) findViewById(R.id.relativelayout_redhook);
//        layout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {}

}
