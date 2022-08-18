package ru.hogwarts.school2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school2.service.ExampleService;

@RestController
@RequestMapping("example")
public class ExampleController {

    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("demo")
    public Integer getStreamExample() {
        return exampleService.streamExample();
    }
}
