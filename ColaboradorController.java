import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @GetMapping
    public List<Colaborador> getAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    @PostMapping
    public Colaborador createColaborador(@RequestBody Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> updateColaborador(@PathVariable Long id, @RequestBody Colaborador colaboradorDetails) {
        return colaboradorRepository.findById(id)
            .map(colaborador -> {
                colaborador.setNome(colaboradorDetails.getNome());
                return ResponseEntity.ok(colaboradorRepository.save(colaborador));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        return colaboradorRepository.findById(id)
            .map(colaborador -> {
                colaboradorRepository.delete(colaborador);
                return ResponseEntity.noContent().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
}
