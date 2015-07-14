package io.relayr.model.onboarding;

import java.io.Serializable;

public class OnBoardingState implements Serializable {

    public boolean isConnected;

    public OnBoardingState(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
