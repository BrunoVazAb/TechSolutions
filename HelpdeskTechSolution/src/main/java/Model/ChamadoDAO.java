package Model;
import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

public class ChamadoDAO {
	private DataSource dataSource;
	
	public ChamadoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean registrarChamado(int usuario_id, String prioridade,String status, String descricao, java.util.Date data_abertura) {
		int resultado;

       
        Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
        	connection = dataSource.getConnection();
        	preparedStatement = connection.prepareStatement("INSERT INTO chamados (usuario_id, prioridade, status, descricao, data_abertura) VALUES (?, ?, ?, ?, ?)");
        	preparedStatement.setInt(1, usuario_id);
        	preparedStatement.setString(2, prioridade);
        	preparedStatement.setString(3, status);
        	preparedStatement.setString(4, descricao);
        	
        	java.sql.Date dataSQL = new java.sql.Date(data_abertura.getTime());
        	
            preparedStatement.setDate(5, dataSQL);
              
			resultado = preparedStatement.executeUpdate();
        }catch (SQLException e) {
    	    e.printStackTrace();
    	    System.err.println("Erro ao inserir chamado: " + e.getMessage());
    	    resultado = 0;
    	}finally {
    		fecharConexao(connection, preparedStatement, null);
    	}
    	
    	return resultado == 1;
    }
	
	public ArrayList<chamadosModel> listaChamadosCliente(int clienteID){
		ArrayList<chamadosModel> chamados = new ArrayList<chamadosModel>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			 
			preparedStatement = connection.prepareStatement("SELECT * FROM chamados WHERE usuario_id = ?");
			preparedStatement.setInt(1, clienteID);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				int id = resultSet.getInt("id");
				int usuario_id = resultSet.getInt("usuario_id");
				int tecnico_id = resultSet.getInt("tecnico_id");
				String prioridade = resultSet.getString("prioridade");
				String status = resultSet.getString("status");
				String descricao = resultSet.getString("descricao");
				Date data_abertura = resultSet.getDate("data_abertura");
				Date data_fechamento= resultSet.getDate("data_fechamento");
				
				chamados.add(new chamadosModel(id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao(connection, preparedStatement, resultSet);
		}
		
		return chamados;
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
