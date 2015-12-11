package com.eribeiro.androidchronometer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean ativo = false;
    private CircularProgressBar circularProgressBar;
    private CounterClass timer;
    private TextView txtCronometro, txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circularProgressBar = (CircularProgressBar)findViewById(R.id.custom_progressBar);

        txtCronometro = (TextView) findViewById(R.id.txtCronometro);
        this.txtStatus = (TextView) findViewById(R.id.txtStatus);
        this.txtStatus.setText(R.string.txt_status_stop);
        circularProgressBar.setOnClickListener(this);
        timer = new CounterClass(120000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.custom_progressBar:
                ativo = !ativo;
                if(ativo){
                    this.timer.start();
                    this.txtStatus.setText(R.string.txt_status_recording);
                    Toast.makeText(MainActivity.this, "ativado", Toast.LENGTH_SHORT).show();
                }else {
                    this.timer.cancel();
                    this.txtCronometro.setText(R.string.txt_status_begin);
                    this.txtStatus.setText(R.string.txt_status_stop);
                    circularProgressBar.setProgressWithAnimation(0);
                    Toast.makeText(MainActivity.this, "desativado", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

            circularProgressBar.setProgressWithAnimation(millisUntilFinished/1200);
            txtCronometro.setText(hms);

        }

        @Override
        public void onFinish() {
            txtStatus.setText("Finalizado.");
            txtCronometro.setText("00:00:00");
            circularProgressBar.setProgressWithAnimation(0);
        }
    }

}
