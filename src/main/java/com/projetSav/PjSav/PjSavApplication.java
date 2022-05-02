package com.projetSav.PjSav;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.projetSav.PjSav.dao.ClientRepository;
import com.projetSav.PjSav.dao.RoleRepository;
import com.projetSav.PjSav.model.Client;
import com.projetSav.PjSav.model.Role;
import com.projetSav.PjSav.model.Sexe;


@SpringBootApplication
public class PjSavApplication implements CommandLineRunner {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	RoleRepository roleRepository;

	private final Logger logger = LoggerFactory.getLogger(PjSavApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PjSavApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		logger.info("Loading data..." + args.toString());
		logger.info("Adding Rxoles ");
		roleRepository.save(new Role(1,"ROLE_ADMIN"));
		roleRepository.save(new Role(2,"ROLE_USER"));
		logger.info("Done Adding Roles ");
		logger.info("Adding Users : ");
		Client clt = new Client();
		//clt.setId(2);
		clt.setNom("hamojg");
		clt.setPrenom("gerpg");
		clt.setSexe(Sexe.HOMME);
		clt.setEmail("hamadi.moh@hotmail.com");
		clt.setPassword(new BCryptPasswordEncoder().encode("hamadi129"));
		clt.setDateNaiss(LocalDate.of(2020, 1, 28));
		clt.setTel("0665457176");
		clt.setTelFixe("0365459156");
		List<Role> listRoles = roleRepository.findAll();
		clt.setRoles(new HashSet<Role>(listRoles));	
		clientRepository.save(clt);
	*/
	}

}
