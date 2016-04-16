package com.androquad.shobujekattor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    TextView aboutUs, header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        header = (TextView) findViewById(R.id.header);
        aboutUs = (TextView) findViewById(R.id.aboutUs);
        header.setText("BITM Mobile App Developemet Batch 13: Team AndroQuad.\n");
        aboutUs.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.activity_main_actions, menu);
        // Associate searchable configuration with the SearchView


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.aboutApp) {
            Intent intentUs = new Intent(this, AboutUsActivity.class);
            startActivity(intentUs);
        }
        if (id == R.id.refresh) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        if (id == R.id.actionExit) {
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }
}
