package com.xgw.wwx.service;

import com.xgw.wwx.dto.jna.DesSliceLocationDTO;
import com.xgw.wwx.dto.jna.MaskSliceLocationDTO;

public interface JnaService {

	public int maskfileExpansion(String fileName);

	public DesSliceLocationDTO desSlice(String mapKey,int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc);

	public MaskSliceLocationDTO maskSlice(String mapKey,int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc);

	public MaskSliceLocationDTO maskSliceNext(String mapKey,int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc);

	public DesSliceLocationDTO desSliceNext(String mapKey,int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc);

	public DesSliceLocationDTO desSlice(int jmtype, int cardnum, long speed, int sunzinum, DesSliceLocationDTO sliceLoc);

	public MaskSliceLocationDTO maskSlice(int jmtype, int cardnum, long speed, int sunzinum, MaskSliceLocationDTO sliceLoc);

}
