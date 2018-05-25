package pl.swiebodzin.pzs.gessnumber2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView player1;
    private TextView player2;
    private TextView pointsPlayer1;
    private TextView pointsPlayer2;
    private TextView currentNumber;
    private EditText editText;
    int currentPlayer = 1;
    int counter = 5;
    int collectPoints1;
    int collectPoints2;
    int playerNum;
    int number;
    int globalCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        currentNumber = findViewById(R.id.currentNumber);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);
        pointsPlayer1 = findViewById(R.id.pointsPlayer1);
        pointsPlayer2 = findViewById(R.id.pointsPlayer2);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setText("START");
        currentNumber.setText("");
        player1.setText("you turn");
        player2.setText("");
        pointsPlayer1.setText("");
        pointsPlayer2.setText("");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = getRandomNumber();
                playerNum = getNumberFromEditText();

                if(playerNum != 0) {
                    gameLogic();
                }
            }
        });

    }

    public int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(10);
    }

    //przelacza pomiedzy uzytkownikami
    public int shiftPlayer(int p) {
        switch (p) {
            case 1:
                currentPlayer = 2;
                player2.setText("you turn");
                player1.setText("");
                counter = 5;
                break;

            case 2:
                currentPlayer = 1;
                player1.setText("you turn");
                player2.setText("");
                counter = 5;
                break;
        }

        return currentPlayer;
    }

    public void gameLogic() {

        try {
            currentNumber.setText(String.valueOf(number));
            counter--;
            button.setText(String.valueOf(counter));
            if(counter == 0) {
                checkCounter();
                shiftPlayer(currentPlayer);
            }

            int number = Integer.parseInt(currentNumber.getText().toString());
            playerNum = Integer.parseInt(editText.getText().toString());

            if (number == playerNum && playerNum != 0) {
                switch (currentPlayer) {
                    case 1:
                        collectPoints1++;
                        pointsPlayer1.setText(String.valueOf(collectPoints1));
                        break;

                    case 2:
                        collectPoints2++;
                        pointsPlayer2.setText(String.valueOf(collectPoints2));
                        break;
                }

            }

        } catch (NumberFormatException e) {
            Toast.makeText(getBaseContext(), "Podaj liczbę", Toast.LENGTH_SHORT).show();

        }

    }

    public int getNumberFromEditText(){

        try {
            return  Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d("errors", "number not found!!!");
        }
        return  0;

    }

    public void checkCounter() {
        if(counter == 0) {
            globalCounter--;
            if(globalCounter == 0) {
                if(collectPoints1 > collectPoints2) {
                    getGameOverActivity(1);
                } else if (collectPoints1 < collectPoints2){
                    getGameOverActivity(2);
                } else if(collectPoints1 == collectPoints2) {
                    getGameOverActivity(0);
                }

            }
        }
    }

    public void getGameOverActivity(int currentPlayer) {
        Intent intent = new Intent(this, GameOverActivity.class);
        String winner = "Wygrywa gracz: ";
        int finalPoints = 0;
        switch (currentPlayer) {
            case 1:
                finalPoints = collectPoints1;
                winner = winner + 1 + " zdobywając: " + finalPoints;
                break;
            case 2:
                finalPoints = collectPoints2;
                winner = winner + 2 + " zdobywając: " + finalPoints;
                break;
            case 0:
                winner = "REMIS!!";
        }

        intent.putExtra("winner", winner);
        startActivity(intent);

    }
}
