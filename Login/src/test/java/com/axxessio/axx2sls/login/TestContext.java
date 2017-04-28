package com.axxessio.axx2sls.login;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

/**
 * A simple mock implementation of the {@code Context} interface. Default
 * values are stubbed out, and setters are provided so you can customize
 * the context before passing it to your function.
 */
public class TestContext implements Context {

    private String awsRequestId = "ceb075ed-d738-11e6-aa5a-dfd5be7167d0";
    private ClientContext clientContext;
    private String functionName = "loginFunction";
    private CognitoIdentity identity;
    private String logGroupName = "/aws/lambda/loginFunction";
    private String logStreamName = "2017/01/10/[$LATEST]19d4650f5df942d38e9c09c6d7367af7";
    private LambdaLogger logger = new TestLogger();
    private int memoryLimitInMB = 128;
    private int remainingTimeInMillis = 15000;

    @Override
    public String getAwsRequestId() {
        return awsRequestId;
    }

    public void setAwsRequestId(String value) {
        awsRequestId = value;
    }

    @Override
    public ClientContext getClientContext() {
        return clientContext;
    }

    public void setClientContext(ClientContext value) {
        clientContext = value;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String value) {
        functionName = value;
    }

    @Override
    public CognitoIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(CognitoIdentity value) {
        identity = value;
    }

    @Override
    public String getLogGroupName() {
        return logGroupName;
    }

    public void setLogGroupName(String value) {
        logGroupName = value;
    }

    @Override
    public String getLogStreamName() {
        return logStreamName;
    }

    public void setLogStreamName(String value) {
        logStreamName = value;
    }

    @Override
    public LambdaLogger getLogger() {
        return logger;
    }

    public void setLogger(LambdaLogger value) {
        logger = value;
    }

    @Override
    public int getMemoryLimitInMB() {
        return memoryLimitInMB;
    }

    public void setMemoryLimitInMB(int value) {
        memoryLimitInMB = value;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return remainingTimeInMillis;
    }

    public void setRemainingTimeInMillis(int value) {
        remainingTimeInMillis = value;
    }

    /**
     * A simple {@code LambdaLogger} that prints everything to stderr.
     */
    private static class TestLogger implements LambdaLogger {

        @Override
        public void log(String message) {
            System.err.println(message);
        } 
    }

	@Override
	public String getFunctionVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInvokedFunctionArn() {
		// TODO Auto-generated method stub
		return null;
	}
}