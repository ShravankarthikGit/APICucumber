package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils {
	public static PrintStream log;

	public static RequestSpecification requestSpec() throws IOException {
		
	    if (log == null) {
	        // The 'true' argument is the key. It enables APPEND mode.
	        log = new PrintStream(new FileOutputStream("logging.txt", true)); 
	    }
	    
		// Use .addFilter to ensure the logs are captured
		RequestSpecification req = new RequestSpecBuilder().setBaseUri(getGlobalValue().getProperty("baseurl"))
				.setRelaxedHTTPSValidation() // Add this line to relax HTTPS validation
				.addQueryParam("key", "qaclick123").addFilter(RequestLoggingFilter.logRequestTo(log)) // Logs Request
				.addFilter(ResponseLoggingFilter.logResponseTo(log)) // Logs Response
				.addHeader("Content-Type", "application/json").setContentType(ContentType.JSON).build();
		return req;
	}

	public static Properties getGlobalValue() throws IOException {
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream("src/test/java/resources/global.properties");
		prop.load(input);
		return prop;
	}

}
