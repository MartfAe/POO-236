package br.edu.ufrb.gcet236.equipe_fornecedores_final.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.edu.ufrb.gcet236.equipe_fornecedores_final.Fornecedor;
import br.edu.ufrb.gcet236.equipe_fornecedores_final.ListaFornecedor;
import br.edu.ufrb.gcet236.equipe_fornecedores_final.FornecedorRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value= "/fornecedores")
public class FornecedoresController {

// Define a mensagem como uma constante
private static final String FORNECEDOR_NAO_ENCONTRADO = "O fornecedor procurado não se encontra no banco de dados";


  @Autowired
  private FornecedorRepository fornecedorRepository;


  @PostMapping(value = "/add")
  public void setFornecedor(@RequestBody Fornecedor fornecedor) {
    fornecedorRepository.save(fornecedor);
  }

  @GetMapping(value = "/all")
  public List<Fornecedor> getAll() {
    return fornecedorRepository.findAll();
  }

  @GetMapping(value = "/search/cnpj:{cnpj}")
  public Object searchByCNPJ(@PathVariable String cnpj) {
    Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj);
    if (fornecedor != null) {
      return fornecedor;
    }
    return FORNECEDOR_NAO_ENCONTRADO;
  }

  @GetMapping(value = "/search/name:{name}")
  public Object searchByName(@PathVariable String name) {
    Fornecedor fornecedor = fornecedorRepository.findByNome(name);
    if (fornecedor != null) {
      return fornecedor;
    }
    return FORNECEDOR_NAO_ENCONTRADO;
  }

  @GetMapping(value = "/search/part:{name}")
  public List<Fornecedor> searchByPartOfName(@PathVariable String name) {
    return fornecedorRepository.findByNomeContainingIgnoreCase(name);
  }

  @GetMapping(value = "/nameandcnpj")
  public List<String> nameAndCNPJ() {
    List<Fornecedor> fornecedores = fornecedorRepository.findAll();
    List<String> nomeECNPJs = new ArrayList<>();

    for (Fornecedor fornecedor : fornecedores) {
      String nomeECNPJ = "Nome: " + fornecedor.getNome() + "; CNPJ: " + fornecedor.getCnpj();
      nomeECNPJs.add(nomeECNPJ);
    }

    return nomeECNPJs;
  }

  @DeleteMapping(value = "/remove/cnpj:{cnpj}")
  public Object deleteByCNPJ(@PathVariable String cnpj) {
    Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj);
    if (fornecedor != null) {
      fornecedorRepository.delete(fornecedor);
      return fornecedor;
    }
    return FORNECEDOR_NAO_ENCONTRADO
  }

  @DeleteMapping(value = "/remove/nome:{nome}")
  public Object deleteByName(@PathVariable String nome) {
    Fornecedor fornecedor = fornecedorRepository.findByNome(nome);
    if (fornecedor != null) {
      fornecedorRepository.delete(fornecedor);
      return fornecedor;
    }
    return FORNECEDOR_NAO_ENCONTRADO;
  }

  @DeleteMapping(value = "/remove/parte:{parteDoNome}")
  public Object deleteByPartName(@PathVariable String parteDoNome) {
    List<Fornecedor> fornecedores = fornecedorRepository.findByNomeContainingIgnoreCase(parteDoNome);
    if (!fornecedores.isEmpty()) {
      Fornecedor fornecedor_removido = fornecedores.get(0);
      fornecedorRepository.delete(fornecedor_removido);
      return fornecedor_removido;
    }
    return FORNECEDOR_NAO_ENCONTRADO;
  }

  @PutMapping(value = "/update/cnpj:{cnpj}")
  public void atualizarFornecedorPorCNPJ(@PathVariable String cnpj, @RequestBody Fornecedor fornecedor) {
    Fornecedor fornecedorExistente = fornecedorRepository.findByCnpj(cnpj);
    if (fornecedorExistente != null) {
      fornecedorExistente.setNome(fornecedor.getNome());
      fornecedorExistente.setEndereco(fornecedor.getEndereco());
      fornecedorExistente.setTelefone(fornecedor.getTelefone());
      fornecedorExistente.setEmail(fornecedor.getEmail());
      fornecedorRepository.save(fornecedorExistente);
    }
  }

  @PutMapping(value = "/update/nome:{nome}")
  public void atualizarFornecedorPorNome(@PathVariable String nome, @RequestBody Fornecedor fornecedor) {
    Fornecedor fornecedorExistente = fornecedorRepository.findByNome(nome);
    if (fornecedorExistente != null) {
      fornecedorExistente.setCnpj(fornecedor.getCnpj());
      fornecedorExistente.setEndereco(fornecedor.getEndereco());
      fornecedorExistente.setTelefone(fornecedor.getTelefone());
      fornecedorExistente.setEmail(fornecedor.getEmail());
      fornecedorRepository.save(fornecedorExistente);
    }
  }
}
