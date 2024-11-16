import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    // Cria uma nova tarefa e atribui colaboradores a ela
    @PostMapping
    public ResponseEntity<Tarefa> createTarefa(@RequestBody Tarefa tarefa) {
        Tarefa novaTarefa = tarefaRepository.save(tarefa);

        // Atribui colaboradores à tarefa
        if (tarefa.getColaboradores() != null) {
            for (Colaborador colaborador : tarefa.getColaboradores()) {
                colaborador.getTarefas().add(novaTarefa);
                colaboradorRepository.save(colaborador);
            }
        }
        return ResponseEntity.ok(novaTarefa);
    }

    // Lista todas as tarefas
    @GetMapping
    public List<Tarefa> getAllTarefas() {
        return tarefaRepository.findAll();
    }

    // Obtém detalhes de uma tarefa específica, incluindo os colaboradores
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> getTarefaById(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            return ResponseEntity.ok(tarefa.get());
        }
        throw new ResourceNotFoundException("Tarefa não encontrada com o id " + id);
    }

    // Atualiza uma tarefa, incluindo os colaboradores
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> updateTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaDetails) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            Tarefa existingTarefa = tarefa.get();
            existingTarefa.setTitulo(tarefaDetails.getTitulo());
            existingTarefa.setDescricao(tarefaDetails.getDescricao());
            existingTarefa.setDataInicio(tarefaDetails.getDataInicio());
            existingTarefa.setDataConclusao(tarefaDetails.getDataConclusao());

            // Atualiza os colaboradores
            existingTarefa.getColaboradores().clear();
            if (tarefaDetails.getColaboradores() != null) {
                for (Colaborador colaborador : tarefaDetails.getColaboradores()) {
                    colaborador.getTarefas().add(existingTarefa);
                }
            }

            Tarefa updatedTarefa = tarefaRepository.save(existingTarefa);
            return ResponseEntity.ok(updatedTarefa);
        }
        return ResponseEntity.notFound().build();
    }

    // Exclui uma tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefa(@PathVariable Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        if (tarefa.isPresent()) {
            tarefaRepository.delete(tarefa.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
