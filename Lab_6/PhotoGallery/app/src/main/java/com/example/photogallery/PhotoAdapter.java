package com.example.photogallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photogallery.db.PhotosDao;
import com.example.photogallery.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> ph;
    private ImageView img;
    private TextView img_tv;
    Context context;
    PhotosDao photosD;

    public PhotoAdapter(List<Photo> photos, Context contxt, PhotosDao DAO) {
        ph = photos;
        context = contxt;
        photosD = DAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String txt = ph.get(position).getTitle();
        img_tv.setText(txt);
        Picasso.with(context).load(ph.get(position).getUrl_s()).into(img);
    }

    @Override
    public int getItemCount() {
        return ph.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageV);
            img_tv = itemView.findViewById(R.id.textV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getLayoutPosition();
                    Photo photo = ph.get(id);
                    photosD.insertPhoto(photo);
                }
            });
        }
    }
}

