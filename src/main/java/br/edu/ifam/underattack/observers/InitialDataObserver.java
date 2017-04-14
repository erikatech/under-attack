package br.edu.ifam.underattack.observers;

import br.com.caelum.vraptor.events.VRaptorInitialized;
import br.edu.ifam.underattack.model.Ingrediente;
import br.edu.ifam.underattack.util.JPAUtil;

import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitialDataObserver {

	/**
	 * Esse é um exemplo simples de observer do CDI com VRaptor 4
	 * 
	 * Ele é utilizado para inserir um usuário e alguns produtos
	 * sempre que a app é startada, pois estamos usando um banco
	 * em memória. Você pode ler mais a respeito de observers em:
	 *  
	 * http://www.vraptor.org/pt/docs/eventos/*/

	public void prepare(@Observes VRaptorInitialized event) {
		EntityManager em = JPAUtil.criaEntityManager();
		TypedQuery<Ingrediente> query = em.createQuery(
				"select i from Ingrediente i", Ingrediente.class);
		List<Ingrediente> ingredientesToRemove = query.getResultList();

		if(ingredientesToRemove.isEmpty()){
			List<Ingrediente> ingredientesToInsert = this.criaIngredientes();
			for(Ingrediente ingrediente : ingredientesToInsert){
				em.getTransaction().begin();
				em.persist(ingrediente);
				em.getTransaction().commit();
			}
		}
		em.close();
	}

	private List<Ingrediente> criaIngredientes() {
		List<Ingrediente> ingredientes = new ArrayList<>();
		Ingrediente cogumelo = new Ingrediente();
		cogumelo.setDescricao("Mucho loco cogumelo");
		cogumelo.setNomeImagem("cogumelo");

		Ingrediente cabeloGoku = new Ingrediente();
		cabeloGoku.setDescricao("Fio de cabelo do Goku");
		cabeloGoku.setNomeImagem("cabelo-goku");

		Ingrediente espada = new Ingrediente();
		espada.setDescricao("Espada");
		espada.setNomeImagem("espada");

		ingredientes.addAll(Arrays.asList(cogumelo, cabeloGoku, espada));
		return ingredientes;
	}
}