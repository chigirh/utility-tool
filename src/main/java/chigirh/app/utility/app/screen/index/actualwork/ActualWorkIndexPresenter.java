package chigirh.app.utility.app.screen.index.actualwork;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation1Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation2Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcationService;
import chigirh.app.utility.app.domain.actualwork.ActualWorkGroupEntity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkService;
import chigirh.app.utility.common.prop.FxmlProperties;
import chigirh.app.utility.javafx.component.UtlChoiceBox;
import chigirh.app.utility.javafx.component.UtlLabel;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import chigirh.app.utility.javafx.component.UtlTextField;
import chigirh.app.utility.javafx.presenter.PresenterBase;
import chigirh.app.utility.javafx.util.JavaFxBindingUtils;
import chigirh.app.utility.javafx.window.WindowFactory;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.RequiredArgsConstructor;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class ActualWorkIndexPresenter extends PresenterBase {

	private static final double HEIGHT = 30.0;

	ActualWorkIndexViewModel vm = new ActualWorkIndexViewModel();

	/*
	 * 一覧タブ
	 */

	@FXML
	private ScrollPane awScroll;
	@FXML
	private VBox awBox;
	@FXML
	private TextField awAddTf;
	@FXML
	private Button awAddBt;

	/*
	 * 登録タブ
	 */
	@FXML
	private ScrollPane classifcationScroll;
	@FXML
	private UtlTextField classification1AddTf;
	@FXML
	private UtlTextField classification2AddTf;
	@FXML
	private UtlChoiceBox<ActualWorkClassifcation1Entity> classification1AddCb;
	@FXML
	private UtlChoiceBox<ActualWorkClassifcation1Entity> classification1RemoveCb;
	@FXML
	private UtlChoiceBox<ActualWorkClassifcation2Entity> classification2RemoveCb;

	final WindowFactory windowFactory;

	final FxmlProperties fxmlProperties;

	final ActualWorkService actualWorkService;

	final ActualWorkClassifcationService classifcationService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		awScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		classifcationScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

		JavaFxBindingUtils.bindingNode(classification1AddTf, vm.classifcation1AddProperty());
		JavaFxBindingUtils.bindingNode(classification1AddCb,
				vm.selectedClassifcation1DispProperty(),
				vm.classifcation1DispListPropery());
		JavaFxBindingUtils.bindingNode(classification2AddTf, vm.classifcation2AddProperty());
		JavaFxBindingUtils.bindingNode(classification1RemoveCb,
				vm.selectedClassifcation1RemoveCbProperty(),
				vm.classifcation1DispListPropery());
		JavaFxBindingUtils.bindingNode(classification2RemoveCb,
				vm.selectedClassifcation2RemoveCbProperty(),
				vm.classifcation2RemoveCbListPropery());

		vm.setClassifcation1DispPropertyList(classifcationService.classifcation1Get().stream()
				.map(this::classifcation1BeanMappr).collect(Collectors.toList()));

		vm.selectedClassifcation1RemoveCbProperty().addListener(this::selectedRemoveClassifcation1Change);

	}

	@Override
	public void onStart() {
		listUupdate();
	}

	private void listUupdate() {
		awBox.getChildren().clear();
		awBox.getChildren()
				.addAll(actualWorkService.awGroupGet().stream().map(this::creteRow).collect(Collectors.toList()));

	}

	private HBox creteRow(ActualWorkGroupEntity entity) {

		Insets mergin = new Insets(0.0, 10.0, 0.0, 0.0);

		HBox row = new HBox();
		VBox.setMargin(row, new Insets(2.0, 0, 2.0, 0));
		UtlLabel title = new UtlLabel(entity.getAwGroupName());
		title.setPrefWidth(300.0);
		title.setPrefHeight(HEIGHT);
		HBox.setMargin(title, mergin);
		row.getChildren().add(title);

		Button addbutton = new Button("開く");
		addbutton.setPrefHeight(HEIGHT);
		HBox.setMargin(addbutton, mergin);
		addbutton.setOnAction(
				e -> windowFactory.createWindow(fxmlProperties.getActualWork(), entity).resizable(false).show());

		Button remButton = new Button("削除");
		remButton.setPrefHeight(HEIGHT);
		HBox.setMargin(remButton, mergin);
		remButton.setOnAction(e -> {
			actualWorkService.awGroupDelete(entity.getAwGroupId());
			listUupdate();
		});

		row.getChildren().add(addbutton);
		row.getChildren().add(remButton);

		return row;
	}

	@FXML
	public void onAwAdd(ActionEvent e) {
		String groupName = awAddTf.getText();

		if (StringUtils.isEmpty(groupName)) {
			return;
		}

		ActualWorkGroupEntity entity = actualWorkService.awGroupAdd(groupName, null);
		if (entity == null) {
			return;
		}
		awAddTf.setText("");
		awBox.getChildren().add(creteRow(entity));
	}

	@FXML
	public void onClassification1AddAction(ActionEvent e) {
		if (StringUtils.isEmpty(vm.getClassifcation1Add())) {
			return;
		}
		ActualWorkClassifcation1Entity addEntity = classifcationService.classifcation1Add(vm.getClassifcation1Add());
		vm.setClassifcation1Add("");
		vm.classifcation1DispListPropery().get().add(classifcation1BeanMappr(addEntity));
	}

	@FXML
	public void onClassification2AddAction(ActionEvent e) {
		if (vm.getSelectedClassifcation1Disp() == null) {
			return;
		}
		ActualWorkClassifcation2Entity addEntity = classifcationService
				.classifcation2Add(vm.getSelectedClassifcation1Disp().getValue().getId(), vm.getClassifcation2Add());
		vm.setClassifcation2Add("");
		if (Objects.equals(vm.getSelectedClassifcation1Disp(),
				vm.getSelectedClassifcation1RemoveCb())) {
			vm.classifcation2RemoveCbListPropery().get().add(classifcation2BeanMappr(addEntity));
		}
	}

	@FXML
	public void onClassification1RemoveAction(ActionEvent e) {
		classifcationService.classifcation1Delete(vm.getSelectedClassifcation1RemoveCb().getValue().getId());
		vm.setSelectedClassifcation1RemoveCb(null);
		vm.setClassifcation1DispPropertyList(classifcationService.classifcation1Get().stream()
				.map(this::classifcation1BeanMappr).collect(Collectors.toList()));
		vm.setSelectedClassifcation2RemoveCb(null);
		vm.setClassifcation2RemoveCbPropertyList(new ArrayList<>());

	}

	@FXML
	public void onClassification2RemoveAction(ActionEvent e) {
		classifcationService.classifcation2Delete(vm.getSelectedClassifcation2RemoveCb().getValue().getId());
		vm.setSelectedClassifcation2RemoveCb(null);
		selectedRemoveClassifcation1Change(null, null, vm.getSelectedClassifcation1RemoveCb());
	}

	private UtlLabelValueBean<ActualWorkClassifcation1Entity> classifcation1BeanMappr(
			ActualWorkClassifcation1Entity entity) {
		return new UtlLabelValueBean<>(entity.getName(), entity);
	}

	private UtlLabelValueBean<ActualWorkClassifcation2Entity> classifcation2BeanMappr(
			ActualWorkClassifcation2Entity entity) {
		return new UtlLabelValueBean<>(entity.getName(), entity);
	}

	private void selectedRemoveClassifcation1Change(
			ObservableValue<? extends UtlLabelValueBean<ActualWorkClassifcation1Entity>> observable,
			UtlLabelValueBean<ActualWorkClassifcation1Entity> oldValue,
			UtlLabelValueBean<ActualWorkClassifcation1Entity> newValue) {
		if (newValue == null) {
			return;
		}
		vm.setClassifcation2RemoveCbPropertyList(
				classifcationService.findByclassifcation1(newValue.getValue().getId()).stream()
						.map(this::classifcation2BeanMappr).collect(Collectors.toList()));
	}

}
