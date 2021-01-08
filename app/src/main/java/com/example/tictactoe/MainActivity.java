package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Its proper 2d array for the game data storing
    private Button[][] buttons = new Button[3][3];
    //Initial turn is playerOnes
    private boolean playerOneTurn = true;
    //For counting if after 9 rounds if there is no user so we can say it's a draw
    private int countRound;

    //Total point of the both the player
    private int playerOnePoints;
    private int playerTwoPoints;


    //all text view declare earlier
    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialising the textViews
        textViewPlayerOne = findViewById(R.id.textView1);
        textViewPlayerTwo = findViewById(R.id.textView2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String BUTTONID = "button" + i + "" + j;
                int buttonArrayId = getResources().getIdentifier(BUTTONID, "id", getPackageName());
                buttons[i][j] = findViewById(buttonArrayId);

                //button click
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!((Button) view).getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this,
                                    "This is already marked", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (playerOneTurn) {
                            ((Button) view).setText("X");
                        } else {
                            ((Button) view).setText("O");
                        }

                        countRound++;

                        if (checkResult()) {
                            if (playerOneTurn) {
                                playerOneWins();
                            } else {
                                playerTwoWins();
                            }
                        } else if (countRound == 9) {
                            draw();
                        } else {
                            playerOneTurn = !playerOneTurn;
                        }
                    }
                });
            }
        }

        Button buttonReset = findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    private void draw() {
        Toast.makeText(MainActivity.this,
                "Draw", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void playerTwoWins() {
        Toast.makeText(MainActivity.this,
                "Player Two Wins(O)", Toast.LENGTH_SHORT).show();
        playerTwoPoints++;
        textViewPlayerTwo.setText("Player 2 (O): " + playerTwoPoints);
        resetBoard();
    }

    private void playerOneWins() {
        Toast.makeText(MainActivity.this,
                "Player One Wins(X)", Toast.LENGTH_SHORT).show();
        playerOnePoints++;
        textViewPlayerOne.setText("Player 1 (X): " + playerOnePoints);
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        countRound = 0;
        playerOneTurn = true;
    }

    private void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;

        textViewPlayerOne.setText("Player 1 (X): " + playerOnePoints);
        textViewPlayerTwo.setText("Player 2 (O): " + playerTwoPoints);
        resetBoard();
    }


    private  boolean checkResult() {
        String fields[][] = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals(fields[i][1])
                && fields[i][0].equals(fields[i][2])
                && !fields[i][0].equals("") ) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (fields[0][i].equals(fields[1][i])
                    && fields[0][i].equals(fields[2][i])
                    && !fields[0][i].equals("") ) {
                return true;
            }
        }

        if (fields[0][0].equals(fields[1][1])
                && fields[0][0].equals(fields[2][2])
                && !fields[0][0].equals("") ) {
            return true;
        }

        if (fields[0][2].equals(fields[1][1])
                && fields[0][2].equals(fields[2][0])
                && !fields[0][2].equals("") ) {
            return true;
        }


        return false;
    }
}