package com.axxessio.axx2sls.login.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

public class AmazonService {

	private static BasicAWSCredentials creds = new BasicAWSCredentials("################", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	
	private static AmazonSNS sns = null;
	private static AWSSecurityTokenService sts = null;
	
	public static AmazonSNS getSNS() {
		if (sns == null){
			sns = AmazonSNSClientBuilder.
			      standard().
				  withCredentials(new AWSStaticCredentialsProvider(creds)).
				  withEndpointConfiguration(new EndpointConfiguration("sns.eu-central-1.amazonaws.com", "eu-central-1")).
				  build();
		}
		return sns;
	}
	
	public static AWSSecurityTokenService getSTS() {
		if (sts == null){
			sts = AWSSecurityTokenServiceClientBuilder.
				  standard().
				  withCredentials(new AWSStaticCredentialsProvider(creds)).
				  withEndpointConfiguration(new EndpointConfiguration("https://sts.eu-central-1.amazonaws.com", "eu-central-1")).
				  build();
		}
		return sts;
	}
}
