package br.com.kaique.techchallenge.services;

import br.com.kaique.techchallenge.dtos.LoginRequestDTO;
import br.com.kaique.techchallenge.dtos.SaveUsuarioRequestDTO;
import br.com.kaique.techchallenge.dtos.UpdateUsuarioRequestDTO;
import br.com.kaique.techchallenge.entities.Usuario;
import br.com.kaique.techchallenge.repositories.UsuarioRepository;
import br.com.kaique.techchallenge.services.exceptions.DuplicateEmailException;
import br.com.kaique.techchallenge.services.exceptions.DuplicateLoginException;
import br.com.kaique.techchallenge.services.exceptions.InvalidCredentialsException;
import br.com.kaique.techchallenge.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public void saveUsuario(SaveUsuarioRequestDTO usuarioDTO) {

        if (usuarioRepository.existsByEmail(usuarioDTO.email())) {
            throw new DuplicateEmailException("Email já existe");
        }

        if (usuarioRepository.existsByLogin(usuarioDTO.login())) {
            throw new DuplicateLoginException("Login já existe");
        }

        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTO.nome());
        usuario.setEndereco(usuarioDTO.endereco());
        usuario.setEmail(usuarioDTO.email());
        usuario.setLogin(usuarioDTO.login());
        usuario.setSenha(usuarioDTO.senha());
        usuario.setTipoUsuario(usuarioDTO.tipoUsuario());

        this.usuarioRepository.save(usuario);
    }

    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findUsuarioByNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Usuario updateUsuario(UpdateUsuarioRequestDTO updateUsuarioRequestDTO, Long idUsuario) {

        Usuario usuario = findById(idUsuario);

        if (!usuario.getEmail().equals(updateUsuarioRequestDTO.email()) && usuarioRepository.existsByEmail(updateUsuarioRequestDTO.email())) {
            throw new DuplicateEmailException("Email já existe");
        }

        if (!usuario.getLogin().equals(updateUsuarioRequestDTO.login()) && usuarioRepository.existsByLogin(updateUsuarioRequestDTO.login())) {
            throw new DuplicateLoginException("Login já existe");
        }

        usuario.setNome(updateUsuarioRequestDTO.nome());
        usuario.setEmail(updateUsuarioRequestDTO.email());
        usuario.setLogin(updateUsuarioRequestDTO.login());
        usuario.setEndereco(updateUsuarioRequestDTO.endereco());
        usuario.setTipoUsuario(updateUsuarioRequestDTO.tipoUsuario());

        return usuarioRepository.save(usuario);
    }

    public void updateSenha(Long idUsuario, String senha) {

        Usuario usuario = findById(idUsuario);

        usuario.setSenha(senha);

        usuarioRepository.save(usuario);
    }

    public Usuario login(LoginRequestDTO loginRequestDTO) {
        return usuarioRepository.findByLoginAndSenha(
                        loginRequestDTO.login(),
                        loginRequestDTO.senha()
                ).orElseThrow(() -> new InvalidCredentialsException("Login ou senha incorreto"));
    }

    public void deleteUsuario(Long idUsuario) {
        Usuario usuario = findById(idUsuario);

        usuarioRepository.delete(usuario);
    }

    public Usuario findById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID:  " + idUsuario));
    }
}
