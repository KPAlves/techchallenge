package br.com.kaique.techchallenge.repositories;

import br.com.kaique.techchallenge.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);

}
