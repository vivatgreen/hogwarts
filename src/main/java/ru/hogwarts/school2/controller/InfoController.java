package ru.hogwarts.school2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school2.service.InfoService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("port")
    ResponseEntity<String> getPort() {
        String getPort = infoService.getPort();
        return ResponseEntity.ok(getPort);
    }
    @GetMapping("/stream/{limit}")
    public String getCalculationResult(@PathVariable int limit) {

        long start1 = System.currentTimeMillis();
        int sum1 = Stream.iterate(1, a -> a +1)
                .limit(limit)
                .collect(Collectors.toList())
                .parallelStream()
                .reduce(0, (a, b) -> a + b);
        long finish1 = System.currentTimeMillis();
        long time1 = finish1 - start1;

        long start2 = System.currentTimeMillis();
        int sum2 = Stream.iterate(1, a -> a +1)
                .limit(limit)
                .reduce(0, (a, b) -> a + b);
        long finish2 = System.currentTimeMillis();
        long time2 = finish2 - start2;

        return "Option-1 summary: " + sum1 + " time spent: " + time1 + "ms. " +
                "Option-2 summary: " + sum2 + " time spent: " + time2 + "ms.";
    }

}
