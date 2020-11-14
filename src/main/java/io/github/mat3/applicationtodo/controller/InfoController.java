package io.github.mat3.applicationtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    @Autowired
    private DataSourceProperties dataSource;
    @Value("${my.prop}")
    private String myProp;

    @GetMapping("/info/dataSource")
    String ulr() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    String myProp() {
        return myProp;
    }

}
