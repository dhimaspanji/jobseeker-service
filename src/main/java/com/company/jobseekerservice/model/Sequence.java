package com.company.jobseekerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "sequences")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sequence {

	@Id
	private String id;
	private long seq;
}
