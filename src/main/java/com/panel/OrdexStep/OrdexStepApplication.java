package com.panel.OrdexStep;

import com.panel.OrdexStep.Entity.Roles;
import com.panel.OrdexStep.Entity.UsuariosAdministradores;
import com.panel.OrdexStep.Repository.RolesRepository;
import com.panel.OrdexStep.Repository.UsuarioAdmiRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class OrdexStepApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdexStepApplication.class, args);
	}
}
