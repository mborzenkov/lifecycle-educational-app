package com.example.android.lifecycleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

/**
 * Эта Activity используется для вывода информации о вызовах из жизненного цикла на экран.
 * Она так же записывает свои вызовы.
 * Подробно, что делает каждая функция и зачем она нужна описано в MainActivity
 */
public class LogActivity extends AppCompatActivity {

    // Ссылки на TextView для вывода информации и на контекст приложения, где список вызовов
    private TextView mLogTextView;
    private TheApplication applicationContext;
    private String activityId;
    private String filterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // нужно запомнить контекст приложения один раз
        activityId = getString(R.string.log_activity_id);
        applicationContext = (TheApplication) getApplicationContext();
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        // нужно запомнить textView один раз
        mLogTextView = (TextView) findViewById(R.id.tv_log);

        Intent intentThatStartedActivity = getIntent();
        filterActivity = intentThatStartedActivity.hasExtra(Intent.EXTRA_TEXT) ?
                intentThatStartedActivity.getStringExtra(Intent.EXTRA_TEXT) :
                getString(R.string.log_all_id);
        applicationContext.addLifecycleCallback(activityId, "LogActivity - Примем данных от MainActivity");
    }

    @Override
    protected void onStart() {
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        applicationContext.addLifecycleCallback(activityId, "Log activity.onResume");
        super.onResume();

        // Тут стираем все что было ранее и записываем новые данные
        mLogTextView.setText("");


        List<String> callbacks = applicationContext.getLifecycleCallbacks(filterActivity);
        for (String callback : callbacks) {
            mLogTextView.append(callback + "\n");
        }
    }

    @Override
    protected void onPause() {
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        applicationContext.addLifecycleCallback(activityId, "LogActivity.onDestroy");
        super.onDestroy();
    }

}
