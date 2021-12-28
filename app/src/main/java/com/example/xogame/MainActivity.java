package com.example.xogame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b;
    TextView tvTurn,tvX,tvO;
    String Turn = "X";
    int[] button_ids = {
            R.id.btGame1,
            R.id.btGame2,
            R.id.btGame3,
            R.id.btGame4,
            R.id.btGame5,
            R.id.btGame6,
            R.id.btGame7,
            R.id.btGame8,
            R.id.btGame9,
    };
    int[] game = {
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    };
    int[][] win = {
            new int[]{0, 1, 2}, new int[]{3, 4, 5}, new int[]{6, 7, 8},
            new int[]{0, 3, 6}, new int[]{1, 4, 7}, new int[]{2, 5, 8},
            new int[]{0, 4, 8}, new int[]{2, 4, 6},
    };
    int turnCounter = 0;

    String PlayerXName, PlayerOName;
    Boolean Mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int id : button_ids) {
            b = (Button) findViewById(id);
            b.setOnClickListener(this);
        }
        tvTurn = findViewById(R.id.tvTurn);
        tvX = findViewById(R.id.tvX);
        tvO = findViewById(R.id.tvO);

        Intent intentLogin = getIntent();
        if(intentLogin.getExtras() != null){
            PlayerOName = intentLogin.getStringExtra("PlayerO");
            PlayerXName = intentLogin.getStringExtra("PlayerX");
        }
        tvX.setText("X: "+PlayerXName);
        tvO.setText("O: "+PlayerOName);
    }


    public void nextTurn() {
        if (Turn.equals("X")) {
            Turn = "O";
        } else if (Turn.equals("O")) {
            Turn = "X";
        }
        tvTurn.setText("Turn: " + Turn);
    }

    @Override
    public void onClick(View view) {


        int game1 = -1;
        turnCounter += 1;
        for (int id : button_ids) {
            game1 += 1;
            b = (Button) findViewById(id);
            if (view.getId() == id) {
                b.setText(Turn);
                b.setClickable(false);

                if (Turn.equals("X")) {
                    game[game1] = 1;
                } else {
                    game[game1] = 2;
                }
                win();
                nextTurn();
            }
        }
        if(PlayerOName.equals("Bot"))
            {
                if(turnCounter != 9)
                {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            BotPlay();
                        }
                    }, 100);
                }
        }
    }

    public void win() {
        for (int i = 0; i < win.length; i++) {
            int[] w = win[i];
            if (game[w[0]] > 0 && game[w[1]] > 0 && game[w[2]] > 0) {

                if (game[w[0]] == game[w[1]] && game[w[1]] == game[w[2]] && game[w[0]] == game[w[2]]) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Game Over...");
                    builder.setCancelable(false);
                    builder.setMessage("Player " + Turn + " won\nDo you want to start a new game?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newGame();
                        }
                    })
                            .setNegativeButton("No", null);

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;

                }
            }
        }
        Results();
    }

    public void newGame() {
        for (int id : button_ids) {
            b = (Button) findViewById(id);
            b.setText("");
            b.setClickable(true);
        }
        for (int i = 0; i < game.length; i++) {
            game[i] = 0;
        }
        Turn="X";
        tvTurn.setText("Turn: "+Turn);
        turnCounter =0;
    }

    public void Results()
    {
        if(turnCounter == 9)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Game Over...");

            builder.setMessage("No winner!\nDo you want to start a new game?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    newGame();
                }
            })
                    .setNegativeButton("No", null);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void ClickNew(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("New Game");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to start a new game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGame();
            }
        })
                .setNegativeButton("No", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void ClickBack(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Go Back");
        builder.setCancelable(false);
        builder.setMessage("Are you sure you want to return to the previous screen?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentCreate = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intentCreate);
            }
        })
                .setNegativeButton("No", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void BotPlay()
    {
        int random;
        Random rnd = new Random();
        random = rnd.nextInt(9);
        while (game[random] != 0)
        {
            random = rnd.nextInt(9);
        }
        int game1 = -1;
        turnCounter += 1;
        for (int id : button_ids) {
            game1 += 1;
            b = (Button) findViewById(id);
            if (button_ids[random] == id) {
                b.setText(Turn);
                b.setClickable(false);

                if (Turn.equals("X")) {
                    game[game1] = 1;
                } else {
                    game[game1] = 2;
                }
                win();
                nextTurn();
            }
        }
    }
}