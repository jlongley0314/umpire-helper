package com.example.jaremylongley.umpirehelper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";
    int strikes = 0;
    int balls = 0;
    int outs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Restore preferences when app has been killed
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int sharedOuts = settings.getInt("outs", 0);
        this.outs = sharedOuts;
        this.update();

        Button strikeButton = (Button) findViewById(R.id.button);
        Button ballButton = (Button) findViewById(R.id.button2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Set preferences when app stops
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("outs", outs);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.reload :
                this.balls=0;
                this.strikes=0;
                this.outs=0;
                this.update();
                return true;
            case R.id.about :
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /** Call when user taps the Strike button **/
    public void incrementStrike(View view) {
        if (this.strikes < 2) {
            this.strikes++;
        } else {
            this.balls=0;
            this.strikes=0;
            this.outs++;
            this.showMessage("Out!");
        }
        this.update();
    }
    /** Call when user taps the Ball button **/
    public void incrementBall(View view) {
        if (this.balls < 3) {
            this.balls++;
        } else {
            this.balls=0;
            this.strikes=0;
            this.showMessage("Walk!");
        }
        this.update();
    }

    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView msg = new TextView(this);
        msg.setText(message);
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextSize(50);
        builder.setView(msg);
        AlertDialog dialog = builder.show();
    }

    public void update() {
        TextView strikeTextView = (TextView) findViewById(R.id.textView4);
        TextView ballsTextView = (TextView) findViewById(R.id.textView5);
        TextView outsTextView = (TextView) findViewById(R.id.outsTextView);

        String strikeString = Integer.toString(this.strikes);
        strikeTextView.setText(strikeString);

        String ballString = Integer.toString(this.balls);
        ballsTextView.setText(ballString);

        String outString = Integer.toString(this.outs);
        outsTextView.setText(outString);
    }

    public void showAbout(View view) {
        System.out.println("here");
    }

}

