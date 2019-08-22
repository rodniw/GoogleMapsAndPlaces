package dev.rodni.ru.googlemapsandplaces.di.main;

import android.app.Application;

import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @Provides
    static LinearLayoutManager provideLayoutManager(Application application) {
        return new LinearLayoutManager(application.getApplicationContext());
    }
}
