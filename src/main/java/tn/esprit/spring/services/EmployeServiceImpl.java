package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional <Employe> employe1 = employeRepository.findById(employeId);
		if (employe1.isPresent()) {
			Employe employe = employe1.get(); // no issue
			employe.setEmail(email);
			employeRepository.save(employe);
		}


	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if(depManagedEntity.getEmployes() == null){

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{

			depManagedEntity.getEmployes().add(employeManagedEntity);

		}
		}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional <Departement> dep1 = deptRepoistory.findById(depId);
		if (dep1.isPresent()) {
			
		Departement dep = dep1.get();
		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				dep.getEmployes().remove(index);
				break;
			}
		}
		}
	}

	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional <Contrat> contratManagedEntity1 = contratRepoistory.findById(contratId);
		Optional <Employe> employeManagedEntity1 = employeRepository.findById(employeId);
		if (contratManagedEntity1.isPresent() && employeManagedEntity1.isPresent()) {
		Contrat contratManagedEntity = contratManagedEntity1.get();
		Employe employeManagedEntity = employeManagedEntity1.get();
		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		}
	}

	public String getEmployePrenomById(int employeId) {
		Optional <Employe> employeManagedEntity1 = employeRepository.findById(employeId);
		if (employeManagedEntity1.isPresent()) {
			Employe employeManagedEntity= employeManagedEntity1.get();
			return employeManagedEntity.getPrenom();	
		}
		else {
			return "employee not found";
		}
	}
	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> employe1 = employeRepository.findById(employeId);
		
		if 	(employe1.isPresent()) {
			Employe employe = employe1.get();
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}
	}
	public void deleteContratById(int contratId) {
		Optional <Contrat> contratManagedEntity1 = contratRepoistory.findById(contratId);
		if (contratManagedEntity1.isPresent()) {
		Contrat contratManagedEntity = contratManagedEntity1.get();
		contratRepoistory.delete(contratManagedEntity);
		}
	}

	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
