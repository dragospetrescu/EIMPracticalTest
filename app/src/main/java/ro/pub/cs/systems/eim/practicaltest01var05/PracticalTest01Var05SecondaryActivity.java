package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        textView.setText("");

        int num1;
        int num2;
        int num3;
        int bife;

        int score = 0;
        Intent intent = getIntent();
        if (intent!= null) {
            num1 = intent.getIntExtra("num1", -1);
            num2 = intent.getIntExtra("num2", -1);
            num3 = intent.getIntExtra("num3", -1);
            bife = intent.getIntExtra("bife", -1);

            String afis = "AICI " + String.valueOf(num1 + " " + num2 + " " + num3);
            if((num1 == num2 || num1 == 0 || num2 == 0) && (num2 == num3 || num2 == 0 || num3 == 0) && (num1 == num3 || num1 == 0 || num3 == 0)){
                if (bife == 0) {
                    score  = 10;
                }else if (bife == 1) {
                    score = 50;
                } else if (bife == 2) {
                    score = 100;
                }
            } else {
                score = 0;
            }
            textView.setText(String.valueOf(score));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05MainActivity.class);
                intent.putExtra("score", Integer.parseInt(textView.getText().toString()));
                setResult(0, intent);
                finish();
            }
        });

    }
}
