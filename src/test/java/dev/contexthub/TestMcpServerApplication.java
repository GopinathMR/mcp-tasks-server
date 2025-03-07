package dev.contexthub;

import org.springframework.boot.SpringApplication;

public class TestMcpServerApplication {

	public static void main(String[] args) {
		SpringApplication.from(McpServerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
