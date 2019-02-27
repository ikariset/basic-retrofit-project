package interfaces;

import java.util.List;
import classes.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Users {
  @POST("/users/")
  @Headers("Content-Type:application/json")
  public Call<User> createNewUser(@Body User user);
  
  @GET("/users/{userid}")
  public Call<User> getUserData(@Path("userid") Integer userid);
  
  @GET("/users/")
  public Call<List<User>> listUserData();
}
