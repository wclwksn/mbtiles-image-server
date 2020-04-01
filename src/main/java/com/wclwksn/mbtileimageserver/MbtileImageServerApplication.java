package com.wclwksn.mbtileimageserver; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; 

@SpringBootApplication
public class MbtileImageServerApplication { 

	public static void main(String[] args) {
		
		SpringApplication.run(MbtileImageServerApplication.class, args);
	}
}
