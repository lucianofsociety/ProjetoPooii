package br.ulbra.controller;

import br.ulbra.model.Usuario;
import br.ulbra.model.UsuarioDAO;
import javax.swing.JOptionPane;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean autenticar(String email, String senha) {
        if (usuarioDAO.autenticar(email, senha)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Usuario ou senha incorreta! ");
            return false;
        }
    }

    public boolean adicionarUsuario(String nome, String email, String senha, String datanasc, int ativo) {
        return usuarioDAO.adicionarUsuario(nome, email, senha, datanasc, ativo);
    }
    
    public List<Usuario> readForDesc(String desc){
        return usuarioDAO.readForDesc(desc);
    }
}
