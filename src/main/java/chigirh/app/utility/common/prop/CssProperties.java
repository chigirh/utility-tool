package chigirh.app.utility.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="javafx.css")
@Data
public class CssProperties {

	private String common;

}
