package es.ucm.fdi.googlebooksclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BooksResultListAdapter extends RecyclerView.Adapter<BooksResultListAdapter.BookViewHolder> implements View.OnClickListener {
    private LayoutInflater mInflater;
    private ArrayList<BookInfo> mBooksData;
    private Context context;

    public BooksResultListAdapter(Context context, List<BookInfo> bookList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        setBooksData(bookList);
    }

    public void setBooksData(List<BookInfo> data) {
        mBooksData = (ArrayList<BookInfo>) data;
    }


    @NonNull
    @Override
    public BooksResultListAdapter.BookViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.results, parent, false);
        return new BookViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BooksResultListAdapter.BookViewHolder holder, int position) {
        String title = mBooksData.get(position).getTitle();
        String author = mBooksData.get(position).getAuthor();
        String url = mBooksData.get(position).getLink().toString();

        holder.wordItemView1.setText(title);
        holder.wordItemView2.setText(author);
        holder.wordItemView3.setText(url);
    }

    @Override
    public int getItemCount() {
        return mBooksData.size();
    }

    @Override
    public void onClick(View v) {
    }

    class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView wordItemView1;
        public TextView wordItemView2;
        public TextView wordItemView3;
        private BooksResultListAdapter mAdapter;

        public BookViewHolder(View itemView, BooksResultListAdapter adapter) {
            super(itemView);
            wordItemView1 = itemView.findViewById(R.id.textView2);
            wordItemView2 = itemView.findViewById(R.id.textView3);
            wordItemView3 = itemView.findViewById(R.id.textView4);

            this.mAdapter = adapter;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = wordItemView3.getText().toString();
                    if (url != null && !url.isEmpty()){
                        Intent intentWeb = new Intent();
                        intentWeb.setAction(Intent.ACTION_VIEW);
                        intentWeb.setData(Uri.parse(url));
                        context.startActivity(intentWeb);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}