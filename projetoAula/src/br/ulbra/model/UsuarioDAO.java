package br.ulbra.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    private GerenciadorConexao gerenciador;

    public UsuarioDAO() {
        this.gerenciador = GerenciadorConexao.getInstancia();
    }

    public boolean autenticar(String email, String senha) {
        String sql = "SELECT * from TBUSUARIO WHERE emailUsu = ? and senhaUsu = ? and ativoUsu =1";
        try {
            PreparedStatement stmt = gerenciador.getConexao().prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return false;
    }

    public boolean adicionarUsuario(String nome, String email, String senha, String datanasc, int ativo) {
        String sql = "INSERT INTO TBUSUARIO (nomeUsu, emailUsu, senhaUsu, datanascUsu, ativoUsu)"
                + "VALUES (?,?,?,?,?)";
        try {
            PreparedStatement stmt = gerenciador.getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setString(4, datanasc);
            stmt.setInt(5, ativo);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario: " + nome + " Inserido com sucesso! ");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro" + e.getMessage());
        }
        return false;
    }

    public List<Usuario> read() {
        String sql = "SELECT * FROM tbusuario";
        List<Usuario> usuarios = new ArrayList<>();

        Connection con = gerenciador.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();
                usuario.setPkUsuario(rs.getInt("pkusu"));
                usuario.setNomeUsu(rs.getString("nomeusu"));
                usuario.setEmailUsu(rs.getString("emailusu"));
                usuario.setSenhaUsu(rs.getString("senhausu"));
                usuario.setDataNasc(rs.getString("datanascusu"));
                usuario.setAtivoUsu(rs.getInt("ativousu"));

                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            GerenciadorConexao.closeConnection(con, stmt, rs);
        }
        return usuarios;
    }
    
    public List<Usuario> readForDesc(String desc){
        String sql = "SELECT * FROM tbusuario WHERE nomeusu LIKE ?";
        GerenciadorConexao gerenciador = GerenciadorConexao.getInstancia();
        Connection con = gerenciador.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario>  usuarios = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%"+desc+"%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()){
                
                Usuario usuario = new Usuario();
                
                usuario.setPkUsuario(rs.getInt("pkusuario"));
                usuario.setNomeUsu(rs.getString("nomeusu"));
                usuario.setEmailUsu(rs.getString("emailusu"));
                usuario.setSenhaUsu(rs.getString("senhausu"));
                usuario.setDataNasc(rs.getString("datanascusu"));
                usuario.setAtivoUsu(rs.getInt("ativousu"));
                usuarios.add(usuario);
            }
        }catch (SQLException ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE,null, ex);
        }finally {
            GerenciadorConexao.closeConnection(con, stmt, rs);
        }
        
        return usuarios;
    }
    
    public Usuario readForPk(int pk){
        String sql = "SELECT * FROM tbusuario WHERE pkusuario = ?";
        GerenciadorConexao gerenciador = GerenciadorConexao.getInstancia();
        Connection con = gerenciador.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario  usuario = new Usuario();
        
        try {
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, pk);
            
            rs = stmt.executeQuery();
            
            while (rs.next()){
                
                
                
                usuario.setPkUsuario(rs.getInt("pkusuario"));
                usuario.setNomeUsu(rs.getString("nomeusu"));
                usuario.setEmailUsu(rs.getString("emailusu"));
                usuario.setSenhaUsu(rs.getString("senhausu"));
                usuario.setDataNasc(rs.getString("datanascusu"));
                usuario.setAtivoUsu(rs.getInt("ativousu"));
                
            }
        }catch (SQLException ex){
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE,null, ex);
        }finally {
            GerenciadorConexao.closeConnection(con, stmt, rs);
        }
        
        return usuario;
    }
    
    public boolean alterarUsuario(Usuario u){
        GerenciadorConexao gerenciador = GerenciadorConexao.getInstancia();
        Connection con = gerenciador.getConexao();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("UPDATE tbUsuario SET nomeusu = ?,emailusu = ?,"
                    +"senhausu = ?, datanascusu = ?, ativousu = ? WHERE pkusuario = ?");
            stmt.setString(1,u.getNomeUsu());
            stmt.setString(2,u.getEmailUsu());
            stmt.setString(3,u.getSenhaUsu());
            stmt.setString(4,u.getDataNasc());
            stmt.setInt(5,u.getAtivoUsu());
            stmt.setInt(6,u.getPkUsuario());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizacao com sucesso! ");
            return true;
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar : " + ex);
        }finally{
            GerenciadorConexao.closeConnection(con, stmt);
        }
        return false;
    }
    
     public boolean excluirUsuario(int pkUsuario){
        GerenciadorConexao gerenciador = GerenciadorConexao.getInstancia();
        Connection con = gerenciador.getConexao();
        PreparedStatement stmt = null;
        
        try{
            stmt = con.prepareStatement("DELETE FROM tbUsuario  WHERE pkusuario = ?");
            
            stmt.setInt(1, pkUsuario);
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Excluir com sucesso! ");
            return true;
            
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro ao excluir : " + ex);
        }finally{
            GerenciadorConexao.closeConnection(con, stmt);
        }
        return false;
    }


}
