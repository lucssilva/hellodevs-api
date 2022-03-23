import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.javalin.Javalin;

public class App {
	public static String URL = "";
	public static String NOME_BANCO = "";
	public static String USUARIO = "";
	public static String SENHA = "";

	public static void main(String[] args) {
		Javalin app = Javalin.create();
		app.start(7070);
		app.get("/", ctx -> ctx.result("Hello devs"));
		app.get("/aluno/{id}", ctx -> ctx.result(getAlunoById(ctx.pathParam("id"))));
	}

	public static String getAlunoById(String id) throws SQLException {
		System.out.println("ID: " + id);
		Connection conexao = null;
		try {
			Class.forName("org.postgresql.Driver");
			conexao = DriverManager.getConnection("jdbc:postgresql://" + URL + NOME_BANCO, USUARIO, SENHA);
			ResultSet aluno = conexao.createStatement().executeQuery("SELECT * FROM alunos WHERE id=" + id);
			aluno.next();
			return aluno.getString("nome");

		} catch (ClassNotFoundException e) {
			System.out.println(e);

		} catch (SQLException e) {
			System.out.println(e);
			
		}
		finally {
			if (conexao != null) {
				conexao.close();
			}
		}
		return "Sem sucesso na conexão";
	}
}