package com.dvilinsk.testermatch.controller;

import com.dvilinsk.testermatch.DTO.TesterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dvilinsk.testermatch.repository.TesterRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TesterController {
    private TesterRepository testerRepository;

    @Autowired
    public TesterController(TesterRepository testerRepository) {
        this.testerRepository = testerRepository;
    }

    @GetMapping("/testers/all")
    public ResponseEntity<List<TesterDTO>> getAllTesters() {
        List<TesterDTO> testers = testerRepository.findAllTesters();
        return new ResponseEntity<>(testers, HttpStatus.OK);
    }

    @GetMapping("/testers/countries")
    public ResponseEntity<List<TesterDTO>> getTestersInCountry(@RequestParam List<String> countries) {
        if (invalidParams(countries)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TesterDTO> testers = testerRepository.findByCountry(lower(countries));
        return new ResponseEntity<>(testers, HttpStatus.OK);
    }

    @GetMapping("/testers/devices")
    public ResponseEntity<List<TesterDTO>> getTestersTestingOnDevice(@RequestParam List<String> devices) {
        if (invalidParams(devices)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TesterDTO> testers = testerRepository.findByDevice(lower(devices));
        return new ResponseEntity<>(testers, HttpStatus.OK);
    }

    @GetMapping("/testers")
    public ResponseEntity<List<TesterDTO>> getTestersByCountyAndDevice(@RequestParam List<String> countries,
                                                                       @RequestParam List<String> devices) {
        if (invalidParams(countries) || invalidParams(devices)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<TesterDTO> testers = testerRepository.findByDeviceAndCountry(lower(devices), lower(countries));
        return new ResponseEntity<>(testers, HttpStatus.OK);
    }

    /**
     * Lowercases every string in a given list
     * @param data List of values to be lowercase
     * @return A new list where each value of the input list has been lowercased
     */
    private List<String> lower(List<String> data) {
        List<String> lower = new ArrayList<>();
        for (String s : data) {
            lower.add(s.toLowerCase());
        }
        return lower;
    }
    
    private boolean invalidParams(List<String> params) {
        return params == null || params.isEmpty();
    }
}