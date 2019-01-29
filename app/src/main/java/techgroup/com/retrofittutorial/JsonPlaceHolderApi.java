package techgroup.com.retrofittutorial;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<PostAttributes>> getAllPost();

}
