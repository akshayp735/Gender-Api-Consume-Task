package com.omsoftware.apiconsumetask.mstgender;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@EnableScheduling
@RequestMapping("/mst_gender")
public class Controller {

    @Autowired
    Repository repository;

    @Scheduled(cron = "0 */5 * ? * *")
    @GetMapping("/getGenderAndSaveGender")
    public void getGenderAndSaveGender() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.genderize.io?name={param1}";
        JSONObject myObject = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String response1 = restTemplate.getForObject(url, String.class, "luc");
            myObject = new JSONObject(response1);
            Gender gender = new Gender();
            gender.setGender(myObject.getString("gender"));
            gender.setName(myObject.getString("name"));
            gender.setProbability((float) myObject.getDouble("probability"));
            gender.setCount(myObject.getLong("count"));
            repository.save(gender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getGenderById/{genderId}")
    public Gender getGenderById(@PathVariable("genderId") Long genderId) {
        return repository.findByGenderId(genderId);

    }

    @GetMapping("/getAllGenders")
    public List<Gender> getAllGenders() {
        return repository.findAll();

    }
}
