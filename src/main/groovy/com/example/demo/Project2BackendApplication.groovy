package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
//just to find the package, without this it cant find the DataLoader class
@ComponentScan(basePackages = ["com.example.demo", "main.groovy.com.example.demo.loader"])
class Project2BackendApplication {

	static void main(String[] args) {
		SpringApplication.run(Project2BackendApplication, args)
	}

}