package luongvo.com.madara;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import luongvo.com.madara.utils.Constants;

public class AddNewPatternActivity extends AppCompatActivity {
    private PatternLockView plvLockApp;
    private TextView txtPattern;
    private Toast confirmFailed;
    private String inputLockPattern = "";
    private String confirmLockPattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_lock);

        configPlv();
        configTxt();
    }

    private void setLockPatternAndExit(String lockPattern) {
        Toast.makeText(this, getString(R.string.toast_success), Toast.LENGTH_SHORT).show();
        SharedPreferences sP = getSharedPreferences(Constants.sPFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putString(Constants.sPLockPattern, lockPattern);
        editor.apply();
        finish();
    }

    private void configPlv() {
        plvLockApp = (PatternLockView) findViewById(R.id.plvLockApp);
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
                String lockPattern = PatternLockUtils.patternToSha1(plvLockApp, pattern);
                if (inputLockPattern.equals("")) {
                    inputLockPattern = lockPattern;
                    plvLockApp.clearPattern();
                    txtPattern.setText(getString(R.string.text_view_confirm_pattern));
                } else {
                    confirmLockPattern = lockPattern;
                    if (!inputLockPattern.equals(confirmLockPattern)) {

                        // inputLockPattern != confirmLockPattern => confirmation failed
                        
                        confirmLockPattern = "";
                        confirmFailed.show();
                    } else {
                        setLockPatternAndExit(confirmLockPattern);
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void configTxt() {
        txtPattern = (TextView) findViewById(R.id.txtPattern);
        txtPattern.setText(getString(R.string.text_view_choose_pattern));
        confirmFailed = Toast.makeText(AddNewPatternActivity.this,getString(R.string.toast_confirm_new_pattern_failed), Toast.LENGTH_SHORT);
        confirmFailed.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        // Avoid cancellation
    }
}