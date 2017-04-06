package com.example.android.lifecycleapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import com.example.android.lifecycleapplication.NewButton;

/**
 * Эта Activity является основной. На ней выводится общая информация о приложении и кнопка.
 * MainActivity регистрирует все вызовы своего жизненного цикла в общем списке (расположенном
 * в контексте приложения).
 * Также она обрабатывает нажатие на кнопку и открывает LogActivity, в которой выводится список
 * всех вызовов жизненного цикла всего приложения.
 *
 * Жизненный цикл Activity:
 *      Запуск
 *      Activity.onCreate() - создание
 *      Activity.onStart() - запуск
 *      Activity.onResume() - вывод
 *      ... какие-то действия пользователя
 *      Activity.onPause() - пауза
 *      Activity.onStop() - остановка
 *          Activity.onRestart() - если нужен снова start после stop
 *      Activity.onDestroy() - уничтожение, то есть закрытие
 *      Стоп
 *
 *      Порядок может быть нарушен функцией finish(), которая сразу вызовет onDestroy().
 *
 *      Еще есть функции onPostCreate() и onPostResume(), они следуют сразу за соответствующими
 *      вызовами. Обычно не переопределяются.
 *      И функции onRestoreInstanceState и onSaveInstanceState, вызываются после onStart и перед
 *      onStop соответственно. Нужны для восстановления состояния Activity.
 *      Не обязательно пользоваться RestoreInstanceState, т.к. данные можно получить в onStart, но
 *      иногда может понадобиться обработать данные после завершения onStart.
 */
public class MainActivity extends AppCompatActivity
        implements MainAdapter.ListItemClickListner {

    private TheApplication applicationContext;
    private RecyclerView mRecyclerView;
    public MainAdapter mMainAdapter;
    private String activityId;
    private String viewgroupId;

    /*
     * Вызывается при запуске Activity. Обычно используется для инициализации переменных интерфейса
     * (findViewById), для привязки layout (тут setContentView(R.layout.activity_main) ) и для всего
     * остального, что может понадобиться до вывода на экран.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // вот тут привязывается layout
        setContentView(R.layout.activity_main);

        // Инициализируем переменную контекста приложения и записываем в список вызов
        activityId = getString(R.string.log_activity_id);
        viewgroupId = getString(R.string.log_viewgroup_id);
        applicationContext = (TheApplication) getApplicationContext();
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onCreate");

        // Инициализируем RecyclerView и LayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        applicationContext.addLifecycleCallback(viewgroupId, "Привязка LayoutManager к RecyclerView");
        mRecyclerView.setHasFixedSize(true);

        // Создаем адаптер и привызываем его к RecycleView
        mMainAdapter = new MainAdapter(this, this);

        mRecyclerView.setAdapter(mMainAdapter);
        applicationContext.addLifecycleCallback(viewgroupId, "Привязка MainAdapter к RecyclerView");

        // SnapHelper нужен, чтобы при перемещении по RecyclerView, все красиво притягивалось
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        applicationContext.addLifecycleCallback(viewgroupId, "Установка SnapHelper для RecyclerView");
    }

    /*
     * Вызывается после onCreate или onRestart.
     */
    @Override
    protected void onStart() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onStart");
        super.onStart();
    }

    /*
     * Вызывается чаще всех и в последнюю очередь (после Start, Restart, Pause...)
     * Обычно тут запускаются анимации, выполняется обращение к важным компонентам (например, камере).
     */
    @Override
    protected void onResume() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onResume");
        super.onResume();
    }

    /*
     * Вызывается, когда Activity стала фоновой, но еще не убита. Причем сналача вызывается onPause
     * для Activity, которая скрывается и потом только onCreate для новой Activity.
     * В onPause обычно сохраняют разные данные, отключают анимацию и прочие затратные элменты UI,
     * закрывают приоритетные доступы к компонентам (например, к Камере).
     *
     * Activity может быть убита после onPause, поэтому желательно все сохранить. Также для этого
     * используется onSaveInstanceState().
     */
    @Override
    protected void onPause() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onPause");
        super.onPause();
    }

    /*
     * Вызывается, когда Activity более не видно. Далее может быть вызов onRestart или onDestroy.
     */
    @Override
    protected void onStop() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onStop");
        super.onStop();
    }

    /*
     * Вызывается после onStop, если пользователь возвращается к этой Activity вновь. Далее идет
     * цепочка onStart -> onResume.
     * Можно вернуть объекты, которые были очищены в onStop
     */
    @Override
    protected void onRestart() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onRestart");
        super.onRestart();
    }

    /*
     * Заключительная функция. В ней нужно соответственно провести заключительную чистку объектов,
     * которыми пользовалась Activity и которые более не нужны.
     */
    @Override
    protected void onDestroy() {
        applicationContext.addLifecycleCallback(activityId, "MainActivity.onDestroy");
        super.onDestroy();
    }

    /**
     * Открывает Activity со списком вызовов (по нажатию на кнопку)
     * @param componentType Тип странички, на которой была нажата кнопка
     */
    @Override
    public void onListItemClick(String componentType) {
        applicationContext.addLifecycleCallback(activityId, "Передача данных из MainActivity и запуск LogActivity");
        Intent startingLogActivity = new Intent(MainActivity.this, LogActivity.class);
        startingLogActivity.putExtra(Intent.EXTRA_TEXT, componentType);
        startActivity(startingLogActivity);
    }

}
