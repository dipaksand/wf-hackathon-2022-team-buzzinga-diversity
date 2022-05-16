package com.wellsfargo.diversity.service;

import java.util.ArrayList;
import java.util.List;

import com.wellsfargo.diversity.entity.Diversity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.wellsfargo.diversity.model.DiversityData;

@Service
public class UploadControllerService {
	@Autowired
	DiversityDataService diversityDataService;
	@Autowired
	ScrapeCompanyDetails scrapeCompanyDetails;

	@Autowired
	IdentifyGender identifyGender;
	private static final Logger logger = LoggerFactory.getLogger(UploadControllerService.class);

	public String processFile(MultipartFile file, Model model) {
		if (file.isEmpty()) {
			logger.info(" Uploaded file is empty");
			model.addAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		List<DiversityData> diversityDataList = new ArrayList();
		List<Diversity> diversities=null;
		try {
			logger.info("updating Java Object");
			List<DiversityData> diversityData = Poiji.fromExcel(file.getInputStream(), PoijiExcelType.XLSX,
					DiversityData.class);
			diversityDataList.addAll(diversityData);
			//scrape details
			logger.info("Scraping details");
			diversities=scrapeCompanyDetails.ScrapeFromDNBSite(diversityData);
			//update gender
			logger.info("updating gender");
			diversities=identifyGender.updateGender(diversities);
			diversityDataService.saveDiversityData(diversities);
		} catch (Exception e) {
			logger.error("IOException {}", e.getMessage());
			e.printStackTrace();
		}
		model.addAttribute("diversityData", diversities);
		return "uploadStatus";
	}

}
