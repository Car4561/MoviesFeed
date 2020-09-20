package com.carlosllerenatest.moviesfeed.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.carlosllerenatest.moviesfeed.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.internal.fuseable.HasUpstreamObservableSource;

public class AdapterMovie extends RecyclerView.Adapter<AdapterMovie.MovieItemViewHolder> {

    public  List<ViewModel> viewModels;

    public AdapterMovie(List<ViewModel> viewModel) {
        this.viewModels =  viewModel;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieItemViewHolder((LinearLayout)itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        holder.tvTittle.setText(viewModels.get(position).getName());
        holder.tvCountry.setText(viewModels.get(position).getCountry());

    }

    @Override
    public int getItemCount() {
        return viewModels.size();
    }

    public static class MovieItemViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.tvTitle)
        public TextView tvTittle;

        @BindView(R.id.tvCountry)
        public TextView tvCountry;

        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


}




