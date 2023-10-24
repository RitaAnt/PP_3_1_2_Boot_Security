package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.service.DropTableInDataBase;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		DropTableInDataBase dropTableInDataBase = new DropTableInDataBase();
		dropTableInDataBase.dataBase();
		CreateTableInDataBase createTableInDataBase = new CreateTableInDataBase();
		createTableInDataBase.dataBase();
		createTableInDataBase.insertData();
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);

	}

}
