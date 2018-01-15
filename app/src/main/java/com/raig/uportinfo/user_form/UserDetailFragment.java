package com.raig.uportinfo.user_form;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raig.uportinfo.R;

public class UserDetailFragment extends Fragment {

    private OnFormInteractionListener mListener;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance() {
        UserDetailFragment fragment = new UserDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFormInteractionListener) {
            mListener = (OnFormInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFormInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}