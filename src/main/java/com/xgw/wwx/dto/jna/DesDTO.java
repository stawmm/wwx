package com.xgw.wwx.dto.jna;

public class DesDTO {

	private long id;

	private int jmtype;

	private int cardnum;

	private long speed;

	private int sunzinum;

	private DesSliceLocationDTO sliceLoc;

	private DesSliceLocationDTO result;

	public int getJmtype() {
		return jmtype;
	}

	public void setJmtype(int jmtype) {
		this.jmtype = jmtype;
	}

	public int getCardnum() {
		return cardnum;
	}

	public void setCardnum(int cardnum) {
		this.cardnum = cardnum;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public int getSunzinum() {
		return sunzinum;
	}

	public void setSunzinum(int sunzinum) {
		this.sunzinum = sunzinum;
	}

	public DesSliceLocationDTO getSliceLoc() {
		return sliceLoc;
	}

	public void setSliceLoc(DesSliceLocationDTO sliceLoc) {
		this.sliceLoc = sliceLoc;
	}

	public DesSliceLocationDTO getResult() {
		return result;
	}

	public void setResult(DesSliceLocationDTO result) {
		this.result = result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
