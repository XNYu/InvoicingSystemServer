package data.commoditydata;

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
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import utility.ShowMessageFrame;
import dataservice.commodityDataService.CommodityDataService;
import PO.*;


public class CommodityData extends UnicastRemoteObject implements CommodityDataService, Serializable{
	private static final long serialVersionUID = 1L;
	ArrayList<CommodityPO> comList ;
	ArrayList<CommodityTypePO> typeList;
	ArrayList<StockPO> stockList;
	public CommodityData() throws RemoteException {
		super();
		this.load();
		
	}

	// load the data
	public void load() throws RemoteException {
		
		try{  
			//commodity======================================================================
			File fvck = new File("Data/commodityData.ser");
			if(fvck.exists()){
            ObjectInputStream in = new ObjectInputStream(
            		new FileInputStream("Data/commodityData.ser"));  
            comList = (ArrayList<CommodityPO>) in.readObject();  
            in.close();
			}else{
            	comList=new ArrayList<CommodityPO>();
			}
			
			//commodity type==============================================================
			File fvck2 = new File("Data/typeData.ser");
			if(fvck2.exists()){
            ObjectInputStream in2 = new ObjectInputStream(
            		new FileInputStream("Data/typeData.ser"));    
            typeList = (ArrayList<CommodityTypePO>) in2.readObject();  
            in2.close();
			}else{
            	typeList=new ArrayList<CommodityTypePO>();
            	CommodityTypePO p = new CommodityTypePO("全部商品分类");
				typeList.add(p);
			}
			
			//stock==============================================================================
			File fvck3 = new File("Data/stockData.ser");
			if(fvck3.exists()){
            ObjectInputStream in3 = new ObjectInputStream(
            		new FileInputStream("Data/stockData.ser"));    
            stockList = (ArrayList<StockPO>) in3.readObject();  
            in3.close();
			}else{
				stockList=new ArrayList<StockPO>();
			}
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
	}

	//auto-generated commodity ID
	public String createComID(int n){
		int j = n/10;
		String back = "";
		for(int k = 0;k<6-j;k++)
			back+="0";
		back+=String.valueOf(n);
		return back;
	}
	//add a commmodity
	public boolean add(String name,String type,int amount,double imp,double exp,String typeName) throws RemoteException {

		for(CommodityPO pp:comList){
			if(name.equals(pp.getName())&&type.equals(pp.getType()))
				return false;
		}
		
		CommodityPO po = new CommodityPO(name,type,amount,imp,exp,typeName);
		String typeID = this.getTypeID(typeName);
		String subComID="";
		for(CommodityTypePO p: typeList){
			if(typeName.equals(p.getType())){
				// if the category has a sub-category , result in faliure.
				System.out.println("size "+p.child.size());
				if(p.child.size()!=0)
					return false;
				p.setHasCommodity(true);
				
				subComID = this.createComID(p.numOfCommodity);
				p.numOfCommodity++;
				p.realNumOfCommodity++;
				break;
			}
		}
		typeID += subComID;
		po.setID(typeID);
		
		for(CommodityPO p: comList){
			
			if(p.getID().equals(po.getID())){
				
				for(CommodityTypePO pp: typeList){
					if(typeName.equals(pp.getType())){
						pp.numOfCommodity--;
						break;
					}
				}
				return false;
			}
		}
		comList.add(po);
		save();
		return true;
	}

	//delete a commodity
	public boolean del(String ID) throws RemoteException {
		for(CommodityPO p:comList){
			if(p.getID().equals(ID)){
				comList.remove(p);
				String category = p.getCommodityType();
				for(CommodityTypePO pp : typeList){
					if(category.equals(pp.getType())){
						pp.realNumOfCommodity--;
						if(pp.realNumOfCommodity==0)
							pp.setHasCommodity(false);
					}
				}
				save();
				return true;
			}
		}
	
		return false;
	}

	//modify a commodity
	public boolean modify(String id,String newName,String newType) throws RemoteException {
		String idToModify=id;
		for(CommodityPO p : comList){
			if(p.getID().equals(id)){
				p.setName(newName);
				p.setType(newType);
				save();
				return true;
			}
			
		}
		return false;
	}
	//change the commodity amount
	public boolean changeAmount(String id,String operateType, int amount) {
		for(CommodityPO p : comList){
			if(p.getID().equals(id)){
				int fixAmount = p.getAmount();
				switch (operateType){
				case "STOCKIN":
				case "IMP":
					fixAmount+=amount;
					break;
				case "STOCKOUT":
				case "EXP":
					fixAmount-=amount;
					break;
				case "DOCUMENT":
					fixAmount = amount;
					break;
				}
				if(fixAmount<=0)
					return false;
				p.setAmount(fixAmount);
				try {
					save();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				return true;
			}
			
		}
		return false;
	}
	//change the report amount of commodity
	public boolean changeReport(String id,int amount) {
		for(CommodityPO p : comList){
			if(p.getID().equals(id)){
				p.setReportAmount(amount);;
				try {
					save();
				} catch (RemoteException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				return true;
			}
			
		}
		return false;
	}
	////show commodity in a category
	public ArrayList<CommodityPO> showCommodity(String typeName){
		ArrayList<CommodityPO> searchList = new ArrayList<CommodityPO>();
		if(typeName==null)
			return null;
		String pressedTypeID="";
		for(CommodityTypePO p : typeList){
			if(p.getType().equals(typeName)){
				pressedTypeID = p.getTypeID();
			}
		}
		pressedTypeID = pressedTypeID.replace("0", "");
		int idLength = pressedTypeID.length();
		for(CommodityPO p : comList){
			if(p.getID().substring(0, idLength).equals(pressedTypeID)){
				searchList.add(p);
			}
		}
		return searchList;
	}
	//find a commodity 
	public ArrayList<CommodityPO> find(String s) throws RemoteException {
		ArrayList<CommodityPO> searchList = new ArrayList<CommodityPO>();
		if(s==null)
			return null;
		for(CommodityPO p : comList){
			if(p.getName().contains(s)||p.getType().contains(s)||p.getID().contains(s)){
				searchList.add(p);
			}
		}
		return searchList;
	}

	//
	public void init() throws RemoteException {
		// TODO 自动生成的方法存根
		
	}

	//save the data
	public void save() throws RemoteException {
		try {  
			ObjectOutputStream out2  = new ObjectOutputStream(new FileOutputStream("Data/typeData.ser"));  
            out2.writeObject(typeList); 
            out2.close();  
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/commodityData.ser"));  
            out.writeObject(comList);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		
	}
	//get a commodity's id 
	public String getComID(String name,String type){
		String id="";
		
		for(CommodityPO p: comList){
			if(name.equals(p.getName())&&type.equals(p.getType())){
				id = p.getID();
				break;
			}
		}
		return id;
	}
	//===============================================tree=========================================
	//get a commmodity category's id
	public String getTypeID(String s){
		String id="";
		for(CommodityTypePO p: typeList){

			if(s.equals(p.getType())){
				id = p.getTypeID();
				break;
			}
		}
		return id;
	}
	//get the category list
	public ArrayList<CommodityTypePO> getTypeList(){
		return typeList;
	}
	//modify a category
	public boolean modifyType(String oldName,String newName){
		String father ="";
		if(oldName.equals("全部商品分类"))
			return false;
		for(CommodityTypePO p: typeList){
			if(newName.equals(p.getType())){
				return false;
			}
		}
		for(CommodityTypePO p: typeList){
			if(oldName.equals(p.getType())){
				p.setType(newName);
				father = p.getFather();
			}
		}
		for(CommodityTypePO p: typeList){
			if(father.equals(p.getType())){
				p.child.set(p.child.indexOf((String)oldName), newName);
			}
		}
		for(CommodityPO p:comList){
			if(p.getCommodityType().equals(oldName))
				p.setCommodityType(newName);
		}
		try {
			saveTypeList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	//auto-generated category ID
	public String createTypeID(String s,int num){
		String back="";
		int i = 0;
		for( ;i<6;i++){
			if(s.charAt(i)!='0')
				back+=s.charAt(i);
			else{
				back+=(char)('A'+(num%26));
				for(int k = 0 ; k < 6-i;k++)
					back+="0";
				break;
			}
		}
		
		return back;
	}
	//delete a category 
	public boolean delType(String name){
		for(CommodityTypePO p: typeList){
			if(name.equals(p.getType())){
				if(p.child.size()!=0||p.isHasCommodity())
					return false;
				String father = p.getFather();
				for(CommodityTypePO pp : typeList){
					if(father.equals(pp.getType()))
						pp.child.remove(name);
				}
				typeList.remove(p);
				
				break;
			}
		}
		try {
			saveTypeList();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	//add a category
	public boolean addType(String name,String parentName){
		CommodityTypePO po = new CommodityTypePO(name);
		CommodityTypePO parentPO = new CommodityTypePO(parentName) ;
		
		String typeID=null;
		for(CommodityTypePO p: typeList){
			if(parentName.equals(p.getType())){
				parentPO = p;
				if(p.isHasCommodity())
					return false;
			}
		}
		for(CommodityTypePO p: typeList){
			if(name.equals(p.getType())){
				return false;
			}
		}

		int tmpChildren = parentPO.getNumOfChildren();
		if(tmpChildren>25)
			return false;
		po.setTypeID(createTypeID(parentPO.getTypeID(),tmpChildren));
		parentPO.setNumOfChildren(++tmpChildren);
		parentPO.child.add(name);
		po.setFather(parentName);
		typeList.add(po);
		try {
			saveTypeList();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	//save the category data
	public void saveTypeList() throws FileNotFoundException, IOException{
		 ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/typeData.ser"));  
         out.writeObject(typeList); 
         out.close();  
	}
	//abondon method
	public void saveTree(JTree tree) throws RemoteException{

	}
	
	//save data
	public void finish() throws RemoteException {
		try {  
            ObjectOutputStream out  = new ObjectOutputStream(new FileOutputStream("Data/commodityData.ser"));  
            out.writeObject(comList);   
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
		
		
	}
	//show commodity list
	public ArrayList<CommodityPO> show() throws RemoteException {
		try{  
			File fck = new File("Data/commodityData.ser");
			if(fck.exists()){
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Data/commodityData.ser"));  
            
            comList = (ArrayList<CommodityPO>) in.readObject();   
            in.close();
			}
            if(comList==null)
            	comList=new ArrayList<CommodityPO>();
        }catch(FileNotFoundException e){  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) { 
            e.printStackTrace();  
        }
		return comList;
	}

	//show a JTree depends on category
	public ArrayList<CommodityTypePO> showTree() throws RemoteException {
		return typeList;
	}



	//ensure a commodity amount for variety operation
	public boolean ensureAmount(String id, int amount) throws RemoteException {
		for(CommodityPO p : comList){
			if(p.getID().equals(id)){
				if(p.getAmount()>=amount)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	//clear data for junit test
	public void clear() throws RemoteException{
		comList.clear();
		typeList.clear();
		save();

	}


}
