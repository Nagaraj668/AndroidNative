package com.basic.androidnativeconcepts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basic.androidnativeconcepts.R;
import com.basic.androidnativeconcepts.listener.ItemClickListener;
import com.basic.androidnativeconcepts.model.Book;

import java.util.List;

/**
 * Created by NAGARAJ SRJ on 8/13/2017.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    List<Book> bookList;
    Context context;
    ItemClickListener itemClickListener;

    public BooksAdapter(List<Book> bookList, Context context, ItemClickListener itemClickListener) {
        this.bookList = bookList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.idTextView.setText(String.valueOf(book.getBookId()));
        holder.bookNameTextView.setText(book.getBookName());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookNameTextView, idTextView;
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(getAdapterPosition());
                }
            });

            bookNameTextView = itemView.findViewById(R.id.book_name);
            idTextView= itemView.findViewById(R.id.book_id);
        }
    }

}
