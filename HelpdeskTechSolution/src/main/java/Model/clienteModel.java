package Model;

public class clienteModel extends usuariosModel{
	private String tipo;

	public clienteModel(int id, String nome, String email, String senha, String tipo) {
		super(id, nome, email, senha);
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



}
