package com.yjh.espresso;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RightFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTextView;
    private EditText mEditText;
    private Button mButton1;
    private Button mButton2;

    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        mTextView = view.findViewById(R.id.text_view);
        mEditText = view.findViewById(R.id.edit_text);
        mButton1 = view.findViewById(R.id.button1);
        mButton2 = view.findViewById(R.id.button2);
        mTextView.setOnClickListener(this);
        mEditText.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view:
                mTextView.setText(R.string.text_clicked);
                break;
            case R.id.edit_text:
                mEditText.setText(R.string.edit_text_clicked);
                break;
            case R.id.button1:
                mButton1.setText(R.string.button_clicked);
                break;
            case R.id.button2:
                mButton2.setText(R.string.button_clicked);
                break;
        }
    }
}
