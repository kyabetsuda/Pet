package com.Tsuda.springboot.Service;

import java.util.Comparator;
import java.util.List;

public class ReserveComparator implements Comparator<List<String>> {
	
	@Override
	public int compare(List<String> data1, List<String> data2) {
		int roomNum1 = Integer.parseInt(data1.get(0));
		int roomNum2 = Integer.parseInt(data2.get(0));
		if( roomNum1 < roomNum2 ) {
			return -1;
		}else {
			return 1;
		}
	}

}
