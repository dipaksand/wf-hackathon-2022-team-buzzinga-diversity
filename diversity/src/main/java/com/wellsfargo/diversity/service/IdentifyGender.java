package com.wellsfargo.diversity.service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wellsfargo.diversity.entity.Diversity;
import com.wellsfargo.diversity.model.GenderResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Log4j2
public class IdentifyGender {
    public List<Diversity> updateGender(List<Diversity> diversityList) throws IOException {
        String name=null;
        List<Diversity> responseObject = new ArrayList<>();
        List<GenderResponse> tmp;
        List<String> fNames = new ArrayList<>();
        for (int i=0;i<diversityList.size();i++) {
            Diversity diversity = diversityList.get(i);
            if(diversity.getExecutiveContact1() != null) {
                fNames.add(diversity.getExecutiveContact1().split(" ")[0]);
            }
            if(diversity.getExecutiveContact2() != null) {
                fNames.add(diversity.getExecutiveContact2().split(" ")[0]);
            }
            if(diversity.getAdditionalContact1() != null) {
                fNames.add(diversity.getAdditionalContact1().split(" ")[0]);
            }
            if(diversity.getAdditionalContact2() != null) {
                fNames.add(diversity.getAdditionalContact2().split(" ")[0]);
            }
            if(diversity.getAdditionalContact3() != null) {
                fNames.add(diversity.getAdditionalContact3().split(" ")[0]);
            }
            if(diversity.getAdditionalContact4() != null) {
                fNames.add(diversity.getAdditionalContact4().split(" ")[0]);
            }
            log.info("getting gender details");
            tmp = getGender(fNames);
            GenderResponse highestProbabilityWomen=isWomenLeadershipIdentification(tmp);
            if(highestProbabilityWomen != null){
                diversity.setIsWomanOwned(Long.valueOf(1));
                diversity.setWomenLeaderName(highestProbabilityWomen.getName());
                diversity.setProbabilityOfBeingWomen(String.valueOf(highestProbabilityWomen.getProbability()));
            } else {
                diversity.setIsWomanOwned(Long.valueOf(0));
            }
            responseObject.add(diversity);
        }
        return responseObject;
    }
    private List<GenderResponse> getGender(List<String> fName) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = null;
        String requestString="https://api.genderize.io/?";
        for(int i=0;i<fName.size();i++) {
            requestString=requestString + "name[]=" + fName.get(i) + "&";
        }
        //remove last char
        requestString = requestString.substring(0, requestString.length() - 1);
        log.info("request string " + requestString);
        request = new HttpGet(requestString);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder data = new StringBuilder();
        String line = "";
        while ((line = rd.readLine()) != null) {
            data.append(line);
        }
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        return gson.fromJson(data.toString(), new TypeToken<ArrayList<GenderResponse>>() {}.getType());
    }
    private GenderResponse isWomenLeadershipIdentification(List<GenderResponse> genderResponses){
        GenderResponse genderResponseReturn = null;
        for(int i=0; i<genderResponses.size();i++){
            GenderResponse genderResponse=genderResponses.get(i);
            if(genderResponse.getGender().equals("female")){
                log.info("found a female leader");
                if(genderResponseReturn != null){
                    if(Math.round(genderResponseReturn.getProbability()*100)<Math.round(genderResponse.getProbability()*100)){
                        genderResponseReturn.setGender(genderResponse.getGender());
                        genderResponseReturn.setName(genderResponse.getName());
                        genderResponseReturn.setProbability(genderResponse.getProbability());
                        genderResponseReturn.setCount(genderResponse.getCount());
                    }
                } else {
                    genderResponseReturn = new GenderResponse();
                    genderResponseReturn.setGender(genderResponse.getGender());
                    genderResponseReturn.setName(genderResponse.getName());
                    genderResponseReturn.setProbability(genderResponse.getProbability());
                    genderResponseReturn.setCount(genderResponse.getCount());
                }
            }
        }
        return genderResponseReturn;
    }
}
