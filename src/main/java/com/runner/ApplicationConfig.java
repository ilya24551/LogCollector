package com.runner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.DBService;
import service.IDBService;
import service.IRestLogCollector;
import service.RestLogCollector;

@Configuration
@ComponentScan({"service"})
public class ApplicationConfig {

    @Bean
    public DBService createIDBService(){
        return new DBService();
    }

    @Bean
    public RestLogCollector createIRestLogCollector(){
        return new RestLogCollector();
    }

    @Bean
    public Executor createExecutor() {
        //IDBService dbService = new DBService();
        //IRestLogCollector restLogCollector = new RestLogCollector();
        Executor executor = new Executor(createIRestLogCollector(),createIDBService());
        executor.run();
        return executor;
    }
}
