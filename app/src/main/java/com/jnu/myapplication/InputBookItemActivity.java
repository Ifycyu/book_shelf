package com.jnu.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jnu.myapplication.data.BookJson;
import com.jnu.myapplication.data.DataDownloader;

import java.util.List;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);




        String title;
        String author;
        String translator;
        String publisher;
        String year;
        String month;
        String isbn;
        int Order;
//        try {
//            Bundle bundle=getIntent().getExtras();
//            title = bundle.getString("title");
//            Order = bundle.getInt("Order");
//        }catch(Exception e)
//        {title = "";
//        }
        title = this.getIntent().getStringExtra("title");
        Order = this.getIntent().getIntExtra("Order",0);
        author= this.getIntent().getStringExtra("author");
        translator= this.getIntent().getStringExtra("translator");
        publisher= this.getIntent().getStringExtra("publisher");
        year= this.getIntent().getStringExtra("year");
        month= this.getIntent().getStringExtra("month");
        isbn= this.getIntent().getStringExtra("isbn");
//        Toast.makeText(this,title,Toast.LENGTH_SHORT).show();


        EditText book_title_edit_text = findViewById(R.id.book_title_edit_text);
        EditText book_author_edit_text = findViewById(R.id.book_author_edit_text);
        EditText book_translator_edit_text = findViewById(R.id.book_translator_edit_text);
        EditText book_publisher_edit_text = findViewById(R.id.book_publisher_edit_text);
        EditText book_pubyear_edit_text = findViewById(R.id.book_pubyear_edit_text);
        EditText book_pubmonth_edit_text = findViewById(R.id.book_pubmonth_edit_text);
        EditText book_isbn_edit_text = findViewById(R.id.book_isbn_edit_text);

        if(null!=title)
        {
            book_title_edit_text.setText(title);
        }
        if(null!=author)
        {
            book_author_edit_text.setText(author);
        }
        if(null!=translator)
        {
            book_translator_edit_text.setText(translator);
        }
        if(null!=publisher)
        {
            book_publisher_edit_text.setText(publisher);
        }
        if(null!=year)
        {
            book_pubyear_edit_text.setText(year);
        }
        if(null!=month)
        {
            book_pubmonth_edit_text.setText(month);
        }
        if(null!=isbn)
        {
            book_isbn_edit_text.setText(isbn);
        }

        Button button = findViewById(R.id.button_ok);
        int finalOrder = Order;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                String book_title_edit_text_string = book_title_edit_text.getText().toString();
                if("".equals(book_title_edit_text_string))
                    book_title_edit_text_string = "Unnamed";
                bundle.putString("title",book_title_edit_text_string);

                String book_author_edit_text_string = book_author_edit_text.getText().toString();
//                if("".equals(book_author_edit_text_string))
//                    book_author_edit_text_string = "Unnamed";
                bundle.putString("author",book_author_edit_text_string);

                String book_translator_edit_text_string = book_translator_edit_text.getText().toString();
//                if("".equals(book_translator_edit_text_string))
//                    book_translator_edit_text_string = "Unnamed";
                bundle.putString("translator",book_translator_edit_text.getText().toString());

                String book_publisher_edit_text_string = book_publisher_edit_text.getText().toString();
//                if("".equals(book_publisher_edit_text_string))
//                    book_publisher_edit_text_string = "Unnamed";
                bundle.putString("publisher",book_publisher_edit_text.getText().toString());
//
                String book_pubyear_edit_text_string = book_pubyear_edit_text.getText().toString();
                bundle.putString("year",book_pubyear_edit_text_string);

                String book_pubmonth_edit_text_string = book_pubmonth_edit_text.getText().toString();
                bundle.putString("month",book_pubmonth_edit_text_string);

                String book_isbn_edit_text_string = book_isbn_edit_text.getText().toString();
                bundle.putString("isbn",book_isbn_edit_text_string);

                bundle.putInt("Order", finalOrder);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                InputBookItemActivity.this.finish();
            }
        });
        Button button2 = findViewById(R.id.button_cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputBookItemActivity.this.finish();
            }
        });
        search_isbn();
    }
    public void search_isbn(){
        EditText book_isbn_edit_text = findViewById(R.id.book_isbn_edit_text);

        Button button = findViewById(R.id.button_isbn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        DataDownloader dataDownloader = new DataDownloader();
                        String book_isbn_edit_text_string = book_isbn_edit_text.getText().toString();
                        if (book_isbn_edit_text_string.length()==13 || book_isbn_edit_text_string.length()==10)
                        {
                            String bookJsonData = dataDownloader.download("http://47.99.80.202:6066/openApi/getInfoByIsbn?isbn="+book_isbn_edit_text_string+"&appKey=ae1718d4587744b0b79f940fbef69e77");
//                        String bookJsonData = dataDownloader.download("http://47.99.80.202:6066/openApi/getInfoByIsbn?isbn=9787115461476&appKey=ae1718d4587744b0b79f940fbef69e77");
                            BookJson bookJson= dataDownloader.parsonJson(bookJsonData);
                            InputBookItemActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    set_edit_text(bookJson);
                                }
                            });
                        }
                        else
                        {
                            InputBookItemActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(InputBookItemActivity.this, "ISBN为10或13位", Toast.LENGTH_SHORT).show();//不能直接用this
                                }
                            });
                        }
                    }
                }).start();

            }
        });


    }
    private void set_edit_text(BookJson bookJson)
    {
        if (bookJson.isBlank())
        {
            Toast.makeText(InputBookItemActivity.this, "ISBN Error", Toast.LENGTH_LONG).show();//不能直接用this
        }else
        {
            EditText book_title_edit_text = findViewById(R.id.book_title_edit_text);
            EditText book_author_edit_text = findViewById(R.id.book_author_edit_text);
            EditText book_translator_edit_text = findViewById(R.id.book_translator_edit_text);
            EditText book_publisher_edit_text = findViewById(R.id.book_publisher_edit_text);
            EditText book_pubyear_edit_text = findViewById(R.id.book_pubyear_edit_text);
            EditText book_pubmonth_edit_text = findViewById(R.id.book_pubmonth_edit_text);
            book_title_edit_text.setText(bookJson.getBookName());
            book_author_edit_text.setText(bookJson.getAuthor());
            book_publisher_edit_text.setText(bookJson.getPress());
            book_pubyear_edit_text.setText(bookJson.getYear());
            book_pubmonth_edit_text.setText(bookJson.getMonth());
        }

    }
}