package br.edu.ufrb.gcet236.equipe_fornecedores_final;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListaFornecedor {
  
  @Autowired
  private FornecedorRepository fornecedorRepository;

  public List<Fornecedor> getListaDeFornecedores() {
    return fornecedorRepository.findAll();
  }

  public void addFornecedor(Fornecedor fornecedor) {
    fornecedorRepository.save(fornecedor);
  }

  public List<Fornecedor> buscaPorParteDoNome(String parteDoNome) {
    return fornecedorRepository.findByNomeContainingIgnoreCase(parteDoNome);
  }

  public Fornecedor buscaPorCNPJ(String cnpj) {
    return fornecedorRepository.findByCnpj(cnpj);
  }

  public Fornecedor buscaPorNome(String nome) {
    return fornecedorRepository.findByNome(nome);
  }

  public List<String> listNameAndCnpj() {
    List<Fornecedor> fornecedores = fornecedorRepository.findAll();
    List<String> nomeECNPJs = new ArrayList<>();

    for (Fornecedor fornecedor : fornecedores) {
      String nomeECNPJ = "Nome: " + fornecedor.getNome() + "; CNPJ: " + fornecedor.getCnpj();
      nomeECNPJs.add(nomeECNPJ);
    }

    return nomeECNPJs;
  }

  public Fornecedor removePorCnpj(String cnpj) {
    Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj);
    if (fornecedor != null) {
      fornecedorRepository.delete(fornecedor);
    }
    return fornecedor;
  }

  public Fornecedor removePorNome(String nome) {
    Fornecedor fornecedor = fornecedorRepository.findByNome(nome);
    if (fornecedor != null) {
      fornecedorRepository.delete(fornecedor);
    }
    return fornecedor;
  }

  public Fornecedor removePorParteDoNome(String parteDoNome) {
    List<Fornecedor> fornecedores = fornecedorRepository.findByNomeContainingIgnoreCase(parteDoNome);
    if (!fornecedores.isEmpty()) {
      Fornecedor fornecedor = fornecedores.get(0);
      fornecedorRepository.delete(fornecedor);
      return fornecedor;
    }
    return null;
  }

  public void updatePorCnpj(String cnpj, Fornecedor novoFornecedor) {
    Fornecedor fornecedor = fornecedorRepository.findByCnpj(cnpj);
    if (fornecedor != null) {
      fornecedor.setNome(novoFornecedor.getNome());
      fornecedor.setEndereco(novoFornecedor.getEndereco());
      fornecedor.setTelefone(novoFornecedor.getTelefone());
      fornecedor.setEmail(novoFornecedor.getEmail());
      fornecedorRepository.save(fornecedor);
    }
  }

  public void updatePorNome(String nome, Fornecedor novoFornecedor) {
    Fornecedor fornecedor = fornecedorRepository.findByNome(nome);
    if (fornecedor != null) {
      fornecedor.setCnpj(novoFornecedor.getCnpj());
      fornecedor.setEndereco(novoFornecedor.getEndereco());
      fornecedor.setTelefone(novoFornecedor.getTelefone());
      fornecedor.setEmail(novoFornecedor.getEmail());
      fornecedorRepository.save(fornecedor);
    }
  }
}
