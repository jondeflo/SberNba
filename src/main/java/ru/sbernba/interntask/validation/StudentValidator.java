package ru.sbernba.interntask.validation;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StudentValidator {

	public boolean validateRequestData(Map<String, String> requestData)
	{
		if (requestData.size() > 1 && requestData.size() != 3)
			return false;
		if (!requestData.containsKey("name") && !requestData.containsKey("surname") && !requestData.containsKey("age"))
			return false;
		if (requestData.size() > 1 && !requestData.containsKey("orderby") && !requestData.containsKey("dir"))
			return false;
		if (requestData.containsKey("orderby") && (!requestData.get("orderby").equals("name") && !requestData.get("orderby").equals("surname") && !requestData.get("orderby").equals("age")))
			return false;
		if (requestData.containsKey("dir") && (!requestData.get("dir").equals("asc") && !requestData.get("dir").equals("desc")))
			return false;
		if (requestData.containsKey("age")){
			try{
				int numberCheck = Integer.parseInt(requestData.get("age"));
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}
}
