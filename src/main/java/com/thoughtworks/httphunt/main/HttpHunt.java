package com.thoughtworks.httphunt.main;

import static com.thoughtworks.httphunt.constants.ApplicationConstants.INPUT_PATH;
import static com.thoughtworks.httphunt.constants.ApplicationConstants.OUTPUT_PATH;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import com.thoughtworks.httphunt.service.HttpHuntService;
import com.thoughtworks.httphunt.service.HttpHuntServiceImpl;

public class HttpHunt {

	private final static Logger logger = getLogger(HttpHunt.class);

	private static HttpHuntService httpHuntService = new HttpHuntServiceImpl();

	public static void main(String[] args) {
			logger.info("Entering http-hunt's main()");
			long startTime = System.currentTimeMillis();
			String text = httpHuntService.readInput(INPUT_PATH);
			long count = httpHuntService.getCount(text);
			long endTime = System.currentTimeMillis();
			logger.info("Time taken between end of Get request and start of Post request is : " + (endTime - startTime) + "ns");
			httpHuntService.writeOutput(OUTPUT_PATH, count);
			logger.info("Exit http-hunt's main()");
	}
}
