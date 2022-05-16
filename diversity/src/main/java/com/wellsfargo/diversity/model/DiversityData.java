package com.wellsfargo.diversity.model;

import com.poiji.annotation.ExcelCell;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiversityData {
	@ExcelCell(0)
	private Object dunsNum;
	@ExcelCell(1)
	private String dunsName;
	@ExcelCell(2)
	private String county;
	@ExcelCell(3)
	private String streetAddress;
	@ExcelCell(4)
	private String city;
	@ExcelCell(5)
	private String STATE;
	@ExcelCell(6)
	private Object zip;
	@ExcelCell(7)
	private Object phone;
	@ExcelCell(8)
	private String executiveContact1;
	@ExcelCell(9)
	private String executiveContact2;
	@ExcelCell(10)
	private Object isWomanOwned;
	@ExcelCell(11)
	private String MinorityOwnedDesc;
}
