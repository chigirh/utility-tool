package chigirh.app.utility.dbinit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SqlIssuer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SqlIssuer.class);

	@Value("${spring.datasource.url}")
	String url;

	@Value("${spring.datasource.username}")
	String username;

	@Value("${spring.datasource.password}")
	String password;

	public boolean createTable(CreateTableSQL sql) {
		String createTableSQL = new SqlCreater(sql).create();

		LOGGER.info("SQL:{}",createTableSQL);
		try (Connection conn = DriverManager.getConnection(url, username, password);) {

			Statement stmt = conn.createStatement();

			return stmt.execute(createTableSQL);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
