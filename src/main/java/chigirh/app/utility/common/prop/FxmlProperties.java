package chigirh.app.utility.common.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="javafx.fxml")
@Data
public class FxmlProperties {

	private String normalWindow;

	private String rootIndex;

	private String actualWorkIndex;

	private String taskManagerIndex;

	private String shortcutIndex;

	private String actualWork;

	private String actualWorkTable;

	private String taskManager;

	private String taskManagerTable;

}
