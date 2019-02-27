package tests.Users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import classes.User;
import commons.BaseConfiguration;
import interfaces.Users;
import retrofit2.Response;
import org.testng.Assert;

public class postTest extends BaseConfiguration {

  String jsonPath = "src/test/resources/classes/User/create-user.json";

  @Test(description = "'POST' test for 'Users' endpoint")
  public void addNewUserTest() throws JsonSyntaxException, IOException {
    // Get JSON Data from resources package
    User user =
        new Gson().fromJson(new String(Files.readAllBytes(Paths.get(jsonPath))), User.class);

    // Create a Retrofit Service with an Interface
    Users service = retrofit.create(Users.class);

    // Make a API call with one of the methods defined in the interface
    Response<User> callSync = service.createNewUser(user).execute();

    String requestJson = new Gson().toJson(user).toString();
    String responseJson = new Gson().toJson(callSync.body()).toString();

    // Now you can make all assertions that you need in order to test target API functioning
    Assert.assertNotEquals(callSync.body(), null, "Post is not working");
    // Also, you can send interest info to extentreport
    test.info("This is the Response Body: " + new String(callSync.message()));
    Assert.assertEquals(callSync.code(), 201,
        "Expected 'OK' HTTP response code but found '" + callSync.code() + "'");
    test.info("This is the Response HTTP code: " + callSync.code());
    Assert.assertEquals(requestJson, responseJson, "Data is not the same");
  }
}
