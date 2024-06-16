package one.digitalinnovation.gof.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;

import one.digitalinnovation.gof.model.Endereco;

//define o path da api
@FeignClient(name="viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {
    
    // faz um mapeamento usando o método get e pega um valor json através de um cep
    //@RequestMapping(method = RequestMethod.GET, value = "/{cep}/json/")
    @GetMapping("/{cep}/json/")
    Endereco consultarCep(@PathVariable("cep") String cep);
}
