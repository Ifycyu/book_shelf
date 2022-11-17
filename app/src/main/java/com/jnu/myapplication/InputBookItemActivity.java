package com.jnu.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);




        String title;
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
            Toast.makeText(this,title,Toast.LENGTH_SHORT).show();
        }



        Button button = findViewById(R.id.button_ok);
        int finalOrder = Order;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                int year,month,isbn;

                String book_title_edit_text_string = book_title_edit_text.getText().toString();
                if("".equals(book_title_edit_text_string))
                    book_title_edit_text_string = "Unnamed";
                bundle.putString("title",book_title_edit_text_string);

                String book_author_edit_text_string = book_author_edit_text.getText().toString();
                if("".equals(book_author_edit_text_string))
                    book_author_edit_text_string = "Unnamed";
                bundle.putString("author",book_author_edit_text.getText().toString());

                String book_translator_edit_text_string = book_translator_edit_text.getText().toString();
                if("".equals(book_translator_edit_text_string))
                    book_translator_edit_text_string = "Unnamed";
                bundle.putString("translator",book_translator_edit_text.getText().toString());

                String book_publisher_edit_text_string = book_publisher_edit_text.getText().toString();
                if("".equals(book_publisher_edit_text_string))
                    book_publisher_edit_text_string = "Unnamed";
                bundle.putString("publisher",book_publisher_edit_text.getText().toString());
//
                String book_pubyear_edit_text_string = book_pubyear_edit_text.getText().toString();
                if( "".equals(book_pubyear_edit_text_string))
                    year=1999;
                else
                    year = Integer.parseInt(book_pubyear_edit_text_string);
                bundle.putInt("year",year);
//
                String book_pubmonth_edit_text_string = book_pubmonth_edit_text.getText().toString();
                if( "".equals(book_pubmonth_edit_text_string))
                    month=1;
                else
                    month = Integer.parseInt(book_pubmonth_edit_text_string);
                bundle.putInt("month",month);
//
                String book_isbn_edit_text_string = book_isbn_edit_text.getText().toString();
                if( "".equals(book_isbn_edit_text_string))
                    isbn=1234567890;
                else
                    isbn = Integer.parseInt(book_isbn_edit_text_string);
                bundle.putInt("isbn",isbn);

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
    }
}