package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Created by diany_000 on 2/18/2015.
 */
public class SplashWelcome extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        TextView textView = (TextView)findViewById(R.id.text_view_expenses_manager);
        Typeface fontType = Typeface.createFromAsset(getAssets(), "fonts/Grundschrift-Bold.otf");
        textView.setTypeface(fontType);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashWelcome.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
