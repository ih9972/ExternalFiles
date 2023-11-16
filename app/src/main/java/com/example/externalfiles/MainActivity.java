package com.example.externalfiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private boolean extExist, permExist;
    private static final int REQUEST_CODE_PERMISSION = 1;
    private final String FILENAME = "exttest.txt";
    TextView text;
    EditText editText;
    String newString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editTextView);
        verify();
        text.setText(read());
    }

    /**
     * this method checks if external sd-card is installed & functioning & if permission is granted
     * @return whether the External Storage is Available or not.
     */
    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * This method checks if permission is granted
     * @return true if permission is granted
     */

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * This method requests permission to write to external storage
     */

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    /**
     * This method checks if external sd-card is installed & functioning & if permission is granted
     * @return if the external sd-card is installed & functioning & if permission is granted
     */
    public boolean verify(){
        extExist = isExternalStorageAvailable();
        permExist = checkPermission();

        if (!extExist) {
            Toast.makeText(this, "External memory not installed", Toast.LENGTH_SHORT).show();
        }

        if (!permExist){
            requestPermission();
        }
        return (extExist && permExist);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     *This method is called when the save button is clicked
     * This method sets the text in the file to be  the string in str and set the file as the text view text.
     */
    public void save(View view) {
        if(verify()) {
            write(editText.getText().toString());
            text.setText(read());
        }
    }
    /**
     *This method is called when the reset button is clicked
     * This method sets the text in the file to be empty
     */
    public void reset(View view) {
        if(verify()) {
            write();
            text.setText(read());
        }
    }

    /**
     * This method is called when the exit button is clicked
     * This method adds the text in the file to the text in the edit text and closes the app
     * @param view
     */
    public void Exit(View view) {
        if(verify()){
            write(editText.getText().toString());
            finish();
        }
    }

    /**
     * This method is called when any button is clicked
     * @return the new text in the file
     */
    public String read(){
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            if (line != null) {
                while (line != null) {
                    sB.append(line + "\n");
                    line = bR.readLine();
                }
                newString = sB.toString().substring(0, sB.length() - 1);
            }
            else{
                newString = sB.toString();
            }
            bR.close();
            reader.close();
            return newString;
        }
        catch (IOException e) {
            Log.e("MainActivity", e.toString());
        }
        return text.getText().toString();
    }
    /**
     *This method is called when the exit and save buttons are clicked
     * This method sets the text in the file to be  the string in str and set the file as the text view text
     */
    public void write(String str) {
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(text.getText().toString() + str);
            writer.close();
        } catch (IOException e) {
            Log.e("MainActivity", e.toString());
        }
    }

    /**
     *This method is called when the reset button is clicked
     * This method sets the text in the file to be empty and set the empty file as the text view text
     */
    public void write(){
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();
        }
        catch (IOException e) {
            Log.e("MainActivity", e.toString());
        }
    }

 @Override
 public boolean onCreateOptionsMenu(Menu menu) {

 getMenuInflater().inflate(R.menu.main,menu);

 return super.onCreateOptionsMenu(menu);
 }


 /**
  * this method matches the credit to the option that is selected.
  * @param item The menu item that was selected.
 *
 * @return
  */

public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    String st = item.getTitle().toString();
    if (st.equals("Credits screen")) {
        Intent si = new Intent(this, credits.class);
    startActivity(si);
    }
    return super.onOptionsItemSelected(item);
}

}

