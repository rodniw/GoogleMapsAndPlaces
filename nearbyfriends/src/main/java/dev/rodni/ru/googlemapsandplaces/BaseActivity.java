package dev.rodni.ru.googlemapsandplaces;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import dev.rodni.ru.googlemapsandplaces.ui.auth.AuthActivity;

//now i can simply extend this activity and i will not care about auth state anymore
public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutRes());

        subscribeObservers();
    }

    @LayoutRes
    protected abstract int layoutRes();

    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case ERROR:

                        break;
                    case AUTHENTICATED:
                        Log.i("TAG", userAuthResource.data.getEmail());
                        break;
                    case NOT_AUTHENTICATED:
                        navigateLoginScreen();
                        break;
                    case LOADING:

                        break;
                }
            }
        });
    }

    private void navigateLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
