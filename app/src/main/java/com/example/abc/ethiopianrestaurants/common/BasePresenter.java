package com.example.abc.ethiopianrestaurants.common;

public class BasePresenter<T extends BaseView> {

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

    protected void showLoading(boolean loading) {
        executeViewOperation(() -> view.showLoading(loading));
    }

    protected void showError() {
        executeViewOperation(() -> {
            view.showError();
            view.showLoading(false);
        });
    }
}
