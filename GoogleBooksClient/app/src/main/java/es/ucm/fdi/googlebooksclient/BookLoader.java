package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<BookInfo>> {
    final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    final String QUERY_PARAM = "q";
    final String PRINT_TYPE = "printType";

    public BookLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<BookInfo> loadInBackground() {
        return null;
    }

    public void onStartLoading(){
        forceLoad();
    }

    public String getBooksInfoJson(String queryString, String printType) {
        Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(PRINT_TYPE, printType)
                .build();

        try {
            URL requestURL = new URL(builtURI.toString());

            HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();

            InputStream is = conn.getInputStream();
            String contentAsString = convertIsToString(is);

            conn.disconnect();
            if (is != null){
                is.close();
            }
            return contentAsString;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertIsToString(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while((line = reader.readLine()) != null){
            builder.append(line +"\n");
        }
        if(builder.length() == 0){
            return null;
        }
        return builder.toString();
    }
}
