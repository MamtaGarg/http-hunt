package com.thoughtworks.httphunt.service;

import java.io.IOException;

/**
 * The Interface HttpHuntService - every class which reads or writes data from any URL or any file should implement this interface. 
 */
public interface HttpHuntService {

	/**
	 * Gets the count.
	 *
	 * @param text the text
	 * @return the count
	 */
	public long getCount(String text);
	
	/**
	 * Read input.
	 *
	 * @param inputPath the input path
	 * @return the string
	 */
	public String readInput(String inputPath);
	
	/**
	 * Write output.
	 *
	 * @param outputPath the output path
	 * @param count the count
	 */
	public void writeOutput(String outputPath, long count);
	
}
