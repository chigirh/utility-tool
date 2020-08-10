package chigirh.app.utility.common.prop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:fxml.properties","classpath:data.properties","classpath:css.properties"})
public class PropertiesConfiguration {


	@Bean
	public FxmlProperties fxmlProperties() {
		return new FxmlProperties();
	}

	@Bean
	public CssProperties cssProperties() {
		return new CssProperties();
	}


	@Bean
	public DataProperties dataProperties() {
		return new DataProperties();
	}

}
