package com.kcb.student.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kcb.common.activity.StartActivity;
import com.kcb.common.application.KAccount;
import com.kcb.common.base.BaseActivity;
import com.kcb.common.listener.CustomOnClickListener;
import com.kcb.common.server.RequestUtil;
import com.kcb.common.server.UrlUtil;
import com.kcb.common.util.AnimationUtil;
import com.kcb.library.view.FloatingEditText;
import com.kcb.library.view.PaperButton;
import com.kcbTeam.R;

/**
 * 
 * @className: LoginActivity
 * @description: Login interface,one Textview,one ImageView,two editView,one Button
 * @author: Tao Li
 * @date: 2015-4-24 下午9:17:01
 */
public class LoginActivity extends BaseActivity {

    private FloatingEditText idEditText;
    private FloatingEditText passwordEditText;
    private PaperButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_activity_login);

        initView();
    }

    @Override
    protected void initView() {
        idEditText = (FloatingEditText) findViewById(R.id.edittext_id);
        passwordEditText = (FloatingEditText) findViewById(R.id.edittext_password);
        loginButton = (PaperButton) findViewById(R.id.button_login);
        loginButton.setOnClickListener(mClickListener);
    }

    @Override
    protected void initData() {}

    private CustomOnClickListener mClickListener = new CustomOnClickListener(
            CustomOnClickListener.DELAY_PAPER_BUTTON) {

        @Override
        public void doClick(View v) {
            final String id = idEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString();
            if (TextUtils.isEmpty(id)) {
                idEditText.requestFocus();
                AnimationUtil.shake(idEditText);
            } else if (TextUtils.isEmpty(password)) {
                passwordEditText.requestFocus();
                AnimationUtil.shake(passwordEditText);
            } else {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

                // TODO request server
                JsonObjectRequest request =
                        new JsonObjectRequest(Method.POST, UrlUtil.getStuLoginUrl(id, password),
                                "", new Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        KAccount account =
                                                new KAccount(KAccount.TYPE_STU, id, password);
                                        account.saveAccount();
                                        Intent intent =
                                                new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                }, new ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {}
                                });
                RequestUtil.getInstance().addToRequestQueue(request);
            }
        }
    };

    @Override
    public void onBackPressed() {
        finish();
        StartActivity.restart(this);
    }
}
