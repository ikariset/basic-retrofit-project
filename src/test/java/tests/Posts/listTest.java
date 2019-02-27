package tests.Posts;

import java.io.IOException;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import classes.Post;
import commons.BaseConfiguration;
import interfaces.Posts;
import retrofit2.Response;

public class listTest extends BaseConfiguration {

  @Test(description = "'GET(List)' test for 'Posts' endpoint")
  public void listPostsTest() throws JsonSyntaxException, IOException {
    // Create a Retrofit Service with an Interface
    Posts service = retrofit.create(Posts.class);
    
    // Make a API call with one of the methods defined in the interface
    Response<List<Post>> callSync = service.listPostsData().execute();
    
    String responseJson = new Gson().toJson(callSync.body()).toString();
    
    // Now you can make all assertions that you need in order to test target API functioning
    Assert.assertNotEquals(callSync.body().size(), 0, "Post is not working");
    test.info("Getting info for all posts: " + callSync.body().size() + " entries");;
    // Also, you can send interest info to extentreport
    test.info("This is the Response Body: " + responseJson);
    Assert.assertEquals(callSync.code(), 200, "Expected 'OK' HTTP response code but found '" + callSync.code() + "'");
    test.info("This is the Response HTTP code: " + callSync.code());
  }
}
