package com.backendprojetointegrador.lajeDev.domain.service;

import com.backendprojetointegrador.lajeDev.common.PasswordEncoder;
import com.backendprojetointegrador.lajeDev.domain.exception.RecursoNaoEncontrado;
import com.backendprojetointegrador.lajeDev.domain.model.Role;
import com.backendprojetointegrador.lajeDev.domain.model.Usuario;
import com.backendprojetointegrador.lajeDev.domain.repository.IRoleRepository;
import com.backendprojetointegrador.lajeDev.domain.repository.IUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean criarUsuario(Usuario usuario) {
        boolean usuarioExiste = usuarioRepository.findByEmail(usuario.getEmail()).stream()
                .anyMatch((usuarioExistente) -> !usuarioExistente.equals(usuario));

        if (!usuarioExiste) {
            BCryptPasswordEncoder bCrypt = passwordEncoder.bCryptPasswordEncoder();
            usuario.setSenha(bCrypt.encode(usuario.getSenha()));

            Optional<Role> roleOptional = roleRepository.findByNome("CLIENT");
            if (usuario.getRoles() == null && roleOptional.isPresent()) {
                usuario.setRoles(List.of(roleOptional.get()));
            }

            usuarioRepository.save(usuario);
            return usuarioExiste;
        }

        return usuarioExiste;
    }

    public List<Role> listarRoles() {
        return roleRepository.findAll();
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuario(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public void excluirUsuario(Long idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNaoEncontrado("Usuário com o id " + idUsuario + " não existe!");
        }

        usuarioRepository.deleteById(idUsuario);
    }
}
