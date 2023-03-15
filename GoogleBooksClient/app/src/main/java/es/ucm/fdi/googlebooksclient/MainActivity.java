package es.ucm.fdi.googlebooksclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BookLoaderCallbacks bookLoaderCallbacks;
    private BooksResultListAdapter booksResultListAdapter;
    final int BOOK_LOADER_ID = 1;
    private EditText author;
    private EditText title;
    private RadioGroup options;
    private Button button;
    private RecyclerView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        bookLoaderCallbacks = new BookLoaderCallbacks(this, this);

        options = findViewById(R.id.radioGroup);
        author = findViewById(R.id.editText);
        title = findViewById(R.id.editText1);
        button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            searchBooks(view);
        });

        results = findViewById(R.id.results);
        booksResultListAdapter = new BooksResultListAdapter(this, new ArrayList<BookInfo>());
        results.setAdapter(booksResultListAdapter);
        results.setLayoutManager(new LinearLayoutManager(this));

        if(loaderManager.getLoader(BOOK_LOADER_ID) != null){
            loaderManager.initLoader(BOOK_LOADER_ID, null, bookLoaderCallbacks);
        }
    }

    private void searchBooks(View view){
        Bundle queryBundle = new Bundle();

        String queryString, printType;

        int optSelected = options.getCheckedRadioButtonId();
        if(optSelected == R.id.radioButton3){
            printType = "books";
        }
        else if(optSelected == R.id.radioButton4){
            printType = "magazines";
        }
        else {
            printType = "all";
        }

        String[] words = author.getText().toString().split(" ");
        String authorJoined = String.join("+", words);

        String[] words1 = title.getText().toString().split(" ");
        String titleJoined = String.join("+", words1);

        queryString = authorJoined + "+" + titleJoined;

        queryBundle.putString(BookLoaderCallbacks.EXTRA_QUERY, queryString);
        queryBundle.putString(BookLoaderCallbacks.EXTRA_PRINT_TYPE, printType);
        LoaderManager.getInstance(this)
                .restartLoader(BOOK_LOADER_ID, queryBundle, bookLoaderCallbacks);
    }

    public void setBooksInfo(List<BookInfo> data){
        booksResultListAdapter.setBooksData(data);
        booksResultListAdapter.notifyDataSetChanged();
    }


}