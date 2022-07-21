package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class BoardVO {
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	
	
}// end class
