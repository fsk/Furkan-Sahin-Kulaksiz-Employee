package com.profelis.profelistask1.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEmployee {

    private Integer emp1Id;
    private Integer emp2Id;
    private Integer projectId;
    private Long intersectionDay;

}
