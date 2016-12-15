package com.formantsynthesizer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.formantsynthesizer.Filter.GraphItem;
import com.formantsynthesizer.Filter.VocalFilter;
import com.formantsynthesizer.View.WaveFormView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonDraw = (Button) findViewById(R.id.drawBtn);
        buttonDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textBoxF1 = (EditText) findViewById(R.id.formant1);
                EditText textBoxF2 = (EditText) findViewById(R.id.formant2);
                EditText textBoxF3 = (EditText) findViewById(R.id.formant3);
                EditText textBoxB1 = (EditText) findViewById(R.id.bandwidth1);
                EditText textBoxB2 = (EditText) findViewById(R.id.bandwidth2);
                EditText textBoxB3 = (EditText) findViewById(R.id.bandwidth3);
                EditText textBoxFs = (EditText) findViewById(R.id.fsample);
                textBoxF1.setText("881");
                textBoxF2.setText("1532");
                textBoxF3.setText("2476");
                textBoxB1.setText("177");
                textBoxB2.setText("119");
                textBoxB3.setText("237");
                textBoxFs.setText("8192");
                int[] formant = new int[3];
                int[] bandwidth = new int[3];
                int fs = Integer.valueOf(textBoxFs.getText().toString());
                formant[0] = Integer.valueOf(textBoxF1.getText().toString());
                formant[1] = Integer.valueOf(textBoxF2.getText().toString());
                formant[2] = Integer.valueOf(textBoxF3.getText().toString());
                bandwidth[0] = Integer.valueOf(textBoxB1.getText().toString());
                bandwidth[1] = Integer.valueOf(textBoxB2.getText().toString());
                bandwidth[2] = Integer.valueOf(textBoxB3.getText().toString());
                VocalFilter vocalFilter = new VocalFilter(formant, bandwidth, fs);
                GraphItem magnitudeResponse = vocalFilter.Sampling(1024);
                WaveFormView waveFormView = (WaveFormView) findViewById(R.id.magnitude);
//                waveFormView.setMinimumHeight(waveFormView.getWidth());
                waveFormView.setWaveForm(magnitudeResponse);
                waveFormView.drawWave();
            }
        });
    }
}
