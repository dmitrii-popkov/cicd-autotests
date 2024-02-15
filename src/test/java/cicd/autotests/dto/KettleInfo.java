package cicd.autotests.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class KettleInfo {
	private String id;
	private SwitchMode switchMode;
	private int temperature;
}
