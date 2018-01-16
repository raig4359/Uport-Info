package com.raig.uportinfo.user_form;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raig.uportinfo.R;
import com.raig.uportinfo.data.UserProfileModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailFragment extends Fragment {

    View view;
    UserProfileModel profileModel;

    private OnFormInteractionListener mListener;
    private final static String USER_PROFILE_PARAM = "userProfileModel";

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(UserProfileModel model) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_PROFILE_PARAM, model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileModel = getArguments().getParcelable(USER_PROFILE_PARAM);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("User Information");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.bt_next)
    public void showVehicleDetailScreen() {
        mListener.showVehicleInfoForm();
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
