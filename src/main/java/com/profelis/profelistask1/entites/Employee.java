package com.profelis.profelistask1.entites;

import com.opencsv.bean.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @CsvBindByName
    private Integer empId;

    @CsvBindByName
    private Integer projectId;


    //@CsvBindByPosition(position = 2)
    //@CsvCustomBindByPosition(position = 2, converter = DateConverter.class)
    @CsvBindByName
    @CsvDate(value = "yyyy-MM-dd")
    private Date dateFrom;


    //@CsvCustomBindByPosition(position = 3, converter = DateConverter.class)
    //@CsvBindByPosition(position = 3)
    //@CsvDate(value = "yyyy-MM-dd")
    @CsvBindByName
    private String dateTo;
}
