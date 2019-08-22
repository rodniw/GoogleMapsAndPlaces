package dev.rodni.ru.googlemapsandplaces.ui.main.avaterpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import dev.rodni.ru.googlemapsandplaces.R;

/*
NOTE: This fragment has its setter method by which its possible to provide dependencies.
 */
public class ImageListRecyclerAdapter extends RecyclerView.Adapter<ImageListRecyclerAdapter.ViewHolder>{

    private List<Integer> imagesList;
    private ImageListRecyclerClickListener imageListRecyclerClickListener;
    private Context context;

    /*public ImageListRecyclerAdapter(Context context, ArrayList<Integer> images, ImageListRecyclerClickListener imageListRecyclerClickListener) {
        context = context;
        imagesList = images;
        imageListRecyclerClickListener = imageListRecyclerClickListener;
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, imageListRecyclerClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.cartman_cop)
                .error(R.drawable.cartman_cop);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(imagesList.get(position))
                .into((holder).image);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void setImageListRecycler(Context context, ArrayList<Integer> imagesList, ImageListRecyclerClickListener imageListRecyclerClickListener){
        this.context = context;
        this.imagesList = imagesList;
        this.imageListRecyclerClickListener = imageListRecyclerClickListener;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        ImageView image;
        ImageListRecyclerClickListener mClickListener;

        public ViewHolder(View itemView, ImageListRecyclerClickListener clickListener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            mClickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onImageSelected(getAdapterPosition());
        }
    }

    public interface ImageListRecyclerClickListener{
        void onImageSelected(int position);
    }
}
