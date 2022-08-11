package com.activity.fakeapi.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.activity.fakeapi.Adapter.PhotoAdapter;
import com.activity.fakeapi.Model.PhotoModel;
import com.activity.fakeapi.ViewModel.PhotoViewModel;
import com.activity.fakeapi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    LinearLayoutManager layoutManager;
    PhotoAdapter photoAdapter;
    List<PhotoModel> photoList = new ArrayList<>();
    PhotoViewModel photoViewModel;
    public  static  int itemsCount = 11, based = 0;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private boolean loading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = ActivityMainBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot());

        layoutManager = new LinearLayoutManager( this );
        binding.recyclerView.setLayoutManager(layoutManager);
        photoAdapter = new PhotoAdapter( photoList);
        binding.recyclerView.setAdapter(photoAdapter);

        photoViewModel = new ViewModelProvider( this ).get( PhotoViewModel.class );
        photoViewModel.getPhotoList().observe( this, new Observer<List<PhotoModel>>() {
            @Override
            public void onChanged(List<PhotoModel> photoModels) {
                photoList = photoModels;
                getPhoto(photoModels);

                binding.recyclerView.addOnScrollListener( new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy ) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = photoAdapter.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if(!loading){
                            if((visibleItemCount + pastVisibleItems) == totalItemCount)
                            {
                                based = itemsCount-1;
                                itemsCount += 10;
                                getPhoto( photoModels );
                            }
                        }

                    }

                } );

                binding.swipeRefresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (based != 0 || itemsCount != 11) {
                            based -= 10;
                            itemsCount -= 10;
                            getPhoto( photoModels );
                        }
                        if(based == 0  ){
                            toastMessage();
                        }
                        binding.swipeRefresh.setRefreshing(false);
                    }
                } );
            }
        } );
        photoViewModel.makeApiCall();
    }

    private void toastMessage() {
        Toast.makeText( this,"You've reached the peak.",Toast.LENGTH_SHORT ).show();
    }

    private void getPhoto(List<PhotoModel> photoModels) {
        loading = true;
        binding.progressBar.setVisibility( View.VISIBLE );
        photoAdapter.updatePhotoList( photoModels.subList( based,itemsCount ) );
        if(itemsCount == photoModels.size()){
            Toast.makeText( this,"You've reached the bottom.",Toast.LENGTH_LONG ).show();
        }else {
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    photoAdapter.updatePhotoList( photoModels.subList( based,itemsCount ) );
                    loading = false;
                    binding.progressBar.setVisibility( View.GONE );
                }
            }, 1000 );
        }

    }

}