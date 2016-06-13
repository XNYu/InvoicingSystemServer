package data.billdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import utility.DateHelper;
import PO.BillPO;
import PO.CostBillPO;
import PO.PaymentBillPO;
import PO.ReceivingBillPO;
import dataservice.billDataService.BillDataService;

public class BillData extends UnicastRemoteObject implements BillDataService,Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<BillPO> list;
	ArrayList<ReceivingBillPO> relist = new ArrayList<ReceivingBillPO>();
	ArrayList<PaymentBillPO> paylist = new ArrayList<PaymentBillPO>();
	ArrayList<CostBillPO> colist = new ArrayList<CostBillPO>();
	String path;

	public BillData() throws RemoteException {
		super();
		path="Data/billdata.ser";
		File file = new File(path);
		try {
			if (!file.exists()) {
				file.createNewFile();
				list= new ArrayList<BillPO>();
				System.out.println(list.size());
				save();
			}else{
				show();
			}

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void getlists(){
		try {
			this.show();
		} catch (RemoteException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		relist = new ArrayList<ReceivingBillPO>();
		paylist = new ArrayList<PaymentBillPO>();
		colist = new ArrayList<CostBillPO>();
		for(int i =0;i<list.size();i++){
			switch(list.get(i).getNumberID().substring(0, 3)){
			case "SKD":  relist.add((ReceivingBillPO) list.get(i));break;
			case "FKD":  paylist.add((PaymentBillPO) list.get(i));break;
			case "XJF":  colist.add((CostBillPO) list.get(i));break;

		}
		}
	}

	@Override
	public boolean add(ReceivingBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		getlists();
		relist.add(bill);
		save();

		return true;
	}

	@Override
	public boolean add(PaymentBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		getlists();
		paylist.add(bill);
		save();
		return true;
	}

	@Override
	public boolean add(CostBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		getlists();
		colist.add(bill);
		save();
		getlists();
		return true;
	}

	@Override
	public boolean modify(ReceivingBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		boolean flag = false;
		getlists();
		for(ReceivingBillPO p:relist){
			if(p.getNumberID().equals(bill.getNumberID())){
				flag = true;
				p.setCus(bill.getCus());
				p.setExamined(bill.isExamined());
				p.setRead(bill.isRead());
				p.setModified(bill.isModified());
				p.setNumberID(bill.getNumberID());
				p.setUsername(bill.getUsername());
				p.setSum(bill.getSum());
				p.setTransferList(bill.getTransferList());
				save();

			}
		}
		return flag;
	}

	@Override
	public boolean modify(PaymentBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		boolean flag = false;
		getlists();
		for(PaymentBillPO p:paylist){
			if(p.getNumberID().equals(bill.getNumberID())){
				flag = true;
				p.setCus(bill.getCus());
				p.setExamined(bill.isExamined());
				p.setRead(bill.isRead());
				p.setModified(bill.isModified());
				p.setNumberID(bill.getNumberID());
				p.setUsername(bill.getUsername());
				p.setSum(bill.getSum());
				p.setTransferList(bill.getTransferList());
				save();

			}
		}
		return flag;
	}

	@Override
	public boolean modify(CostBillPO bill) throws RemoteException {
		// TODO 自动生成的方法存根
		boolean flag = false;
		getlists();
		for(CostBillPO p:colist){
			if(p.getNumberID().equals(bill.getNumberID())){
				flag = true;
				p.setAcc(bill.getAcc());
				p.setExamined(bill.isExamined());
				p.setRead(bill.isRead());
				p.setModified(bill.isModified());
				p.setNumberID(bill.getNumberID());
				p.setUsername(bill.getUsername());
				p.setSum(bill.getSum());
				p.setEntryList(bill.getEntryList());
				save();

			}
		}
		return flag;
	}


	@Override
	public ArrayList<BillPO> getBillExamined() throws RemoteException {
		// TODO 自动生成的方法存根
		ArrayList<BillPO> resultlist = new ArrayList<BillPO>();
		getlists();

		for(BillPO po: list){
			if(po.isExamined()&&po.isRead()==false){
				resultlist.add(po);
			}
		}

		return resultlist;
	}


	@Override
	public BillPO find(String s) throws RemoteException {
		// TODO 自动生成的方法存根
		//需要模糊化查询
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BillPO> show() throws RemoteException {
		// TODO 自动生成的方法存根
		 try{
	            @SuppressWarnings("resource")
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/billdata.ser"));
	            list = (ArrayList<BillPO>) in.readObject();
	        }catch(FileNotFoundException e){
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		return list;
	}



	void save(){
		 try {
	            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/billdata.ser"));
	            mix();
	            out.writeObject(list);
	            out.close();


	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}

	void mix(){
		//合并三个list 并排序
		list = new ArrayList<BillPO>();
		for(ReceivingBillPO rec:relist){
			list.add(rec);
		}
		for(PaymentBillPO pay:paylist){
			list.add(pay);
		}
		for(CostBillPO cost:colist){
			list.add(cost);
		}
		for(int i =0 ;i<list.size();i++){
			for(int j =i+1 ;j<list.size();){
				if(list.get(i).getNumberID().equals(list.get(j).getNumberID()))
					list.remove(j);
				else
					j++;
			}
		}

	}

	@Override
	public void finish() throws RemoteException {
		// TODO 自动生成的方法存根

	}

	@Override
	public void delAll() throws RemoteException {
		// TODO 自动生成的方法存根
		relist = new ArrayList<ReceivingBillPO>();
		paylist = new ArrayList<PaymentBillPO>();
		colist = new ArrayList<CostBillPO>();
		save();
	}

	@Override
	public String getBillID(String type) throws RemoteException {
		// TODO 自动生成的方法存根
		getlists();
		String ID = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat df1 = new DecimalFormat("00000");
		int num=0;
		for(BillPO po:list){
			String s = po.getNumberID();
			String [] ids = s.split("-");

			if(ids[0].equals(type)&&ids[1].equals(df.format(new Date()))){
				num++;
			}

		}



		switch(type){
		case "SKD":ID = "SKD-"+df.format(new Date())+"-"+df1.format(num);break;
		case "FKD":ID = "FKD-"+df.format(new Date())+"-"+df1.format(num);break;
		case "XJFYD":ID = "XJFYD-"+df.format(new Date())+"-"+df1.format(num);break;
		}

		return ID;
	}



	public ArrayList<String> getUserlistOfSKD() throws RemoteException{
		getlists();

		ArrayList<String> usernamelist = new ArrayList<String>();

		for(ReceivingBillPO po:relist){
			String s = po.getUsername();
			boolean flag = true;
			for(int i=0;i<usernamelist.size();i++){
				if(usernamelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				usernamelist.add(s);
			}
		}

		return usernamelist;

	}

	public ArrayList<String> getCustomerlistOfSKD() throws RemoteException{
		getlists();

		ArrayList<String> namelist = new ArrayList<String>();

		for(ReceivingBillPO po:relist){
			String s = po.getCus().getName();
			boolean flag = true;
			for(int i=0;i<namelist.size();i++){
				if(namelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				namelist.add(s);
			}
		}

		return namelist;

	}

	public ArrayList<ReceivingBillPO> getSKDListForExamined() throws RemoteException{
		getlists();

		ArrayList<ReceivingBillPO> skdlist = new ArrayList<ReceivingBillPO>();

		for(ReceivingBillPO po:relist){
			if(!po.isExamined()){
				skdlist.add(po);
			}
		}

		return skdlist;

	}

	public ArrayList<String> getUserlistOfFKD() throws RemoteException{
		getlists();

		ArrayList<String> usernamelist = new ArrayList<String>();

		for(PaymentBillPO po:paylist){
			String s = po.getUsername();
			boolean flag = true;
			for(int i=0;i<usernamelist.size();i++){
				if(usernamelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				usernamelist.add(s);
			}
		}

		return usernamelist;

	}

	public ArrayList<String> getCustomerlistOfFKD() throws RemoteException{
		getlists();

		ArrayList<String> namelist = new ArrayList<String>();

		for(PaymentBillPO po:paylist){
			String s = po.getCus().getName();
			boolean flag = true;
			for(int i=0;i<namelist.size();i++){
				if(namelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				namelist.add(s);
			}
		}

		return namelist;

	}

	public ArrayList<PaymentBillPO> getFKDListForExamined() throws RemoteException{
		getlists();

		ArrayList<PaymentBillPO> fkdlist = new ArrayList<PaymentBillPO>();

		for(PaymentBillPO po:paylist){
			if(!po.isExamined()){
				fkdlist.add(po);
			}
		}

		return fkdlist;

	}


	public ArrayList<String> getUserlistOfXJFYD() throws RemoteException{
		getlists();

		ArrayList<String> usernamelist = new ArrayList<String>();

		for(CostBillPO po:colist){
			String s = po.getUsername();
			boolean flag = true;
			for(int i=0;i<usernamelist.size();i++){
				if(usernamelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				usernamelist.add(s);
			}
		}

		return usernamelist;

	}

	public ArrayList<String> getAccountlistOfXJFYD() throws RemoteException{
		getlists();

		ArrayList<String> namelist = new ArrayList<String>();

		for(CostBillPO po:colist){
			String s = po.getAcc().getName();
			boolean flag = true;
			for(int i=0;i<namelist.size();i++){
				if(namelist.get(i).equals(s)){
					flag = false;
					break;
				}
			}
			if(flag){
				namelist.add(s);
			}
		}

		return namelist;

	}

	public ArrayList<CostBillPO> getXJFYDListForExamined() throws RemoteException{
		getlists();

		ArrayList<CostBillPO> xjfydlist = new ArrayList<CostBillPO>();

		for(CostBillPO po:colist){
			if(!po.isExamined()){
				xjfydlist.add(po);
			}
		}

		return xjfydlist;

	}

	public ArrayList<ReceivingBillPO> searchSKD(Date date1,Date date2,String username,String customer) throws RemoteException {
		getlists();

		ArrayList<ReceivingBillPO> resultlist = new ArrayList<ReceivingBillPO>();
		DateFormat fmt =new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		for(ReceivingBillPO po:relist){
			String []ids = po.getNumberID().split("-");
			try {
				date = fmt.parse(ids[1]);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(DateHelper.timeCompare(date1, date, date2)&&po.getUsername().equals(username)&&po.getCus().getName().equals(customer)){
				resultlist.add(po);
			}
		}

		return resultlist;
	}

	public ArrayList<PaymentBillPO> searchFKD(Date date1,Date date2,String username,String customer) throws RemoteException {
		getlists();

		ArrayList<PaymentBillPO> resultlist = new ArrayList<PaymentBillPO>();
		DateFormat fmt =new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		for(PaymentBillPO po:paylist){
			String []ids = po.getNumberID().split("-");
			try {
				date = fmt.parse(ids[1]);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(DateHelper.timeCompare(date1, date, date2)&&po.getUsername().equals(username)&&po.getCus().getName().equals(customer)){
				resultlist.add(po);
			}
		}

		return resultlist;
	}

	public ArrayList<CostBillPO> searchXJFYD(Date date1,Date date2,String username,String account) throws RemoteException {
		getlists();

		ArrayList<CostBillPO> resultlist = new ArrayList<CostBillPO>();
		DateFormat fmt =new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		for(CostBillPO po:colist){
			String []ids = po.getNumberID().split("-");
			try {
				date = fmt.parse(ids[1]);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			if(DateHelper.timeCompare(date1, date, date2)&&po.getUsername().equals(username)&&po.getAcc().getName().equals(account)){
				resultlist.add(po);
			}
		}

		return resultlist;
	}




}
