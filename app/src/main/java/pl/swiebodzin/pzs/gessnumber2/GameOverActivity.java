package pl.swiebodzin.pzs.gessnumber2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        TextView textView = findViewById(R.id.gameOverText); //nieuzywany;
        TextView winnerText = findViewById(R.id.winnerText);
        getIntent().getStringExtra("winner");
        String s = getIntent().getStringExtra("winner");
        winnerText.setText(s);
    }
}
