package com.amt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @NoArgsConstructor @ToString
public class MessageDTO {

	private String message;
	
	public MessageDTO(String message) {
		this.message = message;
	}
	
}
