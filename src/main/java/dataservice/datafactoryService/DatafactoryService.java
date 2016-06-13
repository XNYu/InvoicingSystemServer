package dataservice.datafactoryService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import dataservice.accountDataService.AccountDataService;
import dataservice.billDataService.BillDataService;
import dataservice.commodityDataService.CommodityDataService;
import dataservice.customerDataService.CustomerDataService;
import dataservice.documentDataService.DocumentDataService;
import dataservice.examineDataService.ExamineDataService;
import dataservice.initialDataService.InitialDataService;
import dataservice.logDataService.LogDataService;
import dataservice.promotionDataService.PromotionDataService;
import dataservice.salesDataService.SalesDataService;
import dataservice.stockDataService.StockDataService;
import dataservice.userDataService.UserDataService;

public interface DatafactoryService extends Remote{
	public UserDataService getUserData() throws RemoteException;
	public CustomerDataService getCustomerData() throws RemoteException;
	public SalesDataService getSalesData() throws RemoteException;
	public StockDataService getStockData() throws RemoteException;
	public DocumentDataService getDocumentData() throws RemoteException;
	public AccountDataService getAccountData() throws RemoteException;
	public PromotionDataService getPromotionData() throws RemoteException;
	public LogDataService getLogData() throws RemoteException;
	public InitialDataService getInitialData() throws RemoteException;
	public BillDataService getBillData() throws RemoteException;
	public ExamineDataService getExamineData() throws RemoteException;
	public CommodityDataService getCommodityData() throws RemoteException;
}
