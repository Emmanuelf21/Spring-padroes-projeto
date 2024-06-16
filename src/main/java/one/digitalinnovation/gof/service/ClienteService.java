package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;

/*
 * define o padrão Strategy no domínio de cliente.
 */
public interface ClienteService {
    
    Iterable<Cliente> buscarTodos();
    
    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void depositar(Long id, String numeroConta, Double valor);
    
    void sacar(Long id, String numeroConta, Double valor);

    void deletar(Long id);
}
