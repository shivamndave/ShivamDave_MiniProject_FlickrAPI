package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

enum DownloaderState {IDLE, WORKING, NOT_INITIALIZED, FAILED, STABLE}

// GetData is the base of all the HTTP calls that we will be doing
public class GetData {
    public String LOG_TAG = "GetData";

    private String _dataURL;
    private String _dataRaw;
    private DownloaderState _currentDownloaderState;

    // Starting state that uses the dataURL input
    // and makes the DownLoaderState at a starting state
    // of idle
    public GetData(String dataURL) {
        this._dataURL = dataURL;
        this._currentDownloaderState = DownloaderState.IDLE;
    }

    // Setter for the dataURL variable
    public void setDataURL(String dataURL) {
        this._dataURL = dataURL;
    }

    // Resets the state of all the variables and the current
    // DownloaderState
    public void reset() {
        this._dataURL = null;
        this._dataRaw = null;
        this._currentDownloaderState = DownloaderState.IDLE;
    }

    // Executes the download from the URL, sets the DownloadState to stable
    // because it has connected to the URL.
    public void execute() {
        this._currentDownloaderState = DownloaderState.STABLE;
        GetJSONData dataParseTemp = new GetJSONData();
        dataParseTemp.execute(_dataURL);
    }

    // Getter for the dataRaw variable
    public String getDataRaw() {
        return _dataRaw;
    }

    // Getter for the current DownloaderState
    public DownloaderState getDownloaderState() {
        return _currentDownloaderState;
    }

    // GetJSONData does the bulk of the work, after executing it sets
    // _dataRaw to the dataWebStr passed in and will return all the
    // JSON code found on the page
    public class GetJSONData extends AsyncTask<String, Void, String> {
        protected void onPostExecute(String dataWebStr) {
            _dataRaw = dataWebStr;
            Log.v(LOG_TAG, "JSON Data:" + _dataRaw);
            if (_dataRaw == null) {
                if (_dataURL == null) {
                    Log.v(LOG_TAG, "DLST = not initialized");
                    _currentDownloaderState = DownloaderState.NOT_INITIALIZED;
                } else {
                    Log.v(LOG_TAG, "DLST = failed");
                    _currentDownloaderState = DownloaderState.FAILED;
                }
            } else {
                Log.v(LOG_TAG, "DLST = working");
                _currentDownloaderState = DownloaderState.WORKING;
            }
        }

        protected String doInBackground(String... params) {
            HttpURLConnection newConnection = null;
            BufferedReader bufferReader = null;

            if (params == null) {
                Log.v(LOG_TAG, "Params returned as null");
                return null;
            } else {
                try {
                    URL urlConnection = new URL(params[0]);

                    newConnection = (HttpsURLConnection) urlConnection.openConnection();
                    newConnection.setRequestMethod("GET");
                    newConnection.connect();

                    StringBuffer bufferString = new StringBuffer();

                    InputStream newInputStream = newConnection.getInputStream();
                    if (newInputStream == null) {
                        Log.v(LOG_TAG, "newInputStream is null");
                        return null;
                    }

                    bufferReader = new BufferedReader(new InputStreamReader(newInputStream));

                    // Not needed, but appends newLines in the tempLineReader based on
                    // the buffer reader
                    String tempLineReader;
                    while ((tempLineReader = bufferReader.readLine()) != null) {
                        bufferString.append(tempLineReader + "\n");
                    }
                    return bufferString.toString();
                } catch (IOException e) {
                    Log.v(LOG_TAG, "IOException hit: " + e.toString());
                    return null;
                } finally {
                    if (newConnection != null) {
                        Log.v(LOG_TAG, "Safely disconnect");
                        newConnection.disconnect();
                    }

                    if (bufferReader != null) {
                        try {
                            bufferReader.close();
                        } catch (final IOException e) {
                            Log.v(LOG_TAG, "IOException hit: " + e.toString());
                            return null;
                        }
                    }
                }
            }
        }
    }
}
