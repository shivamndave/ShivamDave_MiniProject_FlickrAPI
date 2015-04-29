package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;

import java.util.List;

public class FlickrGridAdapter extends BaseAdapter {
    protected Context _context;
    protected List<Image> _fImages;

    public FlickrGridAdapter(Context context, List<Image> fImages) {
        this._context = context;
        this._fImages = fImages;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(_context);
            gridView = inflater.inflate(R.layout.adapter_flickrgrid, null);
            AQuery aq = new AQuery(gridView);
            String imgUrl = _fImages.get(position).get_image();
            aq.id(R.id.grid_item_image).image(imgUrl);
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
