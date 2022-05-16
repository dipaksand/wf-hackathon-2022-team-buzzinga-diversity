package com.wellsfargo.diversity.controller;

import java.io.FileInputStream;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@ContextConfiguration(classes = {UploadController.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class UploadControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
    @Test
    public void testSingleFileUpload()
    {
//    	Workbook mockWorkbook = mock(Workbook.class);
//    	Sheet mockSheet = mock(Sheet.class);
//    	Row mockRow = mock(Row.class);
//    	Cell mockCell = mock(Cell.class);
//    	when(mockWorkbook.createSheet("Conference Details")).thenReturn(mockSheet);
//    	when(mockSheet.createRow(0)).thenReturn(mockRow);
//    	when(mockSheet.createRow(anyInt())).thenReturn(mockRow);
//    	when(mockRow.createCell(anyInt())).thenReturn(mockCell);
    	
      try {
    	  FileInputStream inputFile = new FileInputStream("C:\\Users\\dochi\\Downloads\\Study material\\Book1.xlsx");  
      	
      	MockMultipartFile file = new MockMultipartFile("file", "Test.xls", "multipart/form-data", inputFile); 
//          String responseEntity = uploadController.singleFileUpload(file, model);
          MockMvc mockMvc 
          = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc.perform(multipart("/upload").file(file))
		    .andExpect(status().isOk());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
    }

}
