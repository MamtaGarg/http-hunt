package com.thoughtworks.httphunt.service;

import static com.thoughtworks.httphunt.constants.ApplicationConstants.CONTENT_TYPE;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.CONTENT_TYPE_CONSTANT;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.REQUEST_METHOD_GET;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.REQUEST_METHOD_POST;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.USER_ID;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.USER_ID_CONSTANT;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;

import com.thoughtworks.httphunt.exception.InvalidPathException;
import com.thoughtworks.httphunt.model.HttpHuntOutput;

/**
 * The Class HttpHuntServiceImpl.
 */
public class HttpHuntServiceImpl implements HttpHuntService {

	private final static Logger logger = getLogger(HttpHuntServiceImpl.class);

	/**
	 * Read input from path.
	 *
	 * @param inputPath the input path
	 * @return the string
	 */
	public String readInput(String inputPath) {
		logger.info("Entering readInput()");
		HttpURLConnection conn = null;
		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(inputPath);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(REQUEST_METHOD_GET);
			conn.setRequestProperty(USER_ID_CONSTANT, USER_ID);

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new InvalidPathException("HTTP error status code : " + conn.getResponseCode()
						+ " error message : " + conn.getResponseMessage());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String line;
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
		} catch (IOException exception) {
			logger.error("Not able to read the data from remote url. Error message  : " + exception.getMessage());
		} finally {
			conn.disconnect();
		}
		logger.info("Exiting from readInput()");
		return builder.toString();
	}

	/**
	 * Gets the count.
	 *
	 * @param text the text
	 * @return the count
	 */
	public long getCount(String text) {
		long count = 0;
		if (text != null && !text.isEmpty()) {
			text = text.replaceAll("\"", "");
			text = text.replaceAll("\\{text:", "");
			text = text.replace("}", "");
			count = text.length();
		}
		return count;
	}

	/**
	 * Write output.
	 *
	 * @param outputPath the output path
	 * @param count      the count
	 */
	public void writeOutput(String outputPath, long count) {
		logger.info("Entering writeOutput()");
		HttpURLConnection conn = null;
		try {
			URL outputUrl = new URL(outputPath);
			conn = (HttpURLConnection) outputUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(REQUEST_METHOD_POST);
			conn.setRequestProperty(USER_ID_CONSTANT, USER_ID);
			conn.setRequestProperty(CONTENT_TYPE_CONSTANT, CONTENT_TYPE);
			conn.setRequestProperty("Accept", CONTENT_TYPE);

			HttpHuntOutput httpHuntOutput = new HttpHuntOutput();
			httpHuntOutput.setWordCount(count);
			// String output = "\"wordCount\": " + count;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			//out.writeObject(httpHuntOutput);
			//out.flush();
			byte[] postDataBytes = bos.toByteArray();
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			writer.write(postDataBytes);
			writer.flush();
			writer.close();
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new InvalidPathException("HTTP error status code : " + conn.getResponseCode()
						+ " error message : " + conn.getResponseMessage());
			}
		} catch (IOException exception) {
			logger.error("Not able to write the data on remote url. Error message  : " + exception.getMessage());
		} finally {
			conn.disconnect();
		}
	}
}