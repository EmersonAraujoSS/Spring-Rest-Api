package br.com.avancard.controller;

import br.com.avancard.model.Telefone;
import br.com.avancard.model.TelefoneDTO;
import br.com.avancard.model.Usuario;
import br.com.avancard.repository.TelefoneRepository;
import br.com.avancard.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin //Liberação de acesso ao meu controller com o CrossOrigin (Esse meu controller pode acessado de qualquer lugar)
@RestController
@RequestMapping(value = "/usuario") // RequestMapping = uso para indicar qual endpoint vai acessar esse controller
public class IndexController {

    @Autowired //injeção de dependência // se fosse CDI a anotação seria @Inject
    private UsuarioRepository usuarioRepository;

    @Autowired //injeção de dependência
    private TelefoneRepository telefoneRepository;


    @GetMapping(value = "/", produces = "application/json")  //defaultValue = irá usar esse parâmetro caso eu não passe um nome de parâmetro
    public ResponseEntity init(@RequestParam(value = "nome", defaultValue = "o nome precisa ser informado") String nome, @RequestParam(value = "salario") Long salario){ // ResponseEntity = retorna uma resposta HTTP com um objeto que encapsula a lista de usuários e o status HTTP.
        return new ResponseEntity("Teste Spring boot api\n" +
                                            "Nome usuário: " + nome +
                                            "\nSalário: " + salario, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/codigovenda/{venda}", produces = "application/pdf")
    public ResponseEntity<Usuario> relatorio(@PathVariable(value = "id") Long id, @PathVariable(value = "venda") Long venda){

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return new ResponseEntity<>(usuario.get(), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Usuario> buscaPorId(@PathVariable(value = "id") Long id){

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/listar", produces = "application/json")
    public ResponseEntity<List<Usuario>> listar(){

        List<Usuario> userList = (List<Usuario>) usuarioRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json") //nesse método, se eu não passar o id, ele já gera automaticamente um usuário com o id
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario){

        for(int pos = 0; pos < usuario.getTelefone().size(); pos++){
            usuario.getTelefone().get(pos).setUsuario(usuario);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
    }

    @PutMapping(value = "/", produces = "application/json") //nesse método eu preciso informar qual o id que eu quero atualizar na chamada
    public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario){

        for(int pos = 0; pos < usuario.getTelefone().size(); pos++){
            usuario.getTelefone().get(pos).setUsuario(usuario);
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deletar(@PathVariable (value = "id") Long id){

        usuarioRepository.deleteById(id);
        return "Deletado!";
    }

    @PostMapping(value = "/telefone", produces = "application/json")
    public ResponseEntity<Telefone> cadastraTelefone(@RequestBody TelefoneDTO telefoneDTO){

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(telefoneDTO.getUsuario());

        if(!usuarioOpt.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Se o usuário não for encontrado
        }

        Usuario usuario = usuarioOpt.get(); //busca o usuário

        Telefone telefone = new Telefone(); //cria o objeto telefone e define o usuário
        telefone.setNumero(telefoneDTO.getNumero());
        telefone.setTipo(telefoneDTO.getTipo());
        telefone.setUsuario(usuario);

        //salva
        Telefone telefoneSalvo = telefoneRepository.save(telefone);
        return new ResponseEntity<>(telefoneSalvo, HttpStatus.OK);
    }
}
