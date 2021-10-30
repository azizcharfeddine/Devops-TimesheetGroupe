package tn.esprit.spring;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.EmployeRepository;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TpTimesheetApplicationTests {
@Autowired
private EmployeRepository employerep;
//private static final Logger l =LogManager.getLogger(UserServiceImplTest.class);

	@Autowired
	
		@Test
		public void testAjouterEmploye() {
			try{
	    Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
	    Employe savedemploye = employerep.save(emp);
	    assertNotNull(savedemploye.getId());
			}
			catch (Exception e) {}
		}
}
