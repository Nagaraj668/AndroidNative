package com.basic.androidnativeconcepts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basic.androidnativeconcepts.adapter.BooksAdapter;
import com.basic.androidnativeconcepts.listener.ItemClickListener;
import com.basic.androidnativeconcepts.model.Book;
import com.basic.androidnativeconcepts.sqlite.SQLiteHelper;

import java.util.List;

public class SQLiteActivity extends AppCompatActivity implements ItemClickListener {

    private EditText nameEditText;
    private SQLiteHelper sqLiteHelper;
    private RecyclerView recyclerView;
    private BooksAdapter booksAdapter;
    private List<Book> bookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        // create sqlite instance
        sqLiteHelper = new SQLiteHelper(this);

        nameEditText= (EditText) findViewById(R.id.name);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        bookList = sqLiteHelper.getAllBooks();
        booksAdapter = new BooksAdapter(bookList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(booksAdapter);
    }

    public void addBook(View view) {
        String name = nameEditText.getText().toString();
        Book book = new Book();

        int bookId = sqLiteHelper.getBooksCount() + 1;
        book.setBookName(name);
        book.setBookId(bookId);

        if (sqLiteHelper.addBook(book)) {
            Toast.makeText(getApplicationContext(), "Book added", Toast.LENGTH_SHORT).show();
            bookList.add(book);
            booksAdapter.setBookList(bookList);
            booksAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "Book not added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getApplicationContext(), ""+ position, Toast.LENGTH_SHORT).show();
    }
}