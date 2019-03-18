package commons;

import java.lang.reflect.Method;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseConfiguration {
  
  // Retrofit instance variables
  protected OkHttpClient.Builder httpClient;
  protected Retrofit retrofit;
  
  // Here you can set up the API URL and ExtentReport Location
  private static String baseUrl = "https://jsonplaceholder.typicode.com/";
  private static String extentLocation = "target/extent-report/suite.html";
  
  //Reporting
  String nameClass = "";
  String description = "";
  public ExtentTest test;
  ExtentReports report = new ExtentReports();
  ExtentHtmlReporter reporter = new ExtentHtmlReporter(extentLocation);

  @BeforeMethod(alwaysRun = true)
  public void beforeMethodSetup(Method method) throws Exception {
    // HTTP Client creation
    httpClient = new OkHttpClient.Builder();
    retrofit = new Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(httpClient.build())
      .build();
    
    //Creating new Test entry in HTML Report
    nameClass = this.getClass().getName().substring(
        this.getClass().getPackage().getName().length() + 1, this.getClass().getName().length());
    description = method.getAnnotation(Test.class).description();
    
    report.attachReporter(reporter);
    test = report.createTest("Automated Test Case - " + nameClass + " -- " +  description);
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethodSetup(ITestResult result) throws InterruptedException {
    // Setting Test Status to Extent
    switch(result.getStatus()) {
      case ITestResult.SUCCESS:
        test.pass("Test Case " + nameClass + " -- PASSED");
        break;
      case ITestResult.FAILURE:
        test.fail("Test Case " + nameClass + " -- FAILED");
        break;
      case ITestResult.SKIP:
        test.skip("Test Case " + nameClass + " -- SKIPPED");
        break;
      default:
        test.fail("Result Not Recognized");
        break;
    }    
    
    report.flush();
  }
}
