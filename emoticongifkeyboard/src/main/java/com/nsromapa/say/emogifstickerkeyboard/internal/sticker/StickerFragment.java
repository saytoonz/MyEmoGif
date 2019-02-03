/*
 * Copyright 2017 Keval Patel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.nsromapa.say.emogifstickerkeyboard.internal.sticker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nsromapa.say.R;


public final class StickerFragment extends Fragment implements StickerAdapter.ItemSelectListener {
    private Context mContext;
    private ViewFlipper mViewFlipper;
    private TextView mErrorTv;

    private StickerAdapter mStickerAdapter;
    private StickerSelectListener mStickerSelectListener;



    public StickerFragment() {
    }

    public static StickerFragment getNewInstance() {
        return new StickerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sticker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @SuppressWarnings("DanglingJavadoc")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewFlipper = view.findViewById(R.id.sticker_view_flipper);
       // mErrorTv = view.findViewById(R.id.sticker_error_textview);

        Integer[] stickers = {
                R.drawable.hoffman,
                R.drawable.hoff,
                R.drawable.hof,
                R.drawable.ho,
                R.drawable.peace_hand,
                R.drawable.smile,
                R.drawable.merry_christ,
                R.drawable.m_christ,
                R.drawable.bish_mchristmas,
                R.drawable.be_bright,
                R.drawable.h_new_year,
                R.drawable.h_newyera,
                R.drawable.best_wishes,
                R.drawable.gold_new_year,
                R.drawable.ah_wada,
                R.drawable.cup_cry,
                R.drawable.cup_smile,
                R.drawable.cup_laugh,
                R.drawable.cup_amaze,
                R.drawable.cup_love,
                R.drawable.cup_love_biscuit,
                R.drawable.cup_broken_heart,
                R.drawable.cup_two_love,
                R.drawable.cup_cry_pour,
                R.drawable.cup_angery,
                R.drawable.cup_hot,
                R.drawable.m_christ,
                R.drawable.cup_phone,
                R.drawable.mr_man,
                R.drawable.hrrrrr,
                R.drawable.cup_low_butt,
                R.drawable.cup_joyful,
                R.drawable.cup_wify,
                R.drawable.cup_viberating,
                R.drawable.cup_greet,
                R.drawable.cup_shake
        };

        mStickerAdapter = new StickerAdapter(stickers,getContext(),this);

        GridView stikerGrideView = view.findViewById(R.id.sticker_gridView);
        stikerGrideView.setColumnWidth(getResources().getInteger(R.integer.gif_recycler_view_span_size));
        stikerGrideView.setAdapter(mStickerAdapter);


        if (stickers.length != 0) {
            mViewFlipper.setDisplayedChild(0);
        } else {
            mViewFlipper.setDisplayedChild(1);
        }

    }

    @SuppressWarnings("ConstantConditions")
    public void setStickerSelectListener(@Nullable StickerSelectListener stickerSelectListener) {
        mStickerSelectListener = stickerSelectListener;
    }


    @Override
    public void OnListItemSelected(@NonNull int sticker) {
       if (mStickerSelectListener!=null)
           mStickerSelectListener.onStickerSelectListner(sticker);
    }
}
