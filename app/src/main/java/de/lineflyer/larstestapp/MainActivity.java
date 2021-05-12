package de.lineflyer.larstestapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnHint, btnSkip, btnCheck;
    ImageView ivLogo;
    EditText etLogoName;
    TextView tvHint;

    int currentLevel;
    String companyName;
    String imageName;

    final int maxLevel = 4;
    final String prefNameFirstStart = "firstAppStart";
    final String databaseName = "level.db";
    final String databaseTableName = "level";
    final String prefLevel = "currentLevel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLogo = findViewById(R.id.ivLogo);

        tvHint = findViewById(R.id.tvHint);
        etLogoName = findViewById(R.id.etLogoName);

        btnHint = findViewById(R.id.btnHint);
        btnSkip = findViewById(R.id.btnSkip);
        btnCheck = findViewById(R.id.btnCheck);

        btnSkip.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnHint.setOnClickListener(this);
    }

    public void loadLevel(){
        SharedPreferences preferencesLoad = getSharedPreferences(prefLevel, MODE_PRIVATE);
        currentLevel = preferencesLoad.getInt(prefLevel, 1);
        if (currentLevel <= maxLevel){
            SQLiteDatabase database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT * FROM " + databaseTableName + " WHERE id = '" + currentLevel + "'", null);
            cursor.moveToFirst();
            if (cursor.getCount() == 1){
                companyName = cursor.getString(1);
                imageName = cursor.getString(2);

                cursor.close();
                database.close();
            }
        }
    }

    public void safeLevel(){
        SharedPreferences preferencesLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesLevel.edit();

        editor.putInt(prefLevel, currentLevel);
        editor.commit();
    }

    public void animateLevelCompleted(){

    }

    public boolean firstAppStart(){
        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart, MODE_PRIVATE);
        if (preferences.getBoolean(prefNameFirstStart, true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(prefNameFirstStart, false);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public void createDatabase(){
        SQLiteDatabase database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE " + databaseTableName + " (id INTEGER, company TEXT, imageName TEXT)");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('1', 'NASA', 'nasa')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('2', 'YouTube', 'youtube')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('3', 'Instagram', 'instagram')");
        database.execSQL("INSERT INTO " + databaseTableName + " VALUES('4', 'Audi', 'audi')");
        database.close();
    }



    @Override
    public void onClick(View v) {

    }
}