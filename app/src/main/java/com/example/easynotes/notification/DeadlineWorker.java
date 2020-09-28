package com.example.easynotes.notification;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DeadlineWorker extends Worker {
    public static final String NOTE_ID_KEY = "noteId";
    public static final String CONTENT_TITLE_KEY = "contentTitle";
    public static final String CONTENT_TEXT_KEY = "contentText";
    public static final String TAG = "DeadlineWorker";

    public DeadlineWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        long noteId = getInputData().getLong(NOTE_ID_KEY, 0);
        String contentTitle = getInputData().getString(CONTENT_TITLE_KEY);
        String contentText = getInputData().getString(CONTENT_TEXT_KEY);

        NotificationHelper.showReminderNotification(getApplicationContext(), noteId, contentTitle, contentText);
        Log.d(TAG, "end doWork" + contentText + contentTitle);
        return Result.success();
    }
}
