package interfaces;

import java.util.List;
import classes.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Posts {
  @POST("/posts/")
  @Headers("Content-Type:application/json")
  public Call<Post> createNewPost(@Body Post post);
  
  @GET("/posts/{postid}")
  public Call<Post> getPostData(@Path("postid") Integer postid);
  
  @GET("/posts/")
  public Call<List<Post>> listPostsData();
}
