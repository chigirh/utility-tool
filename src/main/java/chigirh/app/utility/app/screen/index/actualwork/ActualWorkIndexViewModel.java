package chigirh.app.utility.app.screen.index.actualwork;

import java.util.ArrayList;
import java.util.List;

import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation1Entity;
import chigirh.app.utility.app.domain.actualwork.ActualWorkClassifcation2Entity;
import chigirh.app.utility.javafx.component.UtlLabelValueBean;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
* 自動生成.
* <p>
* @author chigirh.app.utility.tools.RowAutoCreater
*/
public class ActualWorkIndexViewModel {

	private StringProperty classifcation1AddProperty = new SimpleStringProperty(this, "classifcation1Add");

	private ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassifcation1DispProperty = new SimpleObjectProperty<>(
			this, "selectedClassifcation1DispProperty");

	private ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation1Entity>>> classifcation1DispPropertyListPropery = new SimpleObjectProperty<>(
			this, "classifcation1DispPropertyListPropery", FXCollections.observableArrayList(new ArrayList<>()));

	private StringProperty classifcation2AddProperty = new SimpleStringProperty(this, "classifcation2Add");

	private ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassifcation1RemoveCbProperty = new SimpleObjectProperty<>(
			this, "selectedClassifcation1RemoveCbProperty");

	private ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation2Entity>> selectedClassifcation2RemoveCbProperty = new SimpleObjectProperty<>(
			this, "selectedClassifcation2RemoveCbProperty");

	private ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation2Entity>>> classifcation2RemoveCbPropertyListPropery = new SimpleObjectProperty<>(
			this, "classifcation2RemoveCbPropertyListPropery", FXCollections.observableArrayList(new ArrayList<>()));

	public StringProperty classifcation1AddProperty() {
		return classifcation1AddProperty;
	}

	public void setClassifcation1Add(String classifcation1Add) {
		classifcation1AddProperty.set(classifcation1Add);
	}

	public String getClassifcation1Add() {
		return classifcation1AddProperty.get();
	}

	public ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassifcation1DispProperty() {
		return selectedClassifcation1DispProperty;
	}

	public void setSelectedClassifcation1Disp(
			UtlLabelValueBean<ActualWorkClassifcation1Entity> selectedClassifcation1Disp) {
		selectedClassifcation1DispProperty.set(selectedClassifcation1Disp);
	}

	public UtlLabelValueBean<ActualWorkClassifcation1Entity> getSelectedClassifcation1Disp() {
		return selectedClassifcation1DispProperty.get();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation1Entity>>> classifcation1DispListPropery() {
		return classifcation1DispPropertyListPropery;
	}

	public void setClassifcation1DispPropertyList(
			List<UtlLabelValueBean<ActualWorkClassifcation1Entity>> classifcation1DispPropertyList) {
		classifcation1DispPropertyListPropery.set(FXCollections.observableArrayList(classifcation1DispPropertyList));
	}

	public StringProperty classifcation2AddProperty() {
		return classifcation2AddProperty;
	}

	public void setClassifcation2Add(String classifcation2Add) {
		classifcation2AddProperty.set(classifcation2Add);
	}

	public String getClassifcation2Add() {
		return classifcation2AddProperty.get();
	}

	public ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation1Entity>> selectedClassifcation1RemoveCbProperty() {
		return selectedClassifcation1RemoveCbProperty;
	}

	public void setSelectedClassifcation1RemoveCb(
			UtlLabelValueBean<ActualWorkClassifcation1Entity> selectedClassifcation1RemoveCb) {
		selectedClassifcation1RemoveCbProperty.set(selectedClassifcation1RemoveCb);
	}

	public UtlLabelValueBean<ActualWorkClassifcation1Entity> getSelectedClassifcation1RemoveCb() {
		return selectedClassifcation1RemoveCbProperty.get();
	}

	public ObjectProperty<UtlLabelValueBean<ActualWorkClassifcation2Entity>> selectedClassifcation2RemoveCbProperty() {
		return selectedClassifcation2RemoveCbProperty;
	}

	public void setSelectedClassifcation2RemoveCb(
			UtlLabelValueBean<ActualWorkClassifcation2Entity> selectedClassifcation2RemoveCb) {
		selectedClassifcation2RemoveCbProperty.set(selectedClassifcation2RemoveCb);
	}

	public UtlLabelValueBean<ActualWorkClassifcation2Entity> getSelectedClassifcation2RemoveCb() {
		return selectedClassifcation2RemoveCbProperty.get();
	}

	public ObjectProperty<ObservableList<UtlLabelValueBean<ActualWorkClassifcation2Entity>>> classifcation2RemoveCbListPropery() {
		return classifcation2RemoveCbPropertyListPropery;
	}

	public void setClassifcation2RemoveCbPropertyList(
			List<UtlLabelValueBean<ActualWorkClassifcation2Entity>> classifcation2RemoveCbPropertyList) {
		classifcation2RemoveCbPropertyListPropery
				.set(FXCollections.observableArrayList(classifcation2RemoveCbPropertyList));
	}
}
