package studio.carbonylgroup.mlogger.Activity;

import android.content.Intent;
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
import studio.carbonylgroup.mlogger.Service.WatchingService;
import studio.carbonylgroup.mlogger.Utilities.Utils;

public class ScrollingActivity extends AppCompatActivity {

    private Intent startIntent;
    private Utils utils;

    private TextInputEditText url_input;
    private TextInputEditText username_input;
    private TextInputEditText password_input;
    private Switch start_on_boot_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initValue();
    }

    private void initValue() {

        utils = new Utils();
        startIntent = new Intent(this, WatchingService.class);
//        startWatching();

        url_input = (TextInputEditText) findViewById(R.id.url_input);
        username_input = (TextInputEditText) findViewById(R.id.username_input);
        password_input = (TextInputEditText) findViewById(R.id.password_input);
        start_on_boot_switch = (Switch) findViewById(R.id.start_on_boot_switch);
        url_input.setText(utils.readURL(this));
        username_input.setText(utils.readString(this, getString(R.string.un_key)));
        password_input.setText(utils.readString(this, getString(R.string.pw_key)));
        start_on_boot_switch.setChecked(utils.readOnBoot(this));

        setSupportActionBar(((Toolbar) findViewById(R.id.toolbar)));
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.setAllSettings(ScrollingActivity.this, url_input.getText().toString(),
                        username_input.getText().toString(), password_input.getText().toString(),
                        start_on_boot_switch.isChecked());
                Snackbar.make(view, getString(R.string.change_applied), Snackbar.LENGTH_SHORT).show();
            }
        });
    }

//    private void enableInput(boolean enable){
//
//        toolbar.setEnabled(enable);
//        fab.setEnabled(enable);
//        url_input.setEnabled(enable);
//        username_input.setEnabled(enable);
//        password_input.setEnabled(enable);
//        start_on_boot_switch.setEnabled(enable);
//    }

    private void startWatching() {
        startService(startIntent);
    }

//    private void restartWatching() {
//
//        stopService(startIntent);
//        startActivity(getBaseContext().getPackageManager().
//                getLaunchIntentForPackage(getBaseContext().getPackageName()).
//                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
