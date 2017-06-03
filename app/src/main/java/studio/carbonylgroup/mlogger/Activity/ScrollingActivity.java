package studio.carbonylgroup.mlogger.Activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import studio.carbonylgroup.mlogger.R;
import studio.carbonylgroup.mlogger.Utilities.Utils;

public class ScrollingActivity extends AppCompatActivity {

    private Utils utils;

    private TextInputEditText url_input;
    private TextInputEditText username_input;
    private TextInputEditText password_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initValue();
    }

    private void initValue() {

        utils = new Utils();
        url_input = (TextInputEditText) findViewById(R.id.url_input);
        username_input = (TextInputEditText) findViewById(R.id.username_input);
        password_input = (TextInputEditText) findViewById(R.id.password_input);
        url_input.setText(utils.readURL(this));
        username_input.setText(utils.readString(this, getString(R.string.un_key)));
        password_input.setText(utils.readString(this, getString(R.string.pw_key)));

        setSupportActionBar(((Toolbar) findViewById(R.id.toolbar)));
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.setAllSettings(ScrollingActivity.this, url_input.getText().toString(),
                        username_input.getText().toString(), password_input.getText().toString());
                Snackbar.make(view, getString(R.string.change_applied), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
