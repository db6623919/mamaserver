package com.mmzb.house.web.core.common.util;

import java.math.BigDecimal;


public class CommonUtils {
    public static final String secondFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String showMonth = "M";
    
    /**
	public static String      REPAY_TYPE_1 = "先息后本";                                           // 1-按月付息到期还本";
    public static String      REPAY_TYPE_2 = "等额本息";                                           // 2-等额本息还款
    public static String      REPAY_TYPE_3 = "等额本金";                                          // 3-等额本金"
    public static String      REPAY_TYPE_4 = "利随本清";                                          // 4-利随本清"
    public static String      REPAY_TYPE_5 = "随借随还";                                          // 5-随借随还"
     */
    public static String getRepayType(String repayType){
    	int type = Integer.parseInt(repayType);
    	switch (type) {
    	case 1:
    		return "先息后本";
    	case 2:
    		return "等额本息";
    	case 3:
    		return "等额本金";
    	case 4:
    		return "利随本清";
    	case 5:
    		return "随借随还";

		default:
			return "其他";
		}
    }
    
    /**
     * 处理换行、缩进
     * @param applyPurpose
     */
    public static String process(String applyPurpose) {
    	applyPurpose = applyPurpose.replaceAll("\n", "<br>");
    	applyPurpose = applyPurpose.replaceAll("\r<br>", "<br>\r");
    	applyPurpose = applyPurpose.replaceAll("\r", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    	applyPurpose = applyPurpose.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
    	return applyPurpose;
	}
    
    public static boolean checkAmount(String strAmount){
        boolean flag = true;
        try{
            new BigDecimal(strAmount);
       }catch(Exception e){
           flag = false;
       }
        return flag;
    }
    
    //全投计算
    public static BigDecimal getAllInAmount(BigDecimal balance,BigDecimal biddableAmount,BigDecimal bidUnit){
        BigDecimal allInAmount = new BigDecimal("0.00");
        BigDecimal amount = new BigDecimal("0.00");
        balance = balance==null?new BigDecimal("0.00"):balance;
        biddableAmount = biddableAmount==null?new BigDecimal("0.00"):biddableAmount;
        if(balance.compareTo(biddableAmount)<=0){
          //余额小于等于可投金额-余额
            amount = balance;
        }else{
            //余额大于可投金额-可投金额
            amount =  biddableAmount;   
        } 
        //如果倍数为0则直接返回金额
        if(new BigDecimal(0).compareTo(bidUnit)==0)return amount;
        //计算出来的金额除以倍数
        BigDecimal remainder = amount.remainder(bidUnit).setScale(2,BigDecimal.ROUND_HALF_UP);
        allInAmount = amount.subtract(remainder);
        return allInAmount;
    }
    
}
