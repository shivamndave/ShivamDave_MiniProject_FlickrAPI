package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// Class that extends GetData to take the JSON gotten from GetData
// and parse that into the images data type
public class GetFlickrJSONData extends GetData {
    public String LOG_TAG = "GetFlickrJSONData";

    public List<Image> _images;
    private Uri endpointURI;
    private String FLICKR_URL = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    public GetFlickrJSONData() {
        super(null);
        createURI();
        _images = new ArrayList<Image>();
    }

    public void execute() {
        super.setDataURL(endpointURI.toString());
        DownloadJSONData jsonDataDownloader = new DownloadJSONData();
        Log.e(LOG_TAG, "Endpoint URI: " + endpointURI.toString());
        jsonDataDownloader.execute(endpointURI.toString());
    }

    // Makes the FLICKR_URL string a uri
    // if its null, it returns a false,
    // otherwise a true
    public boolean createURI() {
        endpointURI = Uri.parse(FLICKR_URL);
        if (endpointURI == null) {
            Log.v(LOG_TAG, "endPointURI is null");
            return false;
        } else {
            Log.v(LOG_TAG, "endPointURI is: " + FLICKR_URL);
            return true;
        }
    }

    // Assembles the list of pictures that hold the title, link, and image
    // of each pic. Also logs the list afterwards. This List of images
    // will be used to be fed into our adapter
    public void createResult() {
        if (getDownloaderState() != DownloaderState.WORKING) {
            Log.e(LOG_TAG, "DownloaderState = NOT WORKING");
        }

        final String flickrItems = "items";
        final String flickrTitle = "title";
        final String flickrLink = "link";
        final String flickrImageUrl = "m";
        final String flickrMedia = "media";

        try {
            JSONObject jsonData = new JSONObject(getDataRaw());
            JSONArray itemsArr = jsonData.getJSONArray(flickrItems);
            for (int i = 0; i < itemsArr.length(); i++) {
                JSONObject jsonImage = itemsArr.getJSONObject(i);
                String jsonTitle = jsonImage.getString(flickrTitle);
                String jsonLink = jsonImage.getString(flickrLink);

                JSONObject jsonMedia = jsonImage.getJSONObject(flickrMedia);
                String imageUrl = jsonMedia.getString(flickrImageUrl);

                Image makeImg = new Image(jsonTitle, jsonLink, imageUrl);
                this._images.add(makeImg);
            }
            int i = 0;
            for (Image oneImg : _images) {
                i++;
                Log.v(LOG_TAG, "Image" + "(" + Integer.toString(i) + "): " + oneImg.toString());
            }
        } catch (JSONException err) {
            err.printStackTrace();
            Log.e(LOG_TAG, "JSON EXECPTION ERROR: " + err.toString());
        }
    }

    // Returns the imageList later, when we have
    // to pass it to the adapter
    public List<Image> getImages() {
        return _images;
    }

    public class DownloadJSONData extends GetJSONData {

        // After executing the _image list is created
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            createResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] paramsArray = {endpointURI.toString()};
            return super.doInBackground(paramsArray);
        }
    }
}