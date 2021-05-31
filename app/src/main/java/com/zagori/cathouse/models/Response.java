package com.zagori.cathouse.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Response<T> {

    @NonNull
    private Status status;

    @Nullable
    private List<T> data;

    @Nullable
    private Throwable error;

    public Response() {
    }


    public Response loading() {
        this.status = Status.LOADING;
        this.data = null;
        this.error = null;
        return this;
    }

    public Response success(@NonNull List<T> data) {
        this.status = Status.SUCCESS;
        this.data = data;
        this.error = null;
        return this;
    }

    public Response error(@NonNull Throwable error) {
        this.status = Status.ERROR;
        this.data = null;
        this.error = error;
        return this;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public List<T> getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}
