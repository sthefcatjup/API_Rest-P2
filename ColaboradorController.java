import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    // Cria um novo colaborador
    @PostMapping
    public ResponseEntity<Colaborador> createColaborador(@RequestBody Colaborador colaborador) {
        Colaborador novoColaborador = colaboradorRepository.save(colaborador);
        return ResponseEntity.ok(novoColaborador);
    }

    // Lista todos os colaboradores
    @GetMapping
    public List<Colaborador> getAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    // Obtém detalhes de um colaborador específico, incluindo as tarefas atribuídas
    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> getColaboradorById(@PathVariable Long id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        return colaborador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualiza os dados de um colaborador
    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> updateColaborador(@PathVariable Long id, @RequestBody Colaborador colaboradorDetails) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        if (colaborador.isPresent()) {
            Colaborador existingColaborador = colaborador.get();
            existingColaborador.setNome(colaboradorDetails.getNome());

            Colaborador updatedColaborador = colaboradorRepository.save(existingColaborador);
            return ResponseEntity.ok(updatedColaborador);
        }
        return ResponseEntity.notFound().build();
    }

    // Exclui um colaborador
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        Optional<Colaborador> colaborador = colaboradorRepository.findById(id);
        if (colaborador.isPresent()) {
            colaboradorRepository.delete(colaborador.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
