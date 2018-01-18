package com.raig.uportinfo.data;

/**
 * Created by Gaurav on 16-01-2018.
 */

public class UserProfileEvent {

    UserProfileModel profileModel;

    public UserProfileEvent(UserProfileModel profileModel) {
        this.profileModel = profileModel;
    }

    public UserProfileModel getProfileModel() {
        return profileModel;
    }
}
