package com.example.akwasikwasia.myemogif;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nsromapa.emoticompack.samsung.SamsungEmoticonProvider;
import com.nsromapa.say.emogifstickerkeyboard.EmoticonGIFKeyboardFragment;
import com.nsromapa.say.emogifstickerkeyboard.emoticons.Emoticon;
import com.nsromapa.say.emogifstickerkeyboard.emoticons.EmoticonSelectListener;
import com.nsromapa.say.emogifstickerkeyboard.gifs.Gif;
import com.nsromapa.say.emogifstickerkeyboard.gifs.GifSelectListener;
import com.nsromapa.say.emogifstickerkeyboard.internal.sticker.StickerSelectListener;
import com.nsromapa.say.emogifstickerkeyboard.widget.EmoticonEditText;
import com.nsromapa.say.emogifstickerkeyboard.widget.EmoticonTextView;
import com.nsromapa.gifpack.giphy.GiphyGifProvider;

public class MainActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = "MainActivity";
    private EmoticonGIFKeyboardFragment mEmoticonGIFKeyboardFragment;

    private static InputMethodManager inputMethodManager;
    EmoticonEditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView selectedImageView = findViewById(R.id.selected_image_view);

        inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        //Set the emoticon text view.
        final EmoticonTextView textView = findViewById(R.id.selected_emoticons_tv);

        /*
          Set the custom emoticon icon provider. If you don't set any icon provider here, library
          will render system emoticons. Here we are setting iOS emoticons icon pack.
         */
        textView.setEmoticonProvider(SamsungEmoticonProvider.create());


        //Set the emoticon edit text.
        editText = findViewById(R.id.selected_emoticons_et);


        /*
          Set the custom emoticon icon provider. If you don't set any icon provider here, library
          will render system emoticons. Here we are setting Android 8.0 emoticons icon pack.
         */
        editText.setEmoticonProvider(SamsungEmoticonProvider.create());


        //Set emoticon configuration.
        EmoticonGIFKeyboardFragment.EmoticonConfig emoticonConfig = new EmoticonGIFKeyboardFragment.EmoticonConfig()

                /*
                  Set the custom emoticon icon provider. If you don't set any icon provider here, library
                  will render system emoticons. Here we are setting Windows 10 emoticons icon pack.
                 */
                .setEmoticonProvider(SamsungEmoticonProvider.create())

                /*
                  Set the emoticon select listener. This will notify you when user selects any emoticon from
                  list or user preses back button.
                  NOTE: The process of removing last character when user preses back space will handle
                  by library if your edit text is in focus.
                 */
                .setEmoticonSelectListener(new EmoticonSelectListener() {

                    @Override
                    public void emoticonSelected(Emoticon emoticon) {
                        //Do something with new emoticon.
                        Log.d(TAG, "emoticonSelected: " + emoticon.getUnicode());
                        editText.append(emoticon.getUnicode(),
                                editText.getSelectionStart(),
                                editText.getSelectionEnd());
                    }

                    @Override
                    public void onBackSpace() {
                        //Do something here to handle backspace event.
                        //The process of removing last character when user preses back space will handle
                        //by library if your edit text is in focus.
                    }
                });







        //Create GIF config
        EmoticonGIFKeyboardFragment.GIFConfig gifConfig = new EmoticonGIFKeyboardFragment
                /*
                  Set the desired GIF provider. Here we are using GIPHY to provide GIFs.
                  Create Giphy GIF provider by passing your key.
                  It is required to set GIF provider before adding fragment into container.
                 */
                .GIFConfig(GiphyGifProvider.create(this, "564ce7370bf347f2b7c0e4746593c179"))

                /*
                  Implement GIF select listener. This will notify you when user selects new GIF.
                 */
                .setGifSelectListener(new GifSelectListener() {
                    @Override
                    public void onGifSelected(@NonNull Gif gif) {
                        //Do something with the selected GIF.
                        Log.d(TAG, "onGifSelected: " + gif.getGifUrl());
                        Glide.with(MainActivity.this)
                                .load(gif.getGifUrl())
                                .asGif()
                                .placeholder(R.mipmap.ic_launcher)
                                .into(selectedImageView);
                    }
                });








              EmoticonGIFKeyboardFragment.STICKERConfig stickerConfig = new EmoticonGIFKeyboardFragment.STICKERConfig()
                .setStickerSelectedListener(new StickerSelectListener() {
                    @Override
                    public void onStickerSelectListner(@NonNull int sticker) {
                        Log.d(TAG, "stickerSelected: " + sticker);
                        Glide.with(MainActivity.this)
                                .load(sticker)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(selectedImageView);
                    }
                });









        /*
          Create instance of emoticon gif keyboard by passing emoticon and gif config. If you pass null
          to emoticon config, emoticon functionality will be disabled. Also, if you pass gif config
          as null, GIF functionality will be disabled.
         */
        mEmoticonGIFKeyboardFragment = EmoticonGIFKeyboardFragment
                .getNewInstance(findViewById(R.id.keyboard_container), emoticonConfig, gifConfig,stickerConfig);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.keyboard_container, mEmoticonGIFKeyboardFragment)
                .commit();
           // mEmoticonGIFKeyboardFragment.open();//Open the fragment by default while initializing.

        final ImageView toggleKeyboardEmoji = (ImageView) findViewById(R.id.emoji_open_close_btn);
        //Set smiley button to open/close the emoticon gif keyboard
        toggleKeyboardEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmoticonGIFKeyboardFragment.isOpen()){

                    mEmoticonGIFKeyboardFragment.toggle();
                    toggleKeyboardEmoji.setImageResource(R.drawable.ic_smiley);

                    if (inputMethodManager != null){
                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    }
                }else {
                    //Check if keyboard is open and close it if it is
                    if (inputMethodManager.isAcceptingText()){
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    }

                    toggleKeyboardEmoji.setImageResource(R.drawable.sp_ic_keyboard);
                    mEmoticonGIFKeyboardFragment.toggle();
                }
            }
        });



        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmoticonGIFKeyboardFragment.isOpen()) {
                    //Check if keyboard is open and close it if it is
                    if (inputMethodManager.isAcceptingText()){
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    }
                }
            }
        });

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    if (mEmoticonGIFKeyboardFragment.isOpen())
                        //Check if keyboard is open and close it if it is
                        if (inputMethodManager.isAcceptingText()){
                            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
                        }
            }
        });




        //Send button
        findViewById(R.id.emoji_send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.append(editText.getText());
                editText.setText("");
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mEmoticonGIFKeyboardFragment == null || !mEmoticonGIFKeyboardFragment.handleBackPressed())
            super.onBackPressed();
    }
}
