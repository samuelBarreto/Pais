package test;


import static org.junit.Assert.assertEquals;
import model.Pais;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import service.PaisService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClienteTest {
	Pais pais, copia;
	PaisService clienteService;
	static int id = 0;

	/*
	 * Antes de rodar este teste, certifique-se que nao ha no banco nenhuma
	 * linha com o id igual ao do escolhido para o to instanciado abaixo. Se
	 * houver, delete. 
	 * Certifique-se também que sobrecarregou o equals na classe
	 * Cliente. 
	 * Certifique-se que a fixture cliente1 foi criada no banco.
	 * Além disso, a ordem de execução dos testes é importante; por
	 * isso a anotação FixMethodOrder logo acima do nome desta classe
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais();
		pais.setId(id);
		pais.setNome("Batista Cepelos");
		pais.setPopulacao(0);
		pais.setArea(0);
		copia = new Pais();
		copia.setId(id);
		copia.setNome("Batista Cepelos");
		copia.setPopulacao(0);
		copia.setArea(0);
		clienteService = new PaisService();
		System.out.println(pais);
		System.out.println(copia);
		System.out.println(id);
	}
	
	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		//para funcionar o cliente 1 deve ter sido carregado no banco por fora
		Pais fixture = new Pais();
		fixture.setId(1);
		fixture.setNome("Carlos Drummond de Andrade");
		fixture.setPopulacao(0);
		fixture.setArea(0);
		PaisService novoService = new PaisService();
		Pais novo = novoService.carregar(1);
		assertEquals("testa inclusao", novo, fixture);
	}

	@Test
	public void test01Criar() {
		System.out.println("criar");
		id = clienteService.criar(pais);
		System.out.println(id);
		copia.setId(id);
		assertEquals("testa criacao", pais, copia);
	}

	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setPopulacao(0);
		copia.setArea(0);		
		clienteService.atualizar(pais);
		pais = clienteService.carregar(pais.getId());
		assertEquals("testa atualizacao", pais, copia);
	}

	@Test
	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome(null);
		copia.setPopulacao(0);
		copia.setArea(0);
		clienteService.excluir(id);
		pais = clienteService.carregar(id);
		assertEquals("testa exclusao", pais, copia);
	}
}