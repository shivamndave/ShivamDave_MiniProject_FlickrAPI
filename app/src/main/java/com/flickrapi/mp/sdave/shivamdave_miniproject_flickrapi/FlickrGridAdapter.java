package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.androidquery.AQuery;

import java.util.List;

public class FlickrGridAdapter extends BaseAdapter {
    protected Context _context;
    protected List<Image> _fImages;

    public FlickrGridAdapter(Context context, List<Image> fImages) {
        this._context = context;
        this._fImages = fImages;
    }

    // Creates the gridview by passing in an image from each position (out of 20)
    // then the Aquery aq is used to display it in the grid position
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(_context);
            gridView = inflater.inflate(R.layout.adapter_flickrgrid, null);
            AQuery aq = new AQuery(gridView);
            String imageUrl = _fImages.get(position).get_image();
            aq.id(R.id.grid_item_image).image(imageUrl);
            gridView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(_context, FullScreenModeActivity.class);
                    _context.startActivity(intent);
                }
            });
            return gridView;
        }
        return convertView;
    }


    @Override
    public int getCount() {
        return _fImages.size();
    }

    @Override
    public Object getItem(int position) {
        return _fImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
