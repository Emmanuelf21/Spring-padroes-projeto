package one.digitalinnovation.gof.service.impl;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Conta;
import one.digitalinnovation.gof.model.ContaRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService{
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        //encontra todos os clientes
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
    }
    @Override
    public void sacar(Long id, String numeroConta, Double valor) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            //String numeroConta = cliente.getConta().getNumero();
            Conta conta = verificarConta(numeroConta, clienteBd);
            conta.setSaldo(conta.getSaldo() - valor);
            clienteBd.get().setConta(conta);
            clienteRepository.save(clienteBd.get());
        }
        
    }
    @Override
    public void depositar(Long id, String numeroConta, Double valor){
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            //String numeroConta = cliente.getConta().getNumero();
            Conta conta = verificarConta(numeroConta, clienteBd);
            valor += conta.getSaldo();
            conta.setSaldo(valor);
            clienteBd.get().setConta(conta);
            clienteRepository.save(clienteBd.get());
        }
    }

    private Conta verificarConta(String numeroConta, Optional<Cliente> clienteBd) {
        Conta conta = contaRepository.findById(numeroConta).orElseGet(() ->{
            Conta novaConta = clienteBd.get().getConta();
            contaRepository.save(novaConta);
            return novaConta;
        });
        return conta;
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private void salvarClienteComCep(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        String numeroConta = cliente.getConta().getNumero();
        //procura o cep do cliente no banco, se não encontrar salva um novo endereco
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() ->{
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        Conta conta = contaRepository.findById(numeroConta).orElseGet(() ->{
            Conta novaConta = cliente.getConta();
            contaRepository.save(novaConta);
            return novaConta;
        });

        cliente.setEndereco(endereco);
        cliente.setConta(conta);
        clienteRepository.save(cliente);
    }
}
