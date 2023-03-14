package es.ucm.fdi.googlebooksclient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookInfo {

    private String author, title;
    private URL link;

    public BookInfo(String author, String title, URL link){
        this.author = author;
        this.title = title;
        this.link = link;
    }

    static List<BookInfo> fromJsonResponse(String s) {
        try {
            JSONObject data = new JSONObject(s);
            String nResults = data.getString("totalItems");
            int nRes = Integer.parseInt(nResults);
            JSONArray items = data.getJSONArray("items");

            List<BookInfo> resultados = new ArrayList<>();

            // Loop through all the results
            for(int i = 0; i < nRes; i++){
                JSONObject it = items.getJSONObject(i);
                JSONObject volumeInfo = it.getJSONObject("volumeInfo");
                String url = it.getString("selfLink");
                URL link = new URL(url);

                //Get title
                String title = volumeInfo.getString("title");

                // Get all authors
                JSONArray arr = volumeInfo.getJSONArray("authors");
                String authors = "";
                for(int j = 0; j < arr.length(); j++){
                    authors += arr.getString(j) + ", ";
                }
                authors = authors.substring(0, authors.length() - 2);

                BookInfo result = new BookInfo(authors, title, link);
                resultados.add(result);
            }
            return resultados;

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public URL getLink() {
        return link;
    }
}
