package com.wellsfargo.diversity.service;

import com.wellsfargo.diversity.DiversityApplication;
import com.wellsfargo.diversity.entity.Diversity;
import com.wellsfargo.diversity.model.DiversityData;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class ScrapeCompanyDetails {
    private static final String CHROME_DRIVER_PATH= System.getProperty("user.dir");
//            DiversityApplication.class.getClassLoader().getResource("driver/chromedriver.exe").getPath();
    public List<Diversity> ScrapeFromDNBSite(List<DiversityData> diversityData) {
		log.info("total rows:" + diversityData.size());
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH + "\\driver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        List<Diversity> diversities = new ArrayList<>();
        for (int i = 0; i < diversityData.size(); i++) {
            Diversity diversity = new Diversity();
            if(!diversityData.get(i).getDunsNum().equals(null)) {
                diversity.setDunsNum(Long.valueOf(diversityData.get(i).getDunsNum().toString()));
                diversity.setDunsName(diversityData.get(i).getDunsName());
                diversity.setCounty(diversityData.get(i).getCounty());
                diversity.setCity(diversityData.get(i).getCity());
                diversity.setStreetAddress(diversityData.get(i).getStreetAddress());
                diversity.setSTATE(diversityData.get(i).getSTATE());
                diversity.setZip(Long.valueOf(diversityData.get(i).getZip().toString()));
                diversity.setPhone(Long.valueOf(diversityData.get(i).getPhone().toString()));
                diversity.setCounty(diversityData.get(i).getCounty());
                diversity.setExecutiveContact1(diversityData.get(i).getExecutiveContact1());
                diversity.setExecutiveContact2(diversityData.get(i).getExecutiveContact2());
                String companyName = diversity.getDunsName();
                try {
                    if (driver == null) {
                        driver = new ChromeDriver();
                    }

                    if (driver.getTitle() == null) {
                        driver = new ChromeDriver();
                    }

                    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                    log.info("companyName: " + companyName);
                    log.info("DUNS no: " + diversity.getDunsNum());
                    driver.get("https://www.dnb.com/site-search-results.html#AllSearch=" + diversity.getDunsNum());
                    driver.findElement(By.cssSelector("a[class*='tableCompanyNameLink']")).click();

                    String websITE = driver.findElement(By.cssSelector("a[class='ext-icon company-website-url']")).getAttribute("href");

                    diversity.setCompanyUrl(websITE);

                    List<WebElement> contacts = driver.findElements(By.cssSelector("div[class='contacts-body'] ul li"));

                    log.info("total contacts:" + contacts.size());

                    int count = 0;
                    for (WebElement contact : contacts) {
                        String name = contact.findElement(By.cssSelector("div:nth-child(1)")).getText();
                        switch (count) {
                            case 0:
                                diversity.setAdditionalContact1(name);
                                break;
                            case 1:
                                diversity.setAdditionalContact2(name);
                                break;
                            case 2:
                                diversity.setAdditionalContact3(name);
                                break;
                            case 3:
                                diversity.setAdditionalContact4(name);
                                break;
                            default:
                                log.info("ignoring more than 4 contacts : " + name);
                        }
                        count++;
                    }
                    diversities.add(diversity);
                    log.info("done scraping for above company");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                log.info("null row");
            }
        }
        driver.close();
        driver.quit();
        return diversities;
    }
}
