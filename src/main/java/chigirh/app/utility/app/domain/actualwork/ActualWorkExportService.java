package chigirh.app.utility.app.domain.actualwork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import chigirh.app.utility.common.prop.ActualWorkProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActualWorkExportService {

	final ActualWorkClassifcationService actualWorkClassifcationService;

	final ActualWorkService actualWorkService;

	final ActualWorkProperties actualWorkProperties;

	@RequiredArgsConstructor
	@Data
	private static class ExportBean {

		private final String classifcation1;

		private final String classifcation2;

		private final String taskName;

		private double time = 0;

	}

	@Transactional
	public void export(final String awGroupId) {

		Map<String, ExportBean> groupByMap = new HashMap<>();

		actualWorkService.awGet(awGroupId).stream()
				.flatMap(e -> actualWorkService.awTaskGet(e.getAwId()).stream())//
				.forEach(e -> groupBy(e, groupByMap));

		try (PrintWriter pw = new PrintWriter(
				new BufferedWriter(new FileWriter(
						actualWorkProperties.getOutput() + "/"
								+ actualWorkService.awGroupGet(awGroupId).get().getAwGroupName() + ".txt",
						false)));) {

			for (ExportBean exportBean : groupByMap.values()) {

				StringBuilder sb = new StringBuilder();
				sb.append(StringUtils.isEmpty(exportBean.getClassifcation1())?""://
					actualWorkClassifcationService.classifcation1Get(exportBean.getClassifcation1()).getName());
				sb.append("|");

				sb.append(StringUtils.isEmpty(exportBean.getClassifcation2())?""://
					actualWorkClassifcationService.classifcation2Get(exportBean.getClassifcation2()).getName());
				sb.append("|");

				sb.append(StringUtils.isEmpty(exportBean.getTaskName())?""://
					exportBean.getTaskName());
				sb.append("|");

				sb.append(exportBean.getTime() + "H");

				pw.println(sb.toString());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String groupByKey(ActualWorkTaskEntity entity) {
		return new StringBuilder(entity.getClassification1())//
				.append(entity.getClassification2())//
				.append(entity.getTaskName()).toString();
	}

	private void groupBy(ActualWorkTaskEntity entity, Map<String, ExportBean> groupByMap) {
		final String key = groupByKey(entity);
		if (!groupByMap.containsKey(key)) {
			groupByMap.put(key,
					new ExportBean(//
							entity.getClassification1(), //
							entity.getClassification2(), //
							entity.getTaskName()));
		}
		ExportBean exportBean = groupByMap.get(key);
		exportBean.setTime(exportBean.getTime() + entity.getTaskTime());
	}

}
