package com.example.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {
    EditText etPlayerX, etPlayerO;
    TextView tvPlayerX, tvPlayerO;
    Button btStart, btAbout;
    RadioButton rbHVH, rbHVP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        rbHVH = findViewById(R.id.rbHVH);
        rbHVP = findViewById(R.id.rbHVP);
        etPlayerO = findViewById(R.id.etPlayerO);
        etPlayerX = findViewById(R.id.etPlayerX);
        rbHVH.setChecked(true);
    }

    public void HVP(View view) {
        etPlayerO.setText("Bot");
        etPlayerO.setError(null);
        etPlayerO.setEnabled(false);
    }

    public void HVH(View view) {
        etPlayerO.setEnabled(true);
        etPlayerO.setText("");
    }


    public void Start(View view) {
        if(Name())
        {

            Intent intentGame = new Intent(CreateActivity.this, MainActivity.class);
            intentGame.putExtra("PlayerX", etPlayerX.getText().toString());
            intentGame.putExtra("PlayerO", etPlayerO.getText().toString());
            startActivity(intentGame);
        }

    }

    public boolean Name()
    {
        int i=0;
        if(etPlayerX.getText().toString().length() ==0 )
        {
            etPlayerX.setError("You must fill in this line");
            i=1;
        }
        if(etPlayerO.getText().toString().length() ==0 )
        {
            etPlayerO.setError("You must fill in this line");
            i=1;
        }
        if(i==1)
        {
            return false;
        } else {
            return true;
        }
    }
}