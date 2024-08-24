package Model;
import java.sql.*;
import javax.sql.DataSource;

public class AdministradorDAO {
	private DataSource dataSource;
	
	public AdministradorDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	  public administradorModel autenticar(String email, String senha) {
	        
	        Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultado = null;
			administradorModel admin = null;

	        try {
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement("SELECT * FROM administradores WHERE email = ? AND senha = ?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, senha);
				resultado = preparedStatement.executeQuery();

	            if (resultado.next()) {
	            	int id = resultado.getInt("id");
	            	String nome = resultado.getString("nome");
	            	email = resultado.getString("email");
	            	senha = resultado.getString("senha");

	            	admin = new administradorModel(id, nome, email, senha);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
				fecharConexao(connection, preparedStatement, null);
			}

	        return admin;
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
