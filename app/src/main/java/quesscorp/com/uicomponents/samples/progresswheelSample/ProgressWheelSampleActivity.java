package quesscorp.com.uicomponents.samples.progresswheelSample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import quesscorp.com.uicomponents.R;
import sg.com.uicomponent_progresswheel.ProgressCallback;
import sg.com.uicomponent_progresswheel.ProgressWheel;


public class ProgressWheelSampleActivity extends ActionBarActivity {


    public static Intent newIntent(Context mContext) {
        Intent intent = new Intent(mContext, ProgressWheelSampleActivity.class);
        return intent;
    }

    private ProgressWheel progressWheel;
    private ProgressWheel progressWheelInterpolated;
    private ProgressWheel progressWheelLinear;

    private TextView interpolatedValue;
    private TextView linearValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_wheel_sample);

        Button buttonAbout = (Button) findViewById(R.id.button_about);
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ProgressWheelSampleActivity.this)
                        .setTitle(R.string.about)
                        .setMessage(R.string.about_text)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                dialog.show();
            }
        });

        progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        progressWheelInterpolated = (ProgressWheel) findViewById(R.id.interpolated);
        progressWheelLinear = (ProgressWheel) findViewById(R.id.linear);

        interpolatedValue = (TextView) findViewById(R.id.interpolatedValue);
        linearValue = (TextView) findViewById(R.id.linearValue);

        Spinner spinnerOptions = (Spinner) findViewById(R.id.spinner_options);
        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheelLinear.setProgress(0.0f);
                        progressWheelInterpolated.setProgress(0.0f);

                        progressWheelInterpolated.setCallback(new ProgressCallback() {
                            @Override
                            public void onProgressUpdate(float progress) {
                                if(progress == 0) {
                                    progressWheelInterpolated.setProgress(1.0f);
                                } else if(progress == 1.0f) {
                                    progressWheelInterpolated.setProgress(0.0f);
                                }

                                interpolatedValue.setText(String.format("%.2f", progress));
                            }
                        });

                        progressWheelLinear.setCallback(new ProgressCallback() {
                            @Override
                            public void onProgressUpdate(float progress) {
                                if(progress == 0) {
                                    progressWheelLinear.setProgress(1.0f);
                                } else if(progress == 1.0f) {
                                    progressWheelLinear.setProgress(0.0f);
                                }

                                linearValue.setText(String.format("%.2f", progress));
                            }
                        });
                        break;
                    case 1:
                        setProgress(0.0f);
                        break;
                    case 2:
                        setProgress(0.1f);
                        break;
                    case 3:
                        setProgress(0.25f);
                        break;
                    case 4:
                        setProgress(0.5f);
                        break;
                    case 5:
                        setProgress(0.75f);
                        break;
                    case 6:
                        setProgress(1.0f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final int defaultBarColor = progressWheel.getBarColor();
        final int defaultWheelColor = progressWheel.getRimColor();

        Spinner colorOptions = (Spinner) findViewById(R.id.spinner_options_color);
        colorOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheel.setBarColor(defaultBarColor);
                        progressWheelInterpolated.setBarColor(defaultBarColor);
                        progressWheelLinear.setBarColor(defaultBarColor);
                        break;
                    case 1:
                        progressWheel.setBarColor(Color.RED);
                        progressWheelInterpolated.setBarColor(Color.RED);
                        progressWheelLinear.setBarColor(Color.RED);
                        break;
                    case 2:
                        progressWheel.setBarColor(Color.MAGENTA);
                        progressWheelInterpolated.setBarColor(Color.MAGENTA);
                        progressWheelLinear.setBarColor(Color.MAGENTA);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner wheelColorOptions = (Spinner) findViewById(R.id.spinner_options_rim_color);
        wheelColorOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0:
                        progressWheel.setRimColor(defaultWheelColor);
                        progressWheelInterpolated.setRimColor(defaultWheelColor);
                        progressWheelLinear.setRimColor(defaultWheelColor);
                        break;
                    case 1:
                        progressWheel.setRimColor(Color.LTGRAY);
                        progressWheelInterpolated.setRimColor(Color.LTGRAY);
                        progressWheelLinear.setRimColor(Color.LTGRAY);
                        break;
                    case 2:
                        progressWheel.setRimColor(Color.GRAY);
                        progressWheelInterpolated.setRimColor(Color.GRAY);
                        progressWheelLinear.setRimColor(Color.GRAY);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setProgress(float progress) {
        progressWheelLinear.setCallback(new ProgressCallback() {
            @Override
            public void onProgressUpdate(float progress) {
                linearValue.setText(String.format("%.2f", progress));
            }
        });
        progressWheelInterpolated.setCallback(new ProgressCallback() {
            @Override
            public void onProgressUpdate(float progress) {
                interpolatedValue.setText(String.format("%.2f", progress));
            }
        });

        progressWheelLinear.setProgress(progress);
        progressWheelInterpolated.setProgress(progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }
}