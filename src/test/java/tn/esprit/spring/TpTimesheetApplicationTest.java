package tn.esprit.spring;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
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
private ContratRepository crep;
	@Autowired
private EmployeServiceImpl employeservices;
	@Autowired
private	DepartementRepository drep;
	@Autowired
private	IEntrepriseService entrepriseservice;
	
	
	private static final Logger l = LoggerFactory.getLogger(TpTimesheetApplicationTest.class);
	@Autowired
		@Test
	public void testAjouterContrat() {
		try{
	     l.info("In testAjouterContrat:");
         List <Contrat> contrats = (List<Contrat>) crep.findAll();
         int existant = contrats.size();
         l.info("nombre de contrat: " + existant);
         String date1="7/05/2020";  
         Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(date1);  
         Contrat cont =new Contrat(date2,"temp",700);
         l.info("l'ajout du contrat : " + cont);
         int refContrat = employeservices.ajouterContrat(cont);
	     l.info("comparaison size avant et apres l'ajout.");
         assertEquals(existant+1, ((List<Contrat>) crep.findAll()).size());
         l.info("assurer que la reference du contrat n est pas nulle.");
         assertNotNull(refContrat);
         l.info("vider la base de donneé.");
         crep.deleteAll();
         employerep.deleteAll();
		}
		catch (Exception e) {l.error("Erreur dans testAjouterContrat() : " + e);
		}
		}
		
	   @Autowired
		@Test
	   public void testAffecterContratAEmploye() {
	 		try{
	 			 l.info("In testAffecterEmployeADepartement():");
	 			 Employe emp =new Employe("Mohamed","Rezgui","123@gmail.com",false,Role.INGENIEUR);
	 			 l.info("creation d'un employe"+emp);
	 			 String date1="7/05/2020";  
	 	         Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(date1);  
	 	         Contrat cont =new Contrat(date2,"temp",700);
	 			 l.info("creation d'un contrat"+cont);
	 			 int refContrat=employeservices.ajouterContrat(cont);
	 		     l.info("ajouter le contrat dans la base de deonneés");
	 			 int idEmploye = employeservices.ajouterEmploye(emp);
	 			 l.info("ajouter l'emplye dans la base de deonneés");
	 			 Contrat C1=crep.findById(refContrat).get();
	 		   	 l.info("trouver le contrat grace a sa reference: "+C1);
	 	         assertNotNull(C1);
	 	         l.info("verifier si le contrat existe");
	 			 employeservices.affecterContratAEmploye(refContrat,idEmploye);
	 			 l.info("affecter le contrat a l employe");
	 			 Contrat C2=crep.findByEmployeId(idEmploye).get();
	 			 l.info("verifier si l employe est affecte "+C2);
	 	         l.info("vider la base de donneé.");
	 	         crep.deleteAll();
	 	         employerep.deleteAll();

	 		}
	 		catch (Exception e) {l.error("testAffecterContratAEmploye() : " + e);}
	          }
	   @Autowired
		@Test
		public void testdeleteContratById() {
		   try{
				l.info("In testdeleteContratById():");
				l.info("On verife le nombre de contrat existant");
		         List <Contrat> contrats = (List<Contrat>) crep.findAll();
		         int existant = contrats.size();
		        l.info("nombre de contrat: " + existant);
				l.info("Je vais ajouter un contrat");
				 String date1="7/05/2020";  
	 	         Date date2=new SimpleDateFormat("dd/MM/yyyy").parse(date1);  
	 	         Contrat cont =new Contrat(date2,"temp",700);
	 	         int refContrat=employeservices.ajouterContrat(cont);
	 	         l.info("Je vais supprimer l'entreprise.");
	 	         employeservices.deleteContratById(refContrat);
	 	         l.info("On assure que le contrat est supprimé en comparant les tailles.");
	 	         assertEquals(existant, ((List<Contrat>) crep.findAll()).size());
	 	         l.info("donc testdeleteContratById()() sans erreurs.");
			}
		   catch (Exception e) { l.error("testdeleteContratById() : " + e); }
	   }

}


