package com.jnu.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jnu.myapplication.data.BookItem;
import com.jnu.myapplication.data.BookJson;
import com.jnu.myapplication.data.DataDownloader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import com.klinker.android.sliding.SlidingActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class DetailBookItemActivity extends SlidingActivity{

        public static String Intent_Book_ToEdit = "BOOKTOEDIT";
        private static final String TAG = "BookDetailActivity";
        private BookItem mBook;

        private TextView infoTitleTextView;
        private ImageView coverImageView;
        private TextView addtimeTextView;
        private RelativeLayout authorRelativeLayout;
        private TextView authorTextView;
        private RelativeLayout translatorRelativeLayout;
        private TextView translatorTextView;
        private RelativeLayout publisherRelativeLayout;
        private TextView publisherTextView;
        private RelativeLayout pubtimeRelativeLayout;
        private TextView pubtimeTextView;
        private RelativeLayout isbnRelativeLayout;
        private TextView isbnTextView;
        private RelativeLayout readingStatusRelativeLayout;
        private TextView readingStatusTextView;
        private RelativeLayout bookshelfRelativeLayout;
        private TextView bookshelfTextView;
        private RelativeLayout notesRelativeLayout;
        private TextView notesTextView;
        private RelativeLayout labelsRelativeLayout;
        private TextView labelsTextView;
        private RelativeLayout websiteRelativeLayout;
        private TextView websiteTextView;

//
//        @Override
//        public void init(Bundle savedInstanceState) {
//            // Instead of overriding onCreate(), we should override init().
//            // Intent will pass in savedInstanceState
//            Intent intent = getIntent();
//            mBook = (BookItem) intent.getSerializableExtra(Intent_Book_ToEdit);
//            setTitle(mBook.getTITLE());
//            setBookInfo();
//            setBookDetails();
//        }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_book_item);
//
//    }

    @Override
    public void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mBook = (BookItem) intent.getSerializableExtra(Intent_Book_ToEdit);
        setPrimaryColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
        setContent(R.layout.activity_detail_book_item);
        setBookInfo();
        setBookDetails();
    }


    private void setBookInfo() {
            infoTitleTextView = findViewById(R.id.book_info_title_bar_text_view);
            authorRelativeLayout = (RelativeLayout) findViewById(R.id.book_info_author_item);
            authorTextView = (TextView) findViewById(R.id.book_info_author_text_view);
            translatorRelativeLayout = (RelativeLayout) findViewById(R.id.book_info_translator_item);
            translatorTextView = (TextView) findViewById(R.id.book_info_translator_text_view);
            publisherRelativeLayout = (RelativeLayout) findViewById(R.id.book_info_publisher_item);
            publisherTextView = (TextView) findViewById(R.id.book_info_publisher_text_view);
            pubtimeRelativeLayout = (RelativeLayout) findViewById(R.id.book_info_pubtime_item);
            pubtimeTextView = (TextView) findViewById(R.id.book_info_pubtime_text_view);
            isbnRelativeLayout = (RelativeLayout) findViewById(R.id.book_info_isbn_item);
            isbnTextView = (TextView) findViewById(R.id.book_info_isbn_text_view);

            final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            String authors = mBook.getAUTHORS();
            infoTitleTextView.setText(mBook.getTITLE());
            if (authors.length()!=0) {
                authorTextView.setText(authors);
                authorRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ClipData clipData = ClipData.newPlainText(
                                getString(R.string.app_name),
                                authorTextView.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(
                                DetailBookItemActivity.this,
                                authorTextView.getText().toString()+"  已经复制到剪贴板！",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                authorRelativeLayout.setVisibility(View.GONE);
            }

            String translators = mBook.getTRANSLATORS();

            if (translators.length()!=0) {
                translatorTextView.setText(translators);
                translatorRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ClipData clipData = ClipData.newPlainText(
                                getString(R.string.app_name),
                                translatorTextView.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(
                                DetailBookItemActivity.this,
                                translatorTextView.getText().toString()+"  已经复制到剪贴板！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                translatorRelativeLayout.setVisibility(View.GONE);
            }


            String publishers = mBook.getPUBLISHER();
            if (publishers.length() != 0) {
                publisherTextView.setText(publishers);
                publisherRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ClipData clipData = ClipData.newPlainText(
                                getString(R.string.app_name),
                                publisherTextView.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(
                                DetailBookItemActivity.this,
                                publisherTextView.getText().toString()+"  已经复制到剪贴板！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                publisherRelativeLayout.setVisibility(View.GONE);
            }

//            Calendar calendar = mBook.getPubTime();

            String year = mBook.getYear();
            if (year.length()==0) {
                pubtimeRelativeLayout.setVisibility(View.GONE);
            } else {
                String month = mBook.getMonth();
                StringBuilder pubtime = new StringBuilder();
                pubtime.append(year);
                pubtime.append(" - ");
                pubtime.append(month);
                pubtimeTextView.setText(pubtime);
            }

            String bookisbn = mBook.getISBN();
            if (bookisbn!=null&&bookisbn.length() != 0) {
                isbnTextView.setText(bookisbn);
                isbnRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ClipData clipData = ClipData.newPlainText(
                                getString(R.string.app_name),
                                isbnTextView.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(
                                DetailBookItemActivity.this,
                                isbnTextView.getText().toString()+"  已经复制到剪贴板！",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                isbnRelativeLayout.setVisibility(View.GONE);
            }

        }

        private void setBookDetails() {
            readingStatusRelativeLayout = (RelativeLayout) findViewById(R.id.book_detail_reading_status_item);
            readingStatusTextView = (TextView) findViewById(R.id.book_detail_reading_status_text_view);
            notesRelativeLayout = (RelativeLayout) findViewById(R.id.book_detail_notes_item);
            notesTextView = (TextView) findViewById(R.id.book_detail_notes_text_view);
            labelsRelativeLayout = (RelativeLayout) findViewById(R.id.book_detail_labels_item);
            labelsTextView = (TextView) findViewById(R.id.book_detail_labels_text_view);


            String[] readingStatus = getResources().getStringArray(R.array.reading_status_array);
            readingStatusTextView.setText(readingStatus[mBook.getReadingStatus()]);


            String note = mBook.getNote();
            if (note!=null && note.length() != 0) {
                notesTextView.setText(note);
//                notesRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        Toast.makeText(
//                                        BookDetailActivity.this,
//                                        getResources().getString(R.string.note_edit_text_hint),
//                                        Toast.LENGTH_SHORT)
//                                .show();
//                        return true;
//                    }
//                });
            } else {
                notesRelativeLayout.setVisibility(View.GONE);
            }

//            List<UUID> labelID = mBook.getLabelID();
//            if (labelID.size() != 0) {
//                StringBuilder labelsTitle = new StringBuilder();
//                for (UUID id : labelID) {
//                    labelsTitle.append(LabelLab.get(this).getLabel(id).getTitle());
//                    labelsTitle.append(",");
//                }
//                labelsTitle.deleteCharAt(labelsTitle.length() - 1);
//                labelsTextView.setText(labelsTitle);
//                labelsRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        Toast.makeText(
//                                        BookDetailActivity.this,
//                                        getResources().getString(R.string.book_detail_labels_image_view),
//                                        Toast.LENGTH_SHORT)
//                                .show();
//                        return true;
//                    }
//                });
//
//            } else {
//                labelsRelativeLayout.setVisibility(View.GONE);
//            }
//

        }
    }