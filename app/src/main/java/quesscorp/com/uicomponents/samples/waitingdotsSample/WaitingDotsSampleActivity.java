package quesscorp.com.uicomponents.samples.waitingdotsSample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import quesscorp.com.uicomponents.R;
import quesscorp.com.uicomponents.components.waitingdots.DotsTextView;

public class WaitingDotsSampleActivity extends AppCompatActivity {

    DotsTextView dotsTextView;
    Button buttonPlay;
    Button buttonHide;
    Button buttonHideAndStop;


    public static Intent newIntent(Context mContext) {
        Intent intent = new Intent(mContext, WaitingDotsSampleActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_dots_sample);
        configureWaitingDotsButtons();

    }

    void configureWaitingDotsButtons() {

        dotsTextView = (DotsTextView) findViewById(R.id.dots);
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHide = (Button) findViewById(R.id.buttonHide);
        buttonHideAndStop = (Button) findViewById(R.id.buttonHideAndStop);


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dotsTextView.isPlaying()) {
                    dotsTextView.stop();
                } else {
                    dotsTextView.start();
                }
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dotsTextView.isHide()) {
                    dotsTextView.show();
                } else {
                    dotsTextView.hide();
                }
            }
        });

        buttonHideAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dotsTextView.isHide()) {
                    dotsTextView.showAndPlay();
                } else {
                    dotsTextView.hideAndStop();
                }
            }
        });

    }

}
