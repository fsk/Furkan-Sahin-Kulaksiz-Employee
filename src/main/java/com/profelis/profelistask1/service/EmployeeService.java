package com.profelis.profelistask1.service;

import com.profelis.profelistask1.entites.Employee;
import com.profelis.profelistask1.entites.ResponseEmployee;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeService {

    public String dayCalculate(List<Employee> list) throws ParseException {

        ListIterator<Employee> iterator = list.listIterator();

        /**
         * 2 farklı nedenden dolayı HashMap var
         * 1-> Proje ID'ye göre value'lar alınıp en çok ortak gün olan değer daha rahat bulunur.
         * 2-> For döngülerinde aynı proje id için kayıt çoklar.
         */
        Map<Integer, Long> pair = new HashMap<>();
        //list.stream().filter(item -> item.getEmpId() == )

        Date before;
        Date after;



        //Listeyi 2 farklı gezmek için 2 for döngüsü kullanıldı. (ListIterator kullanılabilirdi.)
        for (int i = 0; i < list.size(); i++) {
            for (int j = 1; j<list.size(); j++) {

                //Her projenin 1 kere eklendiğinden emin olmak için ve
                //projedeki çalışanların bir kere kaydolması için
                if (list.get(i).getProjectId().equals(list.get(j).getProjectId()) && list.get(i).getEmpId() != list.get(j).getEmpId()) {


                    // DateTo'nun null girilmesi durumundaki kontrol
                    if (list.get(i).getDateTo().contains("-") && list.get(j).getDateTo().contains("-")) {
                        Date sDate = new SimpleDateFormat(("yyyy-MM-dd")).parse(list.get(i).getDateTo());
                        Date s2Date = new SimpleDateFormat(("yyyy-MM-dd")).parse(list.get(j).getDateTo());
                        before = list.get(i).getDateFrom().before(list.get(j).getDateFrom()) ? list.get(i).getDateFrom() : list.get(j).getDateFrom();
                        after = list.get(i).getDateFrom().after(list.get(j).getDateFrom()) ? sDate : s2Date;
                        //calculateDiff(before.getTime(), after.getTime())
                        if (overlap(list.get(i).getDateFrom(), sDate, list.get(j).getDateFrom(), s2Date) == true) {
                            pair.put(list.get(i).getProjectId(), calculateDiff(before.getTime(), after.getTime()));

                        }else {
                            pair.put(list.get(i).getProjectId(), 0L);
                        }
                    }



                }
            }
        }

        if (pair.isEmpty() == true) {
            return "Ortak calisilan bir proje yok ya da dosyada NULL degerler var.";
        }else {
            Long val = pair.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
            return val + " bir projede en cok ortak calisilan gundur.";
        }

    }


    /**
     * CalculateDiff methodu 2 tarih arasındaki günü hesaplar.
     */
    private Long calculateDiff(Long start, Long finish) {
        long timeDiff = Math.abs(finish - start);
        return TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * Overlap methodu ortak projeler için ortak tarihlerin olup olmadığını döndürür.
     */
    private boolean overlap(Date start1, Date end1, Date start2, Date end2){
        return start1.getTime() <= end2.getTime() && start2.getTime() <= end1.getTime();
    }




}
