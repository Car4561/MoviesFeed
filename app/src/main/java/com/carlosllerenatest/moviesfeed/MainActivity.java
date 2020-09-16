package com.carlosllerenatest.moviesfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.carlosllerenatest.moviesfeed.movies.MoviesMVP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String Tag = MainActivity.class.getName();

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

    @BindView(R.id.activityRootView)
    LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @Override
    public void UpdateData(ViewModel viewModel) {

    }

    @Override
    public void showSnackbar(String s) {

    }
}