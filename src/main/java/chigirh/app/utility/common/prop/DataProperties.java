package chigirh.app.utility.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "data.access")
@Data
public class DataProperties {

	private String operationGroup;

	private String operation;

	private String operationTask;

}
