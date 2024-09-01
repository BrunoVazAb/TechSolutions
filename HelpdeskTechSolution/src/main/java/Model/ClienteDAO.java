package Model;
import java.sql.*;

import javax.sql.DataSource;

public class ClienteDAO {
	private DataSource dataSource;
	
	public ClienteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public clienteModel autenticarUsuario(String email, String senha) {
	        
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		clienteModel cliente = null;

	    try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM usuarios WHERE email = ? AND senha = ?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, senha);
			resultado = preparedStatement.executeQuery();

            if (resultado.next()) {
            	int id = resultado.getInt("id");
            	String nome = resultado.getString("nome");
            	email = resultado.getString("email");
            	senha = resultado.getString("senha");
            	String tipo = resultado.getString("tipo");

            	cliente = new clienteModel(id, nome, email, senha, tipo);
            }
	    	
	    }catch (SQLException e) {
            e.printStackTrace();
        }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return cliente;
	}
	
	private void fecharConexao(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if(connection != null)
			connection.close();
				
			if(preparedStatement != null)
			preparedStatement.close();
			
			if(resultSet != null)
			resultSet.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
