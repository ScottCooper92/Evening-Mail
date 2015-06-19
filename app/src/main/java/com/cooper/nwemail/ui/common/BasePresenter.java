package com.cooper.nwemail.ui.common;

public abstract class BasePresenter<T extends BaseView> {

    protected T view;

    public BasePresenter(final T view) {
        this.view = view;
    }

    public abstract void initialize();

    public abstract void resume();

    public abstract void pause();

    public abstract void destroy();

}
