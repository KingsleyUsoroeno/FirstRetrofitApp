package techgroup.com.retrofittutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
    List<PostAttributes> postAttributes;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        postAttributes = new ArrayList<>();

        // Tv = (TextView) findViewById(R.id.TextView);
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(postRecyclerViewAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi placeHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<PostAttributes>> post = placeHolderApi.getAllPost();

        post.enqueue(new Callback<List<PostAttributes>>() {
            @Override
            public void onResponse(Call<List<PostAttributes>> call, Response<List<PostAttributes>> response) {

                    postAttributes = response.body();
                    postRecyclerViewAdapter.setPostList(postAttributes);
                    
            }

            @Override
            public void onFailure(Call<List<PostAttributes>> call, Throwable t) {
                Log.d(TAG, "Failed HTTP Transfer : " + t.getMessage());
            }
        });
    }

}
