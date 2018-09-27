package com.xgw.wwx.common.helper;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class DictHelper {

	public static Long getDictSize(String dictFileName) {
		String fileBaseName = FilenameUtils.getBaseName(dictFileName);
		String[] nameArray = fileBaseName.split("_");
		String lastName = nameArray[nameArray.length - 1];
		if (NumberUtils.isDigits(lastName)) {
			return NumberUtils.toLong(lastName);
		}
		return 0l;
	}

}
