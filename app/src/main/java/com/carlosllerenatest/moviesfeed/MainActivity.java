package com.carlosllerenatest.moviesfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.carlosllerenatest.moviesfeed.movies.AdapterMovie;
import com.carlosllerenatest.moviesfeed.movies.MoviesMVP;
import com.carlosllerenatest.moviesfeed.movies.ViewModel;
import com.carlosllerenatest.moviesfeed.root.App;
import com.google.android.material.snackbar.Snackbar;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;

    @BindView(R.id.activityRootView)
    LinearLayout root;

    @Inject
    MoviesMVP.Presenter presenter;

    private AdapterMovie adapterMovie;

    private List<ViewModel> resultList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((App) getApplication()).getComponent().inject(this);
        adapterMovie = new AdapterMovie(resultList);
        rvMovies.setAdapter(adapterMovie);
        rvMovies.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        rvMovies.setItemAnimator(new DefaultItemAnimator());
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsuscribe();
        resultList.clear();
        adapterMovie.notifyDataSetChanged();
    }


    @Override
    public void updateData(ViewModel viewModel) {
        resultList.add(viewModel);

        adapterMovie.notifyItemInserted(resultList.size() - 1 );
        Log.d(TAG,"Información nueva: "+viewModel.getCountry());
        Log.d(TAG,"Información nueva: "+viewModel.getName());
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(root,message,Snackbar.LENGTH_SHORT).show();
    }
}