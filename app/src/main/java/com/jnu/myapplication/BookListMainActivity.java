package com.jnu.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.jnu.myapplication.data.BookItem;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    public static final int menu_id_add = 1;
    public static final int menu_id_delete = 2;
    private ArrayList<BookItem> bookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;

// 返回activity
    private ActivityResultLauncher<Intent> addDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
        if(null!=result){
            Intent intent =result.getData();
            if(result.getResultCode()==InputBookItemActivity.RESULT_CODE_SUCCESS)
            {
                Bundle bundle = intent.getExtras();
                String title = bundle.getString("title");
                bookItems.add(1,new BookItem(title, R.drawable.book_1));
                mainRecycleViewAdapter.notifyItemInserted(1);
                Toast.makeText(this,"input activity return",Toast.LENGTH_SHORT).show();
            }
        }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);
        //布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        bookItems =new ArrayList<BookItem>();


        bookItems.add(new BookItem("信息安全数学基础（第2版）", R.drawable.book_1));
        bookItems.add(new BookItem("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookItems.add(new BookItem("书", R.drawable.book_no_name));


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
        switch (item.getItemId())
        {
            case menu_id_add:
                Intent intent = new Intent(this, InputBookItemActivity.class);
                addDataLauncher.launch(intent);
//                startActivity(intent);

                /*
                bookItems.add(item.getOrder(),new BookItem("new book", R.drawable.book_1));
                mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                */
                break;
            case menu_id_delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookItems.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
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

            public ViewHolder(View view) {
                super(view);
                //找到view
                imageView = view.findViewById(R.id.list_cover_image_view);
                textTitle = view.findViewById(R.id.list_title_text_view);
                publisher_text = view.findViewById(R.id.list_publisher_text_view);
                publisher_time = view.findViewById(R.id.list_pubtime_text_view);
                //holder的监听事件
                view.setOnCreateContextMenuListener(this);
            }


            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //监听事件的菜单选项样式，选项，item，显示信息
                contextMenu.add(0,menu_id_add,getAdapterPosition(),"add"+getAdapterPosition());
                contextMenu.add(0, menu_id_delete,getAdapterPosition(),"delete"+getAdapterPosition());
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
            holder.getTextTitle().setText(localDataset.get(position).getTitle());//设置书名
            holder.getImageView().setImageResource(localDataset.get(position).getCoverResourceId());//设置图片
            holder.getPublisher_text().setText(localDataset.get(position).getPubText());//设置小字
            holder.getPublisher_time().setText(localDataset.get(position).getPubTime());//设置发表时间
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }

}