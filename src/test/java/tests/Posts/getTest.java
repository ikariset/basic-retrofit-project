package tests.Posts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import classes.Post;
import commons.BaseConfiguration;
import interfaces.Posts;
import retrofit2.Response;

public class getTest extends BaseConfiguration {
  String jsonPath = "src/test/resources/classes/Post/get-post.json";

  @Test(description = "'GET' test for 'Posts' endpoint")
  public void getPostTest() throws JsonSyntaxException, IOException {
    // Get JSON Data from resources package
    Post post =
        new Gson().fromJson(new String(Files.readAllBytes(Paths.get(jsonPath))), Post.class);

    // Create a Retrofit Service with an Interface
    Posts service = retrofit.create(Posts.class);
    
    // Make a API call with one of the methods defined in the interface
    Response<Post> callSync = service.getPostData(post.getId()).execute();
    
    String responseJson = new Gson().toJson(callSync.body()).toString();
    
    // Now you can make all assertions that you need in order to test target API functioning
    Assert.assertNotEquals(callSync.body(), null, "Post is not working");
    Assert.assertEquals(post.getId(), callSync.body().getId(), "Hey! This data is wrong!");
    test.info("Getting info for post: " + callSync.body().getId());
    // Also, you can send interest info to extentreport
    test.info("This is the Response Body: " + responseJson);
    Assert.assertEquals(callSync.code(), 200, "Expected 'OK' HTTP response code but found '" + callSync.code() + "'");
    test.info("This is the Response HTTP code: " + callSync.code());
  }
}
