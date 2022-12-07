package com.jnu.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jnu.myapplication.data.BookItem;
import com.jnu.myapplication.data.BookJson;
import com.jnu.myapplication.data.DataDownloader;

import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.widget.Toolbar;
public class InputBookItemActivity extends AppCompatActivity{

    private Spinner readingStatusSpinner;
    private int readstatus=0;
    private int doubanscore=0;
    public static final int RESULT_CODE_SUCCESS = 666;
    String cover_url="";
    LinearLayout login;

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

//        getSupportActionBar().hide();//最上面取消
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//改成这样来隐藏原来的标题栏，而不是改动整体的主题为noactionbar
        tool_bar();
        book_data();
        search_isbn();
        click_cover();
        setReadingStatus();
        set_douban_score();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() != null){

            EditText book_isbn_edit_text = findViewById(R.id.book_isbn_edit_text);
//              DataDownloader dataDownloader = new DataDownloader();
            String book_isbn_edit_text_string = intentResult.getContents();
            if (book_isbn_edit_text_string.length()==13 || book_isbn_edit_text_string.length()==10)
            {
//                Toast.makeText(getApplicationContext(),
//                        "isbn="+book_isbn_edit_text_string+"&appKey=ae1718d4587744b0b79f940fbef69e77"
//                        ,Toast.LENGTH_SHORT).show();
//                String bookJsonData = dataDownloader.download("http://47.99.80.202:6066/openApi/getInfoByIsbn?isbn="+book_isbn_edit_text_string+"&appKey=ae1718d4587744b0b79f940fbef69e77");
////                        String bookJsonData = dataDownloader.download("http://47.99.80.202:6066/openApi/getInfoByIsbn?isbn=9787115461476&appKey=ae1718d4587744b0b79f940fbef69e77");
//                BookJson bookJson= dataDownloader.parsonJson(bookJsonData);
//                InputBookItemActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        set_edit_text(bookJson);
//                    }
//                });
                book_isbn_edit_text.setText(book_isbn_edit_text_string);

            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        "请扫描书本条形码",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),
                    "抱歉....你没有扫描任何东西！",Toast.LENGTH_SHORT).show();
        }
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
//                            String bookJsonData = dataDownloader.download("http://47.99.80.202:6066/openApi/getInfoByIsbn?isbn="+book_isbn_edit_text_string+"&appKey=ae1718d4587744b0b79f940fbef69e77");
                            String bookJsonData = dataDownloader.download("https://api.jike.xyz/situ/book/isbn/"+book_isbn_edit_text_string+"?apikey=14444.ac1e36a851666c2ae887aaaed19ad753.8330a20bc4cb468ea6cfee6a31d3248e");

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
            EditText book_notes_edit_text = findViewById(R.id.book_notes_edit_text);
            EditText book_labels_edit_text = findViewById(R.id.book_labels_edit_text);
            ImageView BookCover = findViewById(R.id.book_cover_image_view);


            book_title_edit_text.setText(bookJson.getBookName());
            book_author_edit_text.setText(bookJson.getAuthor());
            book_publisher_edit_text.setText(bookJson.getPress());
            book_pubyear_edit_text.setText(bookJson.getYear());
            book_pubmonth_edit_text.setText(bookJson.getMonth());
            book_translator_edit_text.setText(bookJson.getTranslator());
            book_notes_edit_text.setText(bookJson.getDescription());

            doubanscore = bookJson.getDoubanScore();
//            Bitmap image = requestWebPhotoBitmap(bookJson.getPic());
//            BookCover.setImageBitmap(image);
            Glide.with(InputBookItemActivity.this)
                    .load(bookJson.getPic())
                    .into(BookCover);
            cover_url = bookJson.getPic();

            RatingBar ratingBar = findViewById(R.id.rating);
            ratingBar.setRating((float) (doubanscore/20));
        }

    }
    private void tool_bar()
    {

        //搜索栏上面那条
        Toolbar toolbar = findViewById(R.id.toolbar_input);
        setSupportActionBar(toolbar);  //加载Toolbar控件
        //回到上一页
//        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.scan:
                        scan();
//                        Toast.makeText(InputBookItemActivity.this, "msg", Toast.LENGTH_SHORT).show();
                        //显示搜索栏

                        break;
                }
//                Toast.makeText(CommoditySearchActivity.this, msg, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    private void book_data()
    {

        String title;
        String author;
        String translator;
        String publisher;
        String year;
        String month;
        String isbn;
        String notes;
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
        cover_url= this.getIntent().getStringExtra("cover");
        isbn= this.getIntent().getStringExtra("isbn");

        readstatus = this.getIntent().getIntExtra("readstatus",0);
        doubanscore = this.getIntent().getIntExtra("doubanScore",50);
        notes  = this.getIntent().getStringExtra("notes");

//        Toast.makeText(this,title,Toast.LENGTH_SHORT).show();


        EditText book_title_edit_text = findViewById(R.id.book_title_edit_text);
        EditText book_author_edit_text = findViewById(R.id.book_author_edit_text);
        EditText book_translator_edit_text = findViewById(R.id.book_translator_edit_text);
        EditText book_publisher_edit_text = findViewById(R.id.book_publisher_edit_text);
        EditText book_pubyear_edit_text = findViewById(R.id.book_pubyear_edit_text);
        EditText book_pubmonth_edit_text = findViewById(R.id.book_pubmonth_edit_text);
        EditText book_isbn_edit_text = findViewById(R.id.book_isbn_edit_text);
        EditText book_notes_edit_text = findViewById(R.id.book_notes_edit_text);
        EditText book_labels_edit_text = findViewById(R.id.book_labels_edit_text);
        ImageView BookCover = findViewById(R.id.book_cover_image_view);

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
        if(null!=notes)
        {
            book_notes_edit_text.setText(notes);
        }
        if(null!=cover_url)
        {
            Glide.with(InputBookItemActivity.this)
                    .load(cover_url)
                    .into(BookCover);
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

                String book_notes_edit_text_string = book_notes_edit_text.getText().toString();
                bundle.putString("notes",book_notes_edit_text_string);

                bundle.putInt("readstatus", readstatus);
                bundle.putInt("doubanscore", doubanscore);


                bundle.putInt("Order", finalOrder);

                bundle.putString("cover", cover_url);

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
    private void scan()
    {
        IntentIntegrator intentIntegrator = new IntentIntegrator(InputBookItemActivity.this);


        intentIntegrator.setPrompt("音量+打开闪光灯");

        intentIntegrator.setBeepEnabled(true);

        intentIntegrator.setOrientationLocked(true);

        intentIntegrator.setCaptureActivity(Capture.class);

        intentIntegrator.initiateScan();

    }

    private void click_cover()
    {

        ImageView button = findViewById(R.id.book_cover_image_view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(InputBookItemActivity.this);
                login = (LinearLayout)getLayoutInflater().inflate(R.layout.book_cover_input, null);
                dialog.setTitle("图片url地址").setMessage("请输入图片url地址")
                        .setView(login);
                dialog.setPositiveButton("确定", new loginClick());
                dialog.setNegativeButton("退出", new exitClick());
//        dialog.setIcon(R.drawable.qq);

                dialog.create();
                dialog.show();
            }
        });


    }
    /*  输入对话框的“确定”按钮事件   */
    class loginClick implements  DialogInterface.OnClickListener
    {
        EditText txt;
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            txt = (EditText)login.findViewById(R.id.imageCover);

            ImageView BookCover = findViewById(R.id.book_cover_image_view);
            cover_url = txt.getText().toString();
            Glide.with(InputBookItemActivity.this)
                    .load(cover_url)
                    .into(BookCover);

            dialog.dismiss();
        }
    }
    /*  输入对话框的“退出”按钮事件   */
    class exitClick implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.dismiss();

        }
    }
    private void setReadingStatus() {
        readingStatusSpinner = (Spinner) findViewById(R.id.reading_status_spinner);
        ArrayAdapter<CharSequence> readingStatusArrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.reading_status_array, R.layout.spinner_item);
        readingStatusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        readingStatusSpinner.setAdapter(readingStatusArrayAdapter);
        readingStatusSpinner.setSelection(readstatus);
        readingStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                readstatus=i;
//                Log.i(TAG, "Click and set Reading status " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void set_douban_score()
    {
        RatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setRating((float) (doubanscore/20));
        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser)->{
            //当星级评分条的评分发生改变时触发该方法
            //动态改变图片的透明度，其中255是星级评分条的最大值
            //5颗星星就代表最大值255
            doubanscore = (int)(rating*20);

        });
    }
}