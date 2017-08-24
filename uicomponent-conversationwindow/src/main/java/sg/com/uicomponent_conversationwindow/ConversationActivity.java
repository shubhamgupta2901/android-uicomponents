package sg.com.uicomponent_conversationwindow;

import android.content.BroadcastReceiver;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import sg.com.uicomponent_conversationwindow.camera.QuickAttachmentDrawer;
import sg.com.uicomponent_conversationwindow.emoji.EmojiDrawer;
import sg.com.uicomponent_conversationwindow.uicomponents.AnimatingToggle;
import sg.com.uicomponent_conversationwindow.uicomponents.ComposeText;
import sg.com.uicomponent_conversationwindow.uicomponents.HidingLinearLayout;
import sg.com.uicomponent_conversationwindow.uicomponents.InputAwareLayout;
import sg.com.uicomponent_conversationwindow.uicomponents.InputPanel;
import sg.com.uicomponent_conversationwindow.uicomponents.KeyboardAwareLinearLayout;
import sg.com.uicomponent_conversationwindow.uicomponents.Stub;
import sg.com.uicomponent_conversationwindow.utils.TextSecurePreferences;
import sg.com.uicomponent_conversationwindow.utils.ViewUtil;

/**
 * Created by Shubham on 23/08/17.
 */

public class ConversationActivity extends AppCompatActivity implements InputPanel.Listener,
        InputPanel.MediaListener, KeyboardAwareLinearLayout.OnKeyboardShownListener {

    protected ComposeText composeText;
    private AnimatingToggle buttonToggle;
    //private SendButton sendButton;
    private ImageButton attachButton;
    private TextView charactersLeft;
    private Button unblockButton;
    private Button makeDefaultSmsButton;
    private InputAwareLayout container;
    private View composePanel;

    private BroadcastReceiver securityUpdateReceiver;
    private BroadcastReceiver recipientsStaleReceiver;
    private Stub<EmojiDrawer> emojiDrawerStub;
    protected HidingLinearLayout quickAttachmentToggle;
    private QuickAttachmentDrawer quickAttachmentDrawer;
    private InputPanel inputPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initializeActionBar();
        initializeViews();
    }

    private void initializeViews() {
        buttonToggle = ViewUtil.findById(this, R.id.button_toggle);
        //sendButton            = ViewUtil.findById(this, R.id.send_button);
        attachButton = ViewUtil.findById(this, R.id.attach_button);
        composeText = ViewUtil.findById(this, R.id.embedded_text_editor);
        charactersLeft = ViewUtil.findById(this, R.id.space_left);
        emojiDrawerStub = ViewUtil.findStubById(this, R.id.emoji_drawer_stub);
        unblockButton = ViewUtil.findById(this, R.id.unblock_button);
        makeDefaultSmsButton = ViewUtil.findById(this, R.id.make_default_sms_button);
        composePanel = ViewUtil.findById(this, R.id.bottom_panel);
        container = ViewUtil.findById(this, R.id.layout_container);
        quickAttachmentDrawer = ViewUtil.findById(this, R.id.quick_attachment_drawer);
        quickAttachmentToggle = ViewUtil.findById(this, R.id.quick_attachment_toggle);
        inputPanel = ViewUtil.findById(this, R.id.bottom_panel);

        ImageButton quickCameraToggle = ViewUtil.findById(this, R.id.quick_camera_toggle);
        View composeBubble = ViewUtil.findById(this, R.id.compose_bubble);

        container.addOnKeyboardShownListener(this);
        inputPanel.setListener(this);
        inputPanel.setMediaListener(this);

        int[] attributes = new int[]{R.attr.conversation_item_bubble_background};
        TypedArray colors = obtainStyledAttributes(attributes);
        int defaultColor = colors.getColor(0, Color.WHITE);
        composeBubble.getBackground().setColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY);
        colors.recycle();


        SendButtonListener sendButtonListener = new SendButtonListener();
        ComposeKeyPressedListener composeKeyPressedListener = new ComposeKeyPressedListener();

        composeText.setOnEditorActionListener(sendButtonListener);

        //sendButton.setOnClickListener(sendButtonListener);
        //sendButton.setEnabled(true);

        unblockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        makeDefaultSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        composeText.setOnKeyListener(composeKeyPressedListener);
        composeText.addTextChangedListener(composeKeyPressedListener);
        composeText.setOnEditorActionListener(sendButtonListener);
        composeText.setOnClickListener(composeKeyPressedListener);
        composeText.setOnFocusChangeListener(composeKeyPressedListener);

        if (QuickAttachmentDrawer.isDeviceSupported(this)) {
            //quickAttachmentDrawer.setListener(this);
            quickCameraToggle.setOnClickListener(new QuickCameraToggleListener());
        } else {
            quickCameraToggle.setVisibility(View.GONE);
            quickCameraToggle.setEnabled(false);
        }
    }

    protected void initializeActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.conversation_title_view);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    @Override
    public void onRecorderStarted() {

    }

    @Override
    public void onRecorderFinished() {

    }

    @Override
    public void onRecorderCanceled() {

    }

    @Override
    public void onEmojiToggle() {
        if (!emojiDrawerStub.resolved()) {
            inputPanel.setEmojiDrawer(emojiDrawerStub.get());
            emojiDrawerStub.get().setEmojiEventListener(inputPanel);
        }

        if (container.getCurrentInput() == emojiDrawerStub.get()) {
            container.showSoftkey(composeText);
        } else {
            container.show(composeText, emojiDrawerStub.get());
        }
    }

    @Override
    public void onMediaSelected(@NonNull Uri uri, String contentType) {

    }

    private void calculateCharactersRemaining() {
        /*String messageBody = composeText.getTextTrimmed();
        TransportOption transportOption = sendButton.getSelectedTransport();
        CharacterCalculator.CharacterState characterState = transportOption.calculateCharacters(messageBody);

        if (characterState.charactersRemaining <= 15 || characterState.messagesSpent > 1) {
            charactersLeft.setText(characterState.charactersRemaining + "/" + characterState.maxMessageSize
                    + " (" + characterState.messagesSpent + ")");
            charactersLeft.setVisibility(View.VISIBLE);
        } else {
            charactersLeft.setVisibility(View.GONE);
        }*/
    }

    private void updateToggleButtonState() {
        if (composeText.getTextTrimmed().length() == 0) {
            buttonToggle.display(attachButton);
            quickAttachmentToggle.show();
        } else {
            //buttonToggle.display(sendButton);
            quickAttachmentToggle.hide();
        }
    }

    @Override
    public void onKeyboardShown() {
        inputPanel.onKeyboardShown();
    }

    private class SendButtonListener implements View.OnClickListener, TextView.OnEditorActionListener {
        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                //sendButton.performClick();
                return true;
            }
            return false;
        }
    }

    private class ComposeKeyPressedListener implements View.OnKeyListener, View.OnClickListener, TextWatcher, View.OnFocusChangeListener {

        int beforeLength;

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (TextSecurePreferences.isEnterSendsEnabled(ConversationActivity.this)) {
                        //sendButton.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                        //sendButton.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            container.showSoftkey(composeText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            beforeLength = composeText.getTextTrimmed().length();
        }

        @Override
        public void afterTextChanged(Editable s) {
            calculateCharactersRemaining();

            if (composeText.getTextTrimmed().length() == 0 || beforeLength == 0) {
                composeText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateToggleButtonState();
                    }
                }, 50);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
        }
    }

    private class QuickCameraToggleListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!quickAttachmentDrawer.isShowing()) {
                composeText.clearFocus();
                container.show(composeText, quickAttachmentDrawer);
            } else {
                container.hideAttachedInput(false);
            }
        }
    }
}
