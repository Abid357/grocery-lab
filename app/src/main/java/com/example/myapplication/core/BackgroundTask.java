package com.example.myapplication.core;

import android.app.Activity;

// copied from: https://stackoverflow.com/a/65108879
public abstract class BackgroundTask {
    private Activity activity;

    public BackgroundTask(Activity activity) {
        this.activity = activity;
    }

    private void startBackground() {
        new Thread(() -> {
            doInBackground();
            activity.runOnUiThread(this::onPostExecute);
        }).start();
    }
    public void execute(){
        startBackground();
    }

    public Activity getActivity(){
        return activity;
    }

    public abstract void doInBackground();
    public abstract void onPostExecute();
}