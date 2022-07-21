package org.zerock.myapp.domain;

import lombok.Value;

@Value
public class BoardVO {

	private Integer BNO;
	private String TITLE;
	private String CONTENT;
	private String WRITER;
	
}//end class
