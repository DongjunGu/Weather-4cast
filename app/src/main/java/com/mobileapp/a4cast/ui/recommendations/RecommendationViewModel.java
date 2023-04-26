/*
 * Weather4cast
 * Robert Russell | Dongjun Gu
 * April/2023
 */
package com.mobileapp.a4cast.ui.recommendations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecommendationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RecommendationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Recommendation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}