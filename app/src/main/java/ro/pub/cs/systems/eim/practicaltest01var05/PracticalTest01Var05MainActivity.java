package ro.pub.cs.systems.eim.practicaltest01var05;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;

    TextView textView1;
    TextView textView2;
    TextView textView3;

    Button button;
    IntentFilter intentFilter;
    private int MY_REQ = 1;
    int score = 0;
    MessageBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);

        checkBox1 = findViewById(R.id.button1);
        checkBox2 = findViewById(R.id.button2);
        checkBox3 = findViewById(R.id.button3);

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView1.setText("");
        textView2.setText("");
        textView3.setText("");

        receiver = new MessageBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("VICTORY");
        button = findViewById(R.id.button5);

        if(savedInstanceState != null) {
            score = savedInstanceState.getInt("score");
        }

        final Random random = new Random();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num2 = -1;
                int num3 = -1;
                int bife = 0;
                int num1 = -1;
                if(!checkBox1.isChecked()) {
                    num1 = Math.abs(random.nextInt()) %4;
                    textView1.setText(String.valueOf(num1));
                    bife++;
                } else {
                    num1 = Integer.parseInt(textView1.getText().toString());
                }
                if(!checkBox2.isChecked()) {
                    num2 = Math.abs(random.nextInt()) %4;
                    textView2.setText(String.valueOf(num2));
                    bife++;
                } else {
                    num2 = Integer.parseInt(textView2.getText().toString());
                }
                if(!checkBox3.isChecked()) {
                    num3 = Math.abs(random.nextInt()) %4;
                    textView3.setText(String.valueOf(num3));
                    bife++;
                } else {
                    num3 = Integer.parseInt(textView3.getText().toString());
                }

                String afis = String.valueOf(num1 + " " + num2 + " " + num3);

                Toast.makeText(getApplicationContext(), afis, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05SecondaryActivity.class);
                intent.putExtra("num1", num1);
                intent.putExtra("num2", num2);
                intent.putExtra("num3", num3);
                intent.putExtra("bife", bife);
                startActivityForResult(intent, MY_REQ);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == MY_REQ) {
            int localScore = intent.getIntExtra("score", -1);
            score += localScore;
            Toast.makeText(this, "Total score " + score, Toast.LENGTH_LONG).show();

            Intent newIntent = new Intent(getApplicationContext(), MyService.class);
            intent.putExtra("score", score);
            getApplicationContext().startService(newIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("score", score);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        score = savedInstanceState.getInt("score");
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Log.d("[Message]", "RECEIVED " + Stringtring.valueOf(intent.getIntExtra("date", -1)));
            Log.d("[Message]", "RECEIVED " + intent.getStringExtra("date"));
        }
    }

}
