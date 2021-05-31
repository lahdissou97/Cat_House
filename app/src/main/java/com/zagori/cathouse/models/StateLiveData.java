package com.zagori.cathouse.models;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

/*
* A custom mutableLiveData to pass back latest data to the UI with its fetch status
* */
public class StateLiveData<T> extends MutableLiveData<Response> {
    /**
     * Use this to put the Data on a LOADING Status
     */
    public void postLoading() {
        postValue(new Response().loading());
    }

    /**
     * Use this to put the Data on a ERROR Status
     * @param throwable the error to be handled
     */
    public void postError(Throwable throwable) {
        postValue(new Response().error(throwable));
    }

    /**
     * Use this to put the Data on a SUCCESS Status
     * @param data the load returned upon successful network call
     */
    @SuppressWarnings("unchecked")
    public void postSuccess(List<T> data) {
        postValue(new Response().success(data));
    }

}
