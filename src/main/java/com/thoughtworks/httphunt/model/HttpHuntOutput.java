package com.thoughtworks.httphunt.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpHuntOutput implements Serializable {

	private static final long serialVersionUID = 4671286443674007155L;

	@JsonProperty
	private long wordCount;

	public long getWordCount() {
		return wordCount;
	}

	public void setWordCount(long wordCount) {
		this.wordCount = wordCount;
	}

	@Override
	public String toString() {
		return "HttpOutput [wordCount=" + wordCount + "]";
	}
}
	