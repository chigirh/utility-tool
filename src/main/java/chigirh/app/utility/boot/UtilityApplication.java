package chigirh.app.utility.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.dbinit.DataBaseItializer;
import chigirh.app.utility.javafx.window.NormarWIndow;
import chigirh.app.utility.javafx.window.WindowFactory;
import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication(scanBasePackages = "chigirh.app.utility")
@EntityScan("chigirh.app.utility.app.domain")
@EnableJpaRepositories("chigirh.app.utility.app.mapper")
public class UtilityApplication extends Application {

	@Override
	public void start(Stage arg0) throws Exception {

		//データベースの初期化
		DataBaseItializer dbItializer = new DataBaseItializer();
		ApplicationContextSupport.getContext().getAutowireCapableBeanFactory().autowireBean(dbItializer);
		dbItializer.init();

		String fxml = ((FxmlProperties) ApplicationContextSupport.getContext().getBean(FxmlProperties.class))
				.getRootIndex();

		WindowFactory wIndowFactory = (WindowFactory) ApplicationContextSupport.getContext()
				.getBean(WindowFactory.class);
		NormarWIndow wIndow = wIndowFactory.createWindow(fxml);
		wIndow.show();
	}

	public static void main(String[] args) {
		ApplicationContextSupport.set(SpringApplication.run(UtilityApplication.class, args));
		launch(args);
	}
}
