package com.wellsfargo.diversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.wellsfargo.diversity.service.UploadControllerService;

/**
 * @author Shamla -diversity input excel processing
 *
 */
@Controller
public class UploadController {
	@Autowired
	UploadControllerService uploadControllerService;
	

	@GetMapping("/")
	public String upload() {
		return "upload";
	}

	/**
	 * @param file
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@PostMapping(path = "/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
		return uploadControllerService.processFile(file, model);
	}


}