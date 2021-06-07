package spring;

import java.io.IOException;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Component
public class AmazonSES {
	static final String FROM = "els102110@gmail.com";
	static final String TO="els102110@gmail.com";
	static final String SUBJECT = "회원가입에 축하드립니다(AWS SES와 스프링연동 완료)";
	static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
			+ "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
			+ "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" 
			+ "AWS SDK for Java</a><br>"
			+ "회원가입에 축하드립니다.";
	// The email body for recipients with non-HTML email clients.
	static final String TEXTBODY = "This email was sent through Amazon SES "
			+ "using the AWS SDK for Java.";

	public void sendEmail(String email) {
		//String to = email;
		try {
			
			//ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
			//credentialsProvider.getCredentials();
			 BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("-", "-");
		        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);
		        
			AmazonSimpleEmailService client = 
					AmazonSimpleEmailServiceClientBuilder.standard()
					.withCredentials(awsStaticCredentialsProvider)
					.withRegion(Regions.AP_NORTHEAST_2).build();
					// Replace US_WEST_2 with the AWS Region you're using for
					// Amazon SES.
					
			SendEmailRequest request = new SendEmailRequest()
					.withDestination(
							new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body()
									.withHtml(new Content()
											.withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content()
											.withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content()
									.withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM);
          // Comment or remove the next line if you are not using a
          // configuration set
			client.sendEmail(request);
			System.out.println("Email sent!");
    } catch (Exception ex) {
      System.out.println("The email was not sent. Error message: " 
          + ex.getMessage());
    }
  }
}

