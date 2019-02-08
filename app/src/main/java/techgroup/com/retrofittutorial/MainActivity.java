package techgroup.com.retrofittutorial;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //  TextView Tv;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    RecyclerView mRecyclerView;
    PostRecyclerViewAdapter postRecyclerViewAdapter;
    List<PostAttributes> postArray;
    private static final String TAG = "MainActivity";
    Handler handler;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.RecyclerView);
        handler = new Handler();

        // Retrofit instantiation
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getPostArray();

        // Tv = (TextView) findViewById(R.id.TextView);
        postArray = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(MainActivity.this, mRecyclerView);
        postRecyclerViewAdapter.setPostList(postArray);
        Log.d(TAG, "setPostList: " + postArray);
        mRecyclerView.setAdapter(postRecyclerViewAdapter);
        postRecyclerViewAdapter.setOnLoadMoreListener(new PostRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                postArray.add(null);
                postRecyclerViewAdapter.notifyItemInserted(postArray.size() - 1);
                postArray.remove(postArray.size() - 1);
                postRecyclerViewAdapter.notifyItemRemoved(postArray.size());
                for (int i = 0; i < 15; i++) {
                    postArray.add(new PostAttributes(3, 2, "Just Added", "To my RecyclerView"));
                    postRecyclerViewAdapter.notifyItemInserted(postArray.size());
                }
                postRecyclerViewAdapter.setLoaded();
                Log.d(TAG, "onLoadMore: Loading");
            }
        });
    }

    private void getPostArray() {
        JsonPlaceHolderApi placeHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        final Call<List<PostAttributes>> post = placeHolderApi.getAllPost();

        post.enqueue(new Callback<List<PostAttributes>>() {
            @Override
            public void onResponse(Call<List<PostAttributes>> call, Response<List<PostAttributes>> response) {
                postArray = response.body();
                postRecyclerViewAdapter.setPostList(postArray);
                Log.d(TAG, "postArray Size: " + postArray.size());
            }

            @Override
            public void onFailure(Call<List<PostAttributes>> call, Throwable t) {
                Log.d(TAG, "Failed HTTP Transfer : " + t.getMessage());
            }
        });
    }
}
