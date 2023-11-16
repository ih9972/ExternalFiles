package com.example.externalfiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class credits extends AppCompatActivity {
    TextView mTextView;
    String st;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        Intent gi = getIntent();
        mTextView = (TextView) findViewById(R.id.textv);
        mTextView.setText("All of the credits belongs to Itai Hadar THE KING ");
    }

    /**
     * <p> Creating the options menu
     * </p>
     * @param menu the Menu object to pass to the inflater
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * this method matches the credit to the option that is selected.
     * @param item The menu item that was selected.
     *
     * @return
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        st = item.getTitle().toString();
        if (st.equals("Main screen")) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}