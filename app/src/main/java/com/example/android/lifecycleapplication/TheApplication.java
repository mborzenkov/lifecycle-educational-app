package com.example.android.lifecycleapplication;

import android.app.Application;
import android.content.res.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Этот класс нужен для того, чтобы переопределить методы Application, связанные с жизненным циклом.
 * Цикл следующий:
 *      Запуск
 *      Application.onCreate()
 *      ... activity, viewgroup, view обычно дальше тут
 *      В любой момент могут возникать Application.onConfigurationChanged, onLowMemory, onTrimMemory
 *      и только при разработке onTerminate
 *      Стоп
 */
public class TheApplication extends Application {

    // Для демонстрации будем сохранять все вызовы не в общий лог, а в отдельные List, по одному
    // на каждый тип (APPLICATION, ACTIVITY, ...) из которого будем потом получать данные и выводить
    // на форме.
    private static Map<String, List<String>> sLifecycleCallbacks = new HashMap<>();
    private String applicationId;
    private String allTypesId;

    /*
     * Все начинается с Application.onCreate.
     */
    @Override
    public void onCreate() {
        applicationId = getString(R.string.log_application_id);
        allTypesId = getString(R.string.log_all_id);
        if (sLifecycleCallbacks.get(allTypesId) == null) {
            sLifecycleCallbacks.put(allTypesId, new ArrayList<String>());
        }
        addLifecycleCallback(applicationId, "onCreate");
        super.onCreate();
    }

    /*
     * Эта функция вызывается, когда изменилась конфигурация устройства во время работы приложения
     * Обычно используется для освобождения и повторного получения ресурсов.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        addLifecycleCallback(applicationId, "onConfigurationChanged, new config: " + newConfig.toString());
        super.onConfigurationChanged(newConfig);
    }

    /*
     * Эта функция не вызывается на устройстве, потому что процессы просто убиваются без
     * предупреждения :(
     * Нужна для отладки.
     */
    @Override
    public void onTerminate() {
        // Так как функция отладочная, мне тут достаточно записать в лог, что приложение убили.
        addLifecycleCallback(applicationId, "onTerminate"); // В этом вызове нет смысла
        super.onTerminate();
    }

    /*
     * Эта функция вызывается, когда осталось мало памяти. Причем обычно для активно работающих
     * процессов. Для фоновых скорее вызовется onTrimMemory() (потому что у них приоритет ниже).
     * Это старая версия и с API 14 стоит использовать onTrimMemory(int) из ComponentCallbacks2,
     * потому что там сообщают, на сколько действительно мало памяти.
     * Обе функции - это как выстрел в воздух, последний шанс бросить все лишнее, иначе следующий
     * может быть на поражение.
     */
    @Override
    public void onLowMemory() {
        // Если мало памяти
        addLifecycleCallback(applicationId, "onLowMemory");
        trimCallbackLists();
        super.onLowMemory();
    }

    /*
     * Эта функция вызывается, когда остается мало памяти и система собирается начать чистку.
     * В функцию передается значение, по которому можно определить, на сколько все серьезно.
     */
    @Override
    public void onTrimMemory(int level) {

        addLifecycleCallback(applicationId, "onTrimMemory with level " + level);
        // Освобождаем память только если CRITICAL или ниже
        if (level <= TRIM_MEMORY_RUNNING_CRITICAL) {
            trimCallbackLists();
        }
        super.onTrimMemory(level);
    }

    /**
     * Возвращает все записанные вызовы
     * @param componentType Тип отправителя, из strings (например, APPLICATION)
     * @return Список, в котором содержатся записанные вызовы.
     * Данные в списке хранятся в порядке от самых новых к самым старым.
     * Список содержит не менее 10 последних, остальные значения могут быть удалены, в случае
     * нехватки памяти.
     * Каждая строка состоит из: "ДатаВремя | ТипОтправителя: Вызов"
     */
    public List<String> getLifecycleCallbacks(String componentType) {
        List<String> callbacksList = sLifecycleCallbacks.get(componentType);
        if (callbacksList == null) {
            callbacksList = new ArrayList<String>();
        }
        return Collections.unmodifiableList(callbacksList);
    }
    /**
     * Записывает вызов в список вызовов.
     * @param componentType Тип отправителя, из strings (например, APPLICATION)
     * @param callback Строка, идентифицирующая вызов
     */
    public void addLifecycleCallback(String componentType, String callback) {
        String dateFormatted = (new SimpleDateFormat("dd.MM.yy HH:mm:ss:SSS")).format(new Date(System.currentTimeMillis()));
        String result = dateFormatted + " | " + componentType + ": " + callback;
        List<String> callbacksList = sLifecycleCallbacks.get(componentType);
        if (callbacksList != null) {
            callbacksList.add(0, result);
        } else {
            callbacksList = new ArrayList<>();
            callbacksList.add(result);
            sLifecycleCallbacks.put(componentType, callbacksList);
        }
        sLifecycleCallbacks.get(allTypesId).add(0, result);
    }

    /**
     * Очищает список вызовов от всех, кроме последних записей.
     */
    private void trimCallbackLists() {
        for (String componentType : sLifecycleCallbacks.keySet()) {
            List<String> callbacksList = sLifecycleCallbacks.get(componentType);
            if (callbacksList.size() >= 10) {
                callbacksList = callbacksList.subList(0, 10);
                callbacksList.add(getString(R.string.log_trim_message));
            }
        }
    }

}
