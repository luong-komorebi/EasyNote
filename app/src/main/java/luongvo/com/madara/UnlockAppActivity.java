package luongvo.com.madara;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import luongvo.com.madara.utils.Constants;

public class UnlockAppActivity extends AppCompatActivity {
    private PatternLockView plvLockApp;
    private String definedLockPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);
        
        definedLockPattern = getLockPattern();
        if (!definedLockPattern.equals("")) {
            configPlv();
        } else {
            onSuccessAuthenticate();
        }
    }

    private void onSuccessAuthenticate() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);

        // Those flags below make MainActivity CAN'T go backward to UnlockAppActivity when pressing Back button

        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainActivityIntent);
    }

    private String getLockPattern() {
        SharedPreferences sP = getSharedPreferences(Constants.sPFileName, MODE_PRIVATE);
        return sP.getString(Constants.sPLockPattern,"");
    }

    private void configPlv() {
        plvLockApp = findViewById(R.id.plvLockApp);
        plvLockApp.setTactileFeedbackEnabled(true);
        plvLockApp.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String inputLockPattern = PatternLockUtils.patternToSha1(plvLockApp, pattern);
                if (inputLockPattern.equals(definedLockPattern)) {
                    onSuccessAuthenticate();
                } else {
                    plvLockApp.setViewMode(PatternLockView.PatternViewMode.WRONG);
                }
            }

            @Override
            public void onCleared() {
                plvLockApp.setViewMode(PatternLockView.PatternViewMode.AUTO_DRAW);
            }
        });
    }
}
