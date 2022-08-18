package ru.hogwarts.school2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ExampleServiceImpl implements ExampleService {

    Logger logger = LoggerFactory.getLogger(ExampleService.class);

    @Override
    public Integer streamExample() {
//        ---TASK FROM HOMEWORK START---
//        int sum = Stream.iterate(1, a -> a +1)
//                .limit(1_000_000)
//                .reduce(0, (a, b) -> a + b );
//        ---TASK FROM HOMEWORK FINISH---

        List<Integer> limitation = Stream.iterate(1, a -> a + 1)
                .limit(100_000_000)
                .collect(Collectors.toList());

        long starting = System.currentTimeMillis();

        limitation.stream()
                .parallel()
                .mapToInt(ex -> ex)
                .sum();
        return (int) (System.currentTimeMillis() - starting);
    }
}
