package com.renrendai.loan.beetle.commons.util.error;

import com.renrendai.loan.beetle.commons.util.Strings;
import org.springframework.util.StringUtils;

/**
 * 
 * 
 * @author beetle
 */
public interface ErrorCode {

	int getValue();
	
	String getName();
	
	default String getMessage() {
		return null;
	}
	
	default String[] getRequiredFields() {
		return null;
	}
	
	default String[] toArray(String keys) {
		if (StringUtils.isEmpty(keys)) {
			return null;
		}
		return Strings.splitWithTrim(keys);
	}
}
