package quesscorp.com.uicomponents.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import quesscorp.com.uicomponents.R;
import quesscorp.com.uicomponents.samples.cropimage.CropImageSampleActivity;
import quesscorp.com.uicomponents.samples.materialdialogs.MaterialDialogSampleActivity;
import quesscorp.com.uicomponents.samples.photoviewSample.PhotoViewSampleActivity;
import quesscorp.com.uicomponents.samples.progresswheelSample.ProgressWheelSampleActivity;
import quesscorp.com.uicomponents.samples.textdrawableSample.TextDrawableSampleActivity;
import quesscorp.com.uicomponents.samples.waitingdotsSample.WaitingDotsSampleActivity;

/**
 * Created by Shubham
 * Used to index all the samples
 */
public class HomeActivity extends AppCompatActivity {

    Button mButtonWaitingDots, mButtonTextDrawable, mButtonProgressWheel, mButtonPhotoView, mButtonCropImage;
    @BindView(R.id.btn_waiting_dots)
    Button btnWaitingDots;
    @BindView(R.id.btn_text_drawable)
    Button btnTextDrawable;
    @BindView(R.id.btn_progress_wheel)
    Button btnProgressWheel;
    @BindView(R.id.btn_photo_view)
    Button btnPhotoView;
    @BindView(R.id.btn_crop_image)
    Button btnCropImage;
    @BindView(R.id.btn_material_dialog)
    Button btnMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_waiting_dots, R.id.btn_text_drawable, R.id.btn_progress_wheel, R.id.btn_photo_view, R.id.btn_crop_image, R.id.btn_material_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_waiting_dots:
                startActivity(WaitingDotsSampleActivity.newIntent(this));
                break;
            case R.id.btn_text_drawable:
                startActivity(TextDrawableSampleActivity.newIntent(this));
                break;
            case R.id.btn_progress_wheel:
                startActivity(ProgressWheelSampleActivity.newIntent(this));
                break;
            case R.id.btn_photo_view:
                startActivity(PhotoViewSampleActivity.newIntent(this));
                break;
            case R.id.btn_crop_image:
                startActivity(CropImageSampleActivity.newIntent(this));
                break;
            case R.id.btn_material_dialog:
                startActivity(MaterialDialogSampleActivity.newIntent(this));
                break;
        }
    }
}
