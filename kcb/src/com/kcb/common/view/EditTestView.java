package com.kcb.common.view;

import com.kcb.library.view.checkbox.CheckBox;
import com.kcbTeam.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditTestView extends LinearLayout {

    public EditTestView(Context context) {
        super(context);
        init(context);
    }

    public EditTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditTestView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private TextView inputIndexTextView;

    private EditText titleEditText;
    private ImageView deleteTitleImageView;
    private EditText aEditText;
    private ImageView deleteAImageView;
    private EditText bEditText;
    private ImageView deleteBImageView;
    private EditText cEditText;
    private ImageView deleteCImageView;
    private EditText dEditText;
    private ImageView deleteDImageView;

    private CheckBox checkBoxA;
    private CheckBox checkBoxB;
    private CheckBox checkBoxC;
    private CheckBox checkBoxD;

    private Context mContext;

    private void init(Context context) {
        mContext = context;
        inflate(context, R.layout.comm_view_edit_test, this);
    }
}
