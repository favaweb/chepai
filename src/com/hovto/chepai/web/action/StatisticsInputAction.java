package com.hovto.chepai.web.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hovto.chepai.model.InputRegister;
import com.hovto.chepai.model.MonthfinishedProduct;
import com.hovto.chepai.model.SemifinishedProductType;
import com.hovto.chepai.model.StatisticsFinishedProducts;
import com.hovto.chepai.model.StatisticsInput;
import com.hovto.chepai.model.StatisticsMaterial;
import com.hovto.chepai.server.SemifinishedProductTypeServer;
import com.hovto.chepai.server.StatisticsServer;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
public class StatisticsInputAction extends ActionSupport {

	private Date startDate;
	private Date endDate;
	private int typeid;
	private int year;
	private int month;
	private int orderType;
	private int[] count;
	private List<StatisticsInput> statisticsInputs;
	private List<SemifinishedProductType> semifinishedProductTypes;
	private List<MonthfinishedProduct> monthfinishedProducts;
	private List<InputRegister> inputRegisters;
	private List<StatisticsFinishedProducts> statisticsFinisheds;
	private List<String> types;
	private List<String> places;
	private List<StatisticsMaterial> materials;
	private Map<String,int[]> statistic;
	@Resource
	private StatisticsServer statisticsServer;
	@Resource
	private SemifinishedProductTypeServer semifinishedProductTypeServer;

	
	
	/**
	 * 废品统计
	 * @return
	 */
	public String statisticsMaterial(){
		if(startDate!=null){
			materials = statisticsServer.findMeterails(startDate,endDate);
			int k = 0;
			statistic = new HashMap<String,int[]>();
			//算法，生成table
			while(true){
				
				if(k>=materials.size()){
					break;
				}
				if(statistic.get(materials.get(k).getTypename()) != null){
					int i[] = statistic.get(materials.get(k).getTypename());
					if(materials.get(k).getDaynight() == 0){
						i[0] = materials.get(k).getAmount();
					}
					if(materials.get(k).getDaynight() == 1){
						i[1] = materials.get(k).getAmount();
					}
					statistic.put(materials.get(k).getTypename(), i);
					k++;
				}else{
					int i[] = {0,0};
					statistic.put(materials.get(k).getTypename(), i);
				}
			}
		}
		else{
			materials = new ArrayList<StatisticsMaterial>();
			statistic = new HashMap<String,int[]>();
		}
		
		return "statisticsMaterial";
	}
	
	
	/**
	 * 成品发货统计
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String findFinishedList(){
		if(startDate != null && orderType!=0){
			statisticsFinisheds = statisticsServer.findisDeliverGoods(startDate, endDate, orderType);
			types = statisticsServer.findTypesList(startDate, endDate, orderType);
			places = statisticsServer.findPlaceList(orderType);
		}
		else{
			statisticsFinisheds = new ArrayList<StatisticsFinishedProducts>();
		}
		return "findFinishedList";
	}
	/**
	 * 成品发货统计（按箱）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String boxstat(){
		if(startDate != null && orderType!=0){
			statisticsFinisheds = statisticsServer.findisDeliverGoodsbybox(startDate, endDate, orderType);
		}
		else{
			statisticsFinisheds = new ArrayList<StatisticsFinishedProducts>();
		}
		return "boxstat";
	}
	
	
	
	/**
	 * 出入库统计页面
	 * @return
	 */
	public String findList() {
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		return "findList";
	}
	/**
	 * 出入库统计
	 * @return
	 */
	public String findListByCondition() {
		semifinishedProductTypes = semifinishedProductTypeServer.findList();
		statisticsInputs = statisticsServer.find(startDate, endDate,
				typeid);
		return "findList";
	}

	/**
	 * 成品月统计（旧）
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String findfinishedByMonth() {
		try {
			count = new int[6];
			for(int i=0;i<6;i++){
				count[i]=0;
			}
			
			if (year != 0 && month != 0) {
				Calendar c = Calendar.getInstance();
				c.set(year, month - 1, 1, 0, 0, 0);
				startDate = c.getTime();

				c.clear();
				c.set(year, month, -1, 0, 0, 0);
				endDate = c.getTime();


				monthfinishedProducts = statisticsServer
						.findfinishedProductByMonth(startDate, endDate,orderType);
				if(monthfinishedProducts!=null){
					for(MonthfinishedProduct one:monthfinishedProducts){
						count[0]+=one.getDa();
						count[1]+=one.getXiao();
						count[2]+=one.getMo();
						count[3]+=one.getNong();
						count[4]+=one.getGua();
						count[5]+=one.getJiao();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "finishedByMonth";
	}


	/* 以下为get、set方法 */
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public List<StatisticsInput> getStatisticsInputs() {
		return statisticsInputs;
	}

	public void setStatisticsInputs(List<StatisticsInput> statisticsInputs) {
		this.statisticsInputs = statisticsInputs;
	}

	public List<SemifinishedProductType> getSemifinishedProductTypes() {
		return semifinishedProductTypes;
	}

	public void setSemifinishedProductTypes(
			List<SemifinishedProductType> semifinishedProductTypes) {
		this.semifinishedProductTypes = semifinishedProductTypes;
	}

	public List<InputRegister> getInputRegisters() {
		return inputRegisters;
	}

	public void setInputRegisters(List<InputRegister> inputRegisters) {
		this.inputRegisters = inputRegisters;
	}

	public List<MonthfinishedProduct> getMonthfinishedProducts() {
		return monthfinishedProducts;
	}

	public void setMonthfinishedProducts(
			List<MonthfinishedProduct> monthfinishedProducts) {
		this.monthfinishedProducts = monthfinishedProducts;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public int[] getCount() {
		return count;
	}

	public void setCount(int[] count) {
		this.count = count;
	}



	public List<StatisticsFinishedProducts> getStatisticsFinisheds() {
		return statisticsFinisheds;
	}



	public void setStatisticsFinisheds(
			List<StatisticsFinishedProducts> statisticsFinisheds) {
		this.statisticsFinisheds = statisticsFinisheds;
	}






	public List<String> getTypes() {
		return types;
	}



	public void setTypes(List<String> types) {
		this.types = types;
	}



	public List<String> getPlaces() {
		return places;
	}



	public void setPlaces(List<String> places) {
		this.places = places;
	}



	public List<StatisticsMaterial> getMaterials() {
		return materials;
	}



	public void setMaterials(List<StatisticsMaterial> materials) {
		this.materials = materials;
	}


	public Map<String, int[]> getStatistic() {
		return statistic;
	}


	public void setStatistic(Map<String, int[]> statistic) {
		this.statistic = statistic;
	}




}
