package com.wellsfargo.diversity.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.poiji.annotation.ExcelCell;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diversity {
	
	@Id
	private Long dunsNum;
	private String dunsName;
	private String county;
	private String streetAddress;
	private String city;
	private String STATE;
	private Long zip;
	private Long phone;
	private String executiveContact1;
	private String executiveContact1Gender;
	private Double executiveContact1Probability;
	private String executiveContact2;
	private String executiveContact2Gender;
	private Double executiveContact2Probability;
	private Long isWomanOwned;
	private String minorityOwnedDesc;
	private String companyUrl;
	private String additionalContact1;
	private String additionalContact2;
	private String additionalContact3;
	private String additionalContact4;
	private String womenLeaderName;
	private String probabilityOfBeingWomen;
}
