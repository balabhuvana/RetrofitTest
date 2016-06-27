package com.example.retrofittest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import java.util.*;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Callback;



public class MainActivity extends AppCompatActivity {

    private ListView lstView = null;
    private TextView tvTest = null;
    public static Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        lstView = (ListView) findViewById(R.id.lstView);
        tvTest = (TextView) findViewById(R.id.tvTest);
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://demo0214632.mockable.io")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        HttpRequestHandler  httpRequestHandler = retrofit.create(HttpRequestHandler.class);
        Call<List<Book>> call = httpRequestHandler.listBooks();
        call.enqueue(new Callback<List<Book>> () {

            public void onResponse(Call<List<Book>> call, Response <List<Book>> response) {
                Toast.makeText(MainActivity.context, "within onResponse", Toast.LENGTH_SHORT).show();
                List <Book> bookList = response.body();
                String bookNames[] = new String[bookList.size()];
                for(int i = 0; i < bookList.size(); i++) {
                    bookNames[i] = bookList.get(i).getBookName();
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.context, android.R.layout.simple_list_item_1, bookNames);
                lstView.setAdapter(arrayAdapter);
            }

            public void onFailure(Call <List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.context, "within onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                tvTest.setText(t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
