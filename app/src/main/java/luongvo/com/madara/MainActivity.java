package luongvo.com.madara;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import luongvo.com.madara.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatabase();
        toNotebooks();
    }

    private void initDatabase() {
        DBHelper.open(MainActivity.this);
    }

    private void toNotebooks() {
        Intent intent = new Intent(MainActivity.this, NotebooksActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper.close();
    }
}
