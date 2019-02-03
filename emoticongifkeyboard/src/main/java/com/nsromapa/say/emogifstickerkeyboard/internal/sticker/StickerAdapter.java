package com.nsromapa.say.emogifstickerkeyboard.internal.sticker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nsromapa.say.R;

public class StickerAdapter extends BaseAdapter {

    private Integer[] stickers;
    private Context context;
    private LayoutInflater thisInflater;

    private ItemSelectListener mListener;

    public StickerAdapter(Integer[] stickers, Context context, ItemSelectListener listener) {
        this.stickers = stickers;
        this.context = context;
        this.thisInflater = LayoutInflater.from(context);
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return stickers.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View v = convertView;
        StickerAdapter.ViewHolder holder;
        if (v == null) {
            v = thisInflater.inflate(R.layout.item_sticker, parent, false);

            holder = new StickerAdapter.ViewHolder();
            holder.stickerIv = v.findViewById(R.id.sticker_iv);
            v.setTag(holder);
        } else {
            holder = (StickerAdapter.ViewHolder) v.getTag();
        }



        final int sticker = stickers[position];
        if (sticker != 0) {
            Glide.with(context)
                    .load(stickers[position])
                    .into(holder.stickerIv);

            holder.stickerIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.OnListItemSelected(sticker);
                }
            });
        }
        return v;
    }



    /**
     * Callback listener to get notify when item is clicked.
     */
    interface ItemSelectListener {

        void OnListItemSelected(@NonNull int sticker);
    }


    private class ViewHolder {

        /**
         * Image view to display GIFs.
         */
        private ImageView stickerIv;
    }
}
