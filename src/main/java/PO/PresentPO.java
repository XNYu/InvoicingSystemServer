package PO;

import java.io.Serializable;
import java.util.ArrayList;

import utility.DateHelper;
import utility.ResultMessage;

public class PresentPO implements Serializable{
	ArrayList<CommodityPO> commoditylist;
	String customer;
	String examine="未审批";
	public String ID;
	String createdDate="";

	
	public PresentPO(ArrayList<CommodityPO> commoditylist,
			String customer) {
		super();
		this.commoditylist = commoditylist;
		this.customer = customer;
		DateHelper dh = new DateHelper();
		createdDate = dh.getDate();
	}
	public ArrayList<CommodityPO> getCommoditylist() {
		return commoditylist;
	}
	public void setCommoditylist(ArrayList<CommodityPO> commoditylist) {
		this.commoditylist = commoditylist;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getExamine() {
		return examine;
	}
	public void setExamine(String examine) {
		this.examine = examine;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
