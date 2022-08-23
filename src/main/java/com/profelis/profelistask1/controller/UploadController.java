package com.profelis.profelistask1.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.profelis.profelistask1.entites.Employee;
import com.profelis.profelistask1.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UploadController {

    public final EmployeeService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<Employee> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Employee.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Employee> employees = csvToBean.parse();

                model.addAttribute("employees", employees);
                model.addAttribute("status", true);

                model.addAttribute("calculate", service.dayCalculate(employees));



            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }

    @RequestMapping(value="/descriptions")
    public String doStuffMethod() {
        return "descriptions";
    }
}
