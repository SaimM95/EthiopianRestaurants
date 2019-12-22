package com.example.abc.ethiopianrestaurants.common;

import timber.log.Timber;

import java.util.LinkedList;
import java.util.Queue;

public class BasePresenter<T extends BaseView> {

    protected T view;
    private Queue<ViewOperation> queuedOperations = new LinkedList<>();

    public void bindView(T view) {
        this.view = view;
    }

    public void unbindView() {
        view = null;
    }

    protected void executeViewOperation(ViewOperation viewOperation) {
        if (view != null) {
            viewOperation.execute();
        } else {
            queuedOperations.add(viewOperation);
        }
    }

    public void updateState() {
        while (!queuedOperations.isEmpty()) {
            if (view == null) {
                return;
            }
            queuedOperations.remove().execute();
            Timber.d("Operation executed");
        }
    }

    protected void showLoading(boolean loading) {
        executeViewOperation(() -> view.showLoading(loading));
    }

    protected void showError() {
        executeViewOperation(() -> view.showError());
    }

    @FunctionalInterface
    protected interface ViewOperation {
        void execute();
    }
}
