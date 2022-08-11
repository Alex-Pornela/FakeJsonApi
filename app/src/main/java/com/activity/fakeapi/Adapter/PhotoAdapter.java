package com.activity.fakeapi.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.activity.fakeapi.Model.PhotoModel;
import com.activity.fakeapi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.myViewHolder> {

    private List<PhotoModel> photoList;


    public PhotoAdapter(List<PhotoModel> photoList) {
        this.photoList = photoList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updatePhotoList(List<PhotoModel> list){
        this.photoList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_item,parent,false );
        return new myViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.title.setText( photoList.get( position ).getId() );
        Picasso.get( )
                .load( photoList.get( position ).getThumbnailUrl())
                .into( holder.thumbnailUrl );
    }

    @Override
    public int getItemCount() {
        if(this.photoList!=null){
            return photoList.size();
        }else
            return 0;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailUrl;
        TextView title;

        public myViewHolder(@NonNull View itemView) {
            super( itemView );
            thumbnailUrl = itemView.findViewById( R.id.photo );
            title = itemView.findViewById( R.id.tvTitle );

        }
    }
}
