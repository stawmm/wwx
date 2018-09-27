package com.xgw.wwx.dto.request;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DeviceRespDTO extends BaseRespDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("IP")
	private String ip;

	@SerializedName("Mac")
	private String mac;

	@SerializedName("Nodetype")
	private Integer nodeType;

	@SerializedName("Cardnum")
	private Integer cardNum;

	@SerializedName("Algnum")
	private Integer algNum;

	@SerializedName("Dicnum")
	private Integer dictNum;

	@SerializedName("Cardlist")
	private List<CardRespDTO> cardlist;

	@SerializedName("Alglist")
	private String alglist;

	@SerializedName("Diclist")
	private List<DictRespDTO> dictList;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public Integer getAlgNum() {
		return algNum;
	}

	public void setAlgNum(Integer algNum) {
		this.algNum = algNum;
	}

	public Integer getDictNum() {
		return dictNum;
	}

	public void setDictNum(Integer dictNum) {
		this.dictNum = dictNum;
	}

	public List<CardRespDTO> getCardlist() {
		return cardlist;
	}

	public void setCardlist(List<CardRespDTO> cardlist) {
		this.cardlist = cardlist;
	}

	public List<DictRespDTO> getDictList() {
		return dictList;
	}

	public void setDictList(List<DictRespDTO> dictList) {
		this.dictList = dictList;
	}

	public String getAlglist() {
		return alglist;
	}

	public void setAlglist(String alglist) {
		this.alglist = alglist;
	}

}
