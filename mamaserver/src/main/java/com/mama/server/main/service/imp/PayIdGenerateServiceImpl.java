package com.mama.server.main.service.imp;

import com.mama.server.main.service.PayIdGenerateService;
import com.mmzb.charge.domain.common.IdWorker;

public class PayIdGenerateServiceImpl implements PayIdGenerateService {

	private static long workerid = 0; 
	
	private static long datacenterid = 0;
	
    private static final IdWorker idWorker = new IdWorker(workerid,datacenterid);
	
	public String getPayID(){
		return idWorker.nextId()+"00000000000000";
	}

	public static long getWorkerid() {
		return workerid;
	}

	public static void setWorkerid(long workerid) {
		PayIdGenerateServiceImpl.workerid = workerid;
	}

	public static long getDatacenterid() {
		return datacenterid;
	}

	public static void setDatacenterid(long datacenterid) {
		PayIdGenerateServiceImpl.datacenterid = datacenterid;
	}
	
}