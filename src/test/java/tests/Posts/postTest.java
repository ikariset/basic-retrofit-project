package tests.Posts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import classes.Post;
import commons.BaseConfiguration;
import interfaces.Posts;
import retrofit2.Response;
import org.testng.Assert;

public class postTest extends BaseConfiguration {

  String jsonPath = "src/test/resources/classes/Post/create-post.json";

  @Test(description = "'POST' test for 'Posts' endpoint")
  public void addNewPostTest() throws JsonSyntaxException, IOException {
    // Get JSON Data from resources package
    Post post =
        new Gson().fromJson(new String(Files.readAllBytes(Paths.get(jsonPath))), Post.class);

    // Create a Retrofit Service with an Interface
    Posts service = retrofit.create(Posts.class);
    
    // Make a API call with one of the methods defined in the interface
    Response<Post> callSync = service.createNewPost(post).execute();
    
    String requestJson = new Gson().toJson(post).toString();
    String responseJson = new Gson().toJson(callSync.body()).toString();
    
    // Now you can make all assertions that you need in order to test target API functioning
    Assert.assertNotEquals(callSync.body(), null, "Post is not working");
    test.info("This is the Response Body: " + new String(callSync.message()));
    Assert.assertEquals(callSync.code(), 201, "Expected 'OK' HTTP response code but found '" + callSync.code() + "'");
    test.info("This is the Response HTTP code: " + callSync.code());
    Assert.assertEquals(requestJson, responseJson, "Data is not the same");
  }
}
