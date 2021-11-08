package tn.esprit.spring;
import static org.junit.Assert.*;

import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TpTimesheetApplicationTest {
	@Autowired
private EmployeRepository employerep;
	@Autowired
private EmployeServiceImpl employeservices;
	@Autowired
private	DepartementRepository drep;
	@Autowired
private	IEntrepriseService entrepriseservice;
	
	
	private static final Logger l = LoggerFactory.getLogger(TpTimesheetApplicationTest.class);
	@Autowired
		@Test
	public void testAjouterEmploye() {
		try{
	     l.info("In testAjouterEmploye():");
         List <Employe> employes = employeservices.getAllEmployes();
         int expected = employes.size();
         l.info("nombre d'employes avant l'ajout: " + expected);
         Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
         l.info("l'ajout l'employe: " + emp);
         int id_employe = employeservices.ajouterEmploye(emp);
	     l.info("comparaison size avant et apres l'ajout.");
         assertEquals(expected+1, employeservices.getAllEmployes().size());
         l.info("assurer que l'Id de l'employer not null.");
         assertNotNull(id_employe);
         l.info("vider la base de donneé.");
         employerep.deleteAll();
		}
		catch (Exception e) {l.error("Erreur dans testAjouterEmploye() : " + e);}
         }

	   @Autowired
		@Test
	public void testMettreAjourEmailByEmployeId() {
		try{
			l.info("In testMettreAjourEmailByEmployeId():");
         Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
         l.info("ajouter un employe dans ola base de dannée "+emp);
         int id_employe = employeservices.ajouterEmploye(emp);
         l.info("prendre l'id de l'employe:"+id_employe);
         employeservices.mettreAjourEmailByEmployeId("aziz", id_employe);
         Employe emp_modifier =employerep.findById(id_employe).get();
         l.info("metre ajour l'adresse email de l'employe: "+emp);
         assertNotEquals(emp.getEmail(), emp_modifier.getEmail());
         l.info("verifier que email de employe" + emp+ "est modifier");
         employerep.deleteAll();
         l.info("vider la base de donneé.");
		}
		catch (Exception e) {l.error("Erreur dans testMettreAjourEmailByEmployeId() : " + e);}
         }
	   
	   @Autowired
	 		@Test
	 	public void testDeleteEmployeById() {
	 		try{
	 			 l.info("In testDeleteEmployeById():");
	 			 Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
	 			 l.info("ajouter un employe dans ola base de dannée "+emp);
	 	         int id_employe = employeservices.ajouterEmploye(emp);
	 	         l.info("prendre l'id de l'employe:"+id_employe);
	 			 List <Employe> employes = employeservices.getAllEmployes();
	 			 l.info("prendre la liste de tous les employes");
	 	         int expected = employes.size();
	 	         l.info("nombre d'employes apres l'ajout: " + expected);
	 	         employeservices.deleteEmployeById(id_employe);
	 	         l.info("delete l'employe"+emp);
	 	         assertEquals(expected-1, employeservices.getAllEmployes().size());
	 	         l.info("comparaison size avant et apres la supression.");
	 	         employerep.deleteAll();
	 	         l.info("vider la base de donneé.");
	 		}
	 		catch (Exception e) {l.error("Erreur dans testDeleteEmployeById() : " + e);}
	          }
	   //aziz
	   @Autowired
		@Test
	   public void testAffecterEmployeADepartement() {
	 		try{
	 			 l.info("In testAffecterEmployeADepartement():");
	 			 Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
	 			 l.info("creation d'un employe"+emp);
	 			 Departement D=new Departement("Ressources Humaines");
	 			 l.info("creation d'un departement"+D);
	 			 int id_departement=entrepriseservice.ajouterDepartement(D);
	 		     l.info("ajouter le departement dans la base de deonneés");
	 			 int id_employe = employeservices.ajouterEmploye(emp);
	 			 l.info("ajouter l'emplye dans la base de deonneés");
	 			 Departement D1=drep.findById(id_departement).get();
	 			 List <Employe> employes = D1.getEmployes();
	 		   	 l.info("prendre la liste des employe affecter a la departement: "+D1);
	 	         int expected = employes.size();
	 	         l.info("prendre la tail la liste des employe affecter a la departement");
	 			 employeservices.affecterEmployeADepartement(id_employe, id_departement);
	 			 l.info("affecter l'employe a un partement");
	 			 Departement D2=drep.findById(id_departement).get();
	 			 l.info("prendre la liste des employe affecter a la departement: "+D2);
	 			 assertEquals(expected+1, D2.getEmployes().size());
	 			 l.info("comparaison size avant et apres l'affectation.");
	 			 employerep.deleteAll();
	 			 l.info("vider la base de donneé."); 
	 		}
	 		catch (Exception e) {l.error("testAffecterEmployeADepartement() : " + e);}
	          }
	        @Autowired
	 		@Test
	   public void testDesaffecterEmployeDuDepartement() {
	 		try{
	 			l.info("In testDesaffecterEmployeDuDepartement():");
	 			 Employe emp =new Employe("aziz","charfeddine","azizcharfeddine@gmail.com",false,Role.INGENIEUR);
	 			 l.info("creation d'un employe"+emp);
	 			 Departement D=new Departement("Ressources Humaines");
	 			 l.info("creation d'un departement"+D);
	 			 int id_departement=entrepriseservice.ajouterDepartement(D);
	 			 l.info("ajouter le departement dans la base de deonneés");
	 			 int id_employe = employeservices.ajouterEmploye(emp);
	 			 l.info("ajouter le employe dans la base de deonneés");
	 			 employeservices.affecterEmployeADepartement(id_employe, id_departement);
	 		     l.info("affecter l'employe a un partement");
	 			 Departement D1=drep.findById(id_departement).get();
	 			 List <Employe> employes = D1.getEmployes();
	 			 l.info("prendre la liste des employe affecter a la departement: "+D1);
	 	         int expected = employes.size();
	 	         l.info("prendre la tail la liste des employe affecter a la departement");
	 	         employeservices.desaffecterEmployeDuDepartement(id_employe, id_departement);
	 	         l.info("desaffecter l'employe a un partement");
	 			 Departement D2=drep.findById(id_departement).get();
	 			 l.info("prendre la liste des employe affecter a la departement: "+D2);
	 			 assertEquals(expected, D2.getEmployes().size());
	 			 l.info("comparaison size avant et apres l'affectation.");
	 			 employerep.deleteAll();
	 			 l.info("vider la base de donneé."); 
	 		}
	 		catch (Exception e) {l.error("testDesaffecterEmployeDuDepartement() : " + e);}
	          }
}
