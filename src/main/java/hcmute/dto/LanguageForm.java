package hcmute.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageForm {
	private String id;
	private String name;

	private String createBy;
	private String lastUpdateBy;
	private String createDate;
	private String lastUpdateDate;
}
