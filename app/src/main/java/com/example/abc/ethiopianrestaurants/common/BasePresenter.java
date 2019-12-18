package com.example.abc.ethiopianrestaurants.common;

public class BasePresenter<T> {

    protected T view;

    public void bindView(T view) {
        this.view = view;
    }

    public void unbindView() {
        view = null;
    }

    protected void executeViewOperation(ViewOperation operation) {
        if (view != null) {
            operation.execute();
        }
    }
}
