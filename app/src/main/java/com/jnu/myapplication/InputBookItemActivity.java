package com.jnu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

        EditText book_title_edit_text = findViewById(R.id.book_title_edit_text);


        Button button = findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title",book_title_edit_text.getText().toString());

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                InputBookItemActivity.this.finish();
            }
        });
    }
}