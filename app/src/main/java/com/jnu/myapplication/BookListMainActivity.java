package com.jnu.myapplication;

import static com.jnu.myapplication.R.drawable.*;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jnu.myapplication.data.BookItem;
import com.jnu.myapplication.data.BookJson;
import com.jnu.myapplication.data.BookShelf;
import com.jnu.myapplication.data.DataDownloader;
import com.jnu.myapplication.data.DataSaver;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    private Spinner mSpinner;
    public static final int menu_id_add = 1;
    public static final int menu_id_delete = 2;
    public static final int menu_id_edit = 3;
    private RecyclerView recyclerViewMain;
    private ArrayList<BookItem> bookItems;
    private ArrayList<BookItem> mBooks;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    private MainRecycleViewAdapter SearchAdapter;



    private SearchView mSearchView;
    // 返回activity
    private ActivityResultLauncher<Intent> addDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent =result.getData();
                    if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String author = bundle.getString("author");
                        String translator = bundle.getString("translator");
                        String publisher = bundle.getString("publisher");
                        String year = bundle.getString("year");
                        String month = bundle.getString("month");
                        String isbn = bundle.getString("isbn");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        bookItems.add(bookItems.size(),new BookItem(title,author,translator,publisher, year,month,isbn, book_no_name,(int)timestamp.getTime()));
                        new DataSaver().save(this,bookItems);


                        int new_book_position = mBooks.size();
                        mBooks.add(new_book_position,new BookItem(title,author,translator,publisher, year,month,isbn, book_no_name,(int)timestamp.getTime()));
                        mainRecycleViewAdapter.notifyItemInserted(new_book_position);//把新书放在最后
//                        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
//                        mSearchView = findViewById(R.id.search);
//                        mSearchView.setQuery("",false);
                    }
                }
            });
    private ActivityResultLauncher<Intent> editDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent =result.getData();
                    if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle = intent.getExtras();
                        String title = bundle.getString("title");
                        String author = bundle.getString("author");
                        String translator = bundle.getString("translator");
                        String publisher = bundle.getString("publisher");
                        String year= bundle.getString("year");
                        String month= bundle.getString("month");
                        String isbn = bundle.getString("isbn");
                        int Order = bundle.getInt("Order");
                        mBooks.get(Order).setTITLE(title);
                        mBooks.get(Order).setAUTHORS(author);
                        mBooks.get(Order).setTRANSLATORS(translator);
                        mBooks.get(Order).setPUBLISHER(publisher);
                        mBooks.get(Order).setYear(year);
                        mBooks.get(Order).setMonth(month);
                        mBooks.get(Order).setISBN(isbn);

                        int data_book_order = get_book_order(mBooks.get(Order).getBookId());
                        bookItems.get(data_book_order).setTITLE(title);
                        bookItems.get(data_book_order).setAUTHORS(author);
                        bookItems.get(data_book_order).setTRANSLATORS(translator);
                        bookItems.get(data_book_order).setPUBLISHER(publisher);
                        bookItems.get(data_book_order).setYear(year);
                        bookItems.get(data_book_order).setMonth(month);
                        bookItems.get(data_book_order).setISBN(isbn);
                        new DataSaver().save(this,bookItems);
                        updateUI(false,null);
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportActionBar().hide();//最上面取消
        setRecyclerView();//RecyclerView
        setFloatingActionButton();//悬浮按钮
        setDrawerLayout();//侧滑
        setBookShelfSpinner(1);
        initSearchView();



    }
    //
    private void setFloatingActionButton() {
        FloatingActionButton add_book_button = (FloatingActionButton) findViewById(R.id.add_book);
        add_book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(BookListMainActivity.this, "Add a new book", Toast.LENGTH_LONG).show();//不能直接用this
                Intent intent = new Intent(BookListMainActivity.this, InputBookItemActivity.class);
                addDataLauncher.launch(intent);
            }
        });
    }
    //
    private void setRecyclerView() {
        recyclerViewMain=findViewById(R.id.recycle_view_books);
        //布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        DataSaver dataSaver = new DataSaver();
        bookItems = dataSaver.Load(this);
        mBooks = dataSaver.Load(this);
        updateUI(false,null);
//        if(bookItems.size()==0)
//            bookItems.add(new BookItem("信息安全数学基础（第2版）", R.drawable.book_1));
//        bookItems.add(new BookItem("软件项目管理案例教程（第4版）", R.drawable.book_2));
//        bookItems.add(new BookItem("书", R.drawable.book_no_name));


        //设置数据接收渲染器
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);
    }
    //
    public ArrayList<BookItem> getListBooks(){
        return bookItems;
    }
    //
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //菜单menu的选项执行事件
        Intent intent;
        switch (item.getItemId())
        {
//            case menu_id_add:
//                Intent intent = new Intent(this, InputBookItemActivity.class);
//                addDataLauncher.launch(intent);
////                startActivity(intent);
//
//                /*
//                bookItems.add(item.getOrder(),new BookItem("new book", R.drawable.book_1));
//                mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
//                */
//                break;
            case menu_id_delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                bookItems.remove(get_book_order(mBooks.get(item.getOrder()).getBookId()));
                                new DataSaver().save(BookListMainActivity.this,bookItems);

                                mBooks.remove(item.getOrder());
                                updateUI(false,null);

                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
            case menu_id_edit:
                intent = new Intent(BookListMainActivity.this, InputBookItemActivity.class);
                Bundle bundle = new Bundle();

                String book_title_edit_text_title_string = mBooks.get(item.getOrder()).getTITLE();
                String book_title_edit_text_author_string = mBooks.get(item.getOrder()).getAUTHORS();
                String book_title_edit_text_translator_string = mBooks.get(item.getOrder()).getTRANSLATORS();
                String book_title_edit_text_publisher_string = mBooks.get(item.getOrder()).getPUBLISHER();
                String book_title_edit_text_year_string = mBooks.get(item.getOrder()).getYear();
                String book_title_edit_text_month_string = mBooks.get(item.getOrder()).getMonth();
                String book_title_edit_text_isbn_string = mBooks.get(item.getOrder()).getISBN();



                bundle.putString("title",book_title_edit_text_title_string);
                bundle.putString("author",book_title_edit_text_author_string);
                bundle.putString("translator",book_title_edit_text_translator_string);
                bundle.putString("publisher",book_title_edit_text_publisher_string);
                bundle.putString("isbn",book_title_edit_text_isbn_string);
                bundle.putString("year",book_title_edit_text_year_string);
                bundle.putString("month",book_title_edit_text_month_string);
                bundle.putInt("Order",item.getOrder());
                intent.putExtras(bundle);
                editDataLauncher.launch(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }




    //adapter重写三个方法
    // 在内部类设置viewholder类
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<BookItem>localDataset;

        //创建viewholder，针对每一个item生成一个viewholder
        //相当一个容器，里面的东西自定义
        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textTitle;
            private final ImageView imageView;
            private final TextView publisher_text;
            private final TextView publisher_time;
            private  final  ImageView starView;


            public TextView getPublisher_text() {
                return publisher_text;
            }

            public TextView getPublisher_time() {
                return publisher_time;
            }
            public TextView getTextTitle() {
                return textTitle;
            }

            public ImageView getImageView() {
                return imageView;
            }
            public ImageView getStarView()
            {return  starView;}

            public ViewHolder(View view) {
                super(view);
                //找到view
                imageView = view.findViewById(R.id.list_cover_image_view);
                textTitle = view.findViewById(R.id.list_title_text_view);
                publisher_text = view.findViewById(R.id.list_publisher_text_view);
                publisher_time = view.findViewById(R.id.list_pubtime_text_view);
                starView = view.findViewById(R.id.list_star);


                //holder的监听事件
                view.setOnCreateContextMenuListener(this);
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //监听事件的菜单选项样式，选项，item，显示信息
//                contextMenu.add(0,menu_id_add,getAdapterPosition(),"add"+getAdapterPosition());
                contextMenu.add(0, menu_id_delete,getAdapterPosition(),"delete");
                contextMenu.add(0, menu_id_edit,getAdapterPosition(),"edit");
            }
        }
        public MainRecycleViewAdapter(ArrayList<BookItem> dataset){
            localDataset=dataset;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            //提取出view 用在viewholder
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_booklist,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //holder设置数据
            holder.getTextTitle().setText(localDataset.get(position).getTITLE());//设置书名
            holder.getImageView().setImageResource(localDataset.get(position).getCoverResourceId());//设置图片
            holder.getPublisher_text().setText(localDataset.get(position).getPubText());//设置小字
            holder.getPublisher_time().setText(localDataset.get(position).getPubTime());//设置发表时间

            holder.getStarView().setOnClickListener(new View.OnClickListener() {  //设置点击事件
                @Override
                public void onClick(View v) {
                    localDataset.get(position).setStar(!localDataset.get(position).getStar());
                    mainRecycleViewAdapter.notifyDataSetChanged();

                }
            });

            if(localDataset.get(position).getStar())
                holder.getStarView().setImageResource(ic_baseline_star_24);
            else
                holder.getStarView().setImageResource(ic_baseline_star_border_24);

        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }

    private void setDrawerLayout() {
        DrawerLayout mDrawerLayout;

        mDrawerLayout = findViewById(R.id.layout_main);

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_setting:
                        startActivity(new Intent(BookListMainActivity.this, SettingActivity.class));
                        Toast.makeText(BookListMainActivity.this,"setting activity ",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        startActivity(new Intent(BookListMainActivity.this, AboutActivity.class));
                        Toast.makeText(BookListMainActivity.this,"About activity ",Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });

        ImageView setting = findViewById(R.id.main_toolbar_setting);
        setting.setOnClickListener(new View.OnClickListener() {  //设置点击事件
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void setBookShelfSpinner(int selection) {
        mSpinner = findViewById(R.id.toolbar_spinner);
        if(mSpinner == null){
            // for example, if searchView is expanded, mSpinner is null
            return;
        }
        BookShelf allBookShelf = new BookShelf();
        allBookShelf.setTitle("ALL");
        List<BookShelf> bookShelves =new ArrayList<BookShelf>();
        bookShelves.add(0, allBookShelf);
        ArrayAdapter<BookShelf> arrayAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_item_white, bookShelves);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_drop_down_white);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                updateUI(true, null);
//                Toast.makeText(BookListMainActivity.this,"input activity return",Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (selection >= 0 && selection < bookShelves.size()) {
            mSpinner.setSelection(selection);
        }
    }

    private void initSearchView() {
        mSearchView = findViewById(R.id.search);
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                // 当搜索框内容变化 ， 新的搜索数据清除，并更新
//                search_mData.clear();
//                find(newText);
//                SearchAdapter = new MainRecycleViewAdapter(search_mData);
//                /// 新的adapter进行数据更新
//                recyclerViewMain.setAdapter(SearchAdapter);
                updateUI(true,newText);
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //关闭搜索按钮的时候，设置显示默认页面
                recyclerViewMain.setAdapter(mainRecycleViewAdapter);
                return false;
            }
        });
    }
    private boolean check(String a,String b)
    {
        int  vis[] = new int[1000];//标记位置
        /// a 搜索框字符串 b标签字符串
        for (int i=0;i<a.length();++i)
        {
            char x = a.charAt(i);
            boolean flag = false;
            for (int j=0;j<b.length();++j)
            {
                char y = b.charAt(j);
                // 如果在标签之中该字符出现过，直接标记
                if(vis[j]==0 && x==y)
                {
                    vis[j]=1;
                    flag = true;
                    break;
                }
            }
            /// 未找到未标记的，说明不是合法的匹配
            if (!flag) return false;
        }
        return true;
    }
    private ArrayList<BookItem> find(String x)
    {
        ArrayList<BookItem> mBooks1 = new ArrayList<BookItem>();
        for (int i=0;i<bookItems.size();++i)
        {
            if (check(x,bookItems.get(i).getTITLE()))
            {
                mBooks1.add(bookItems.get(i));
                /// 找到的新的，作为更新
            }
        }
        return  mBooks1;
    }
    private void updateUI(boolean updateBooksList, @Nullable String keyword) {
        if (updateBooksList) {
            mBooks= find(keyword);

        }
            mainRecycleViewAdapter = new MainRecycleViewAdapter(mBooks);
            recyclerViewMain.setAdapter(mainRecycleViewAdapter);
            mainRecycleViewAdapter.notifyDataSetChanged();
    }

    private int get_book_order(int book_id)
    {
        for(int i=0;i<bookItems.size();i++)
            if(book_id == bookItems.get(i).getBookId())
                return  i;
            return -1;
    }
}