package chigirh.app.utility.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="app.actualwork")
@Data
public class ActualWorkProperties {

	private String output;

}
