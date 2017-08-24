package com.yjh.espresso;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class RightFragment extends BaseFragment implements View.OnClickListener {

    public static RightFragment newInstance() {
        return new RightFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        view.findViewById(R.id.text).setOnClickListener(this);
        view.findViewById(R.id.button).setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                Toast.makeText(getActivity(), R.string.text_clicked, Toast.LENGTH_LONG).show();
                break;
            case R.id.button:
                Toast.makeText(getActivity(), R.string.button_clicked, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
