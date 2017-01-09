import java.util.Calendar;
import java.util.Random;

import com.mmzb.house.web.model.ActivityConfig;
import com.mmzb.house.web.model.ActivityDate;
import com.mmzb.house.web.model.Luckybean;
import com.mmzb.house.web.model.TimePeriod;
import com.netfinworks.common.util.DateUtil;



public class TestLuckyBean {
	public static void main(String[] args) throws Exception {
		ActivityConfig activityConfig = new ActivityConfig();
		activityConfig.setActivityCode("AT001");
		
		// 抽奖日
		ActivityDate activityDate1 = new ActivityDate();
		activityDate1.setCalendarField(7);
		activityDate1.setFieldValue(3);
		
		ActivityDate activityDate2 = new ActivityDate();
		activityDate2.setCalendarField(7);
		activityDate2.setFieldValue(4);
		
		activityConfig.setActivityDate(activityDate1);
		activityConfig.setActivityDate(activityDate2);
		
		// 抽奖时间段
		TimePeriod timePeriod1 = new TimePeriod();
		timePeriod1.setStartDateTime(Calendar.getInstance());
		timePeriod1.getStartDateTime().set(2016, 5, 22, 0, 0);
		timePeriod1.setEndDateTime(Calendar.getInstance());
		timePeriod1.getEndDateTime().set(2016, 5, 22, 5, 59);
		activityConfig.setTimePeriod(timePeriod1);
		
		TimePeriod timePeriod2 = new TimePeriod();
		timePeriod2.setStartDateTime(Calendar.getInstance());
		timePeriod2.getStartDateTime().set(2016, 5, 22, 6, 0);
		timePeriod2.setEndDateTime(Calendar.getInstance());
		timePeriod2.getEndDateTime().set(2016, 5, 22, 11, 59);
		activityConfig.setTimePeriod(timePeriod2);
		
		TimePeriod timePeriod3 = new TimePeriod();
		timePeriod3.setStartDateTime(Calendar.getInstance());
		timePeriod3.getStartDateTime().set(2016, 5, 22, 12, 0);
		timePeriod3.setEndDateTime(Calendar.getInstance());
		timePeriod3.getEndDateTime().set(2016, 5, 22, 17, 59);
		activityConfig.setTimePeriod(timePeriod3);
		
		TimePeriod timePeriod4 = new TimePeriod();
		timePeriod4.setStartDateTime(Calendar.getInstance());
		timePeriod4.getStartDateTime().set(2016, 5, 22, 18, 0);
		timePeriod4.setEndDateTime(Calendar.getInstance());
		timePeriod4.getEndDateTime().set(2016, 5, 22, 19, 45);
		activityConfig.setTimePeriod(timePeriod4);
		
		// 抽奖次数
		activityConfig.setPeriodTimes(3);
		
		// 开奖时间
		activityConfig.setPublishTime(Calendar.getInstance());
		activityConfig.getPublishTime().set(2016, 5, 22, 20, 0);
		
		System.out.println(DateUtil.format(timePeriod4.getStartDateTime().getTime(), "yyyy-MM-dd HH:mm"));
		System.out.println(DateUtil.format(timePeriod4.getEndDateTime().getTime(), "yyyy-MM-dd HH:mm"));
		System.out.println(DateUtil.format(activityConfig.getPublishTime().getTime(), "yyyy-MM-dd HH:mm"));
		
		// 幸运豆
		Luckybean Luckybean1 = new Luckybean();
		Luckybean1.setBeanNums(0);
		Luckybean1.setProbability(1);
		Luckybean1.setNote("下次必中");
		activityConfig.setLuckyBean(Luckybean1);
		
		Luckybean Luckybean2 = new Luckybean();
		Luckybean2.setBeanNums(21);
		Luckybean2.setProbability(8);
		Luckybean2.setNote("爱你哦");
		activityConfig.setLuckyBean(Luckybean2);
		
		Luckybean Luckybean3 = new Luckybean();
		Luckybean3.setBeanNums(147);
		Luckybean3.setProbability(8);
		Luckybean3.setNote("一世情");
		activityConfig.setLuckyBean(Luckybean3);
		
		Luckybean Luckybean4 = new Luckybean();
		Luckybean4.setBeanNums(200);
		Luckybean4.setProbability(12);
		Luckybean4.setNote("爱你哦");
		activityConfig.setLuckyBean(Luckybean4);
		
		Luckybean Luckybean5 = new Luckybean();
		Luckybean5.setBeanNums(505);
		Luckybean5.setProbability(13);
		Luckybean5.setNote("爱的sos");
		activityConfig.setLuckyBean(Luckybean5);
		
		Luckybean Luckybean6 = new Luckybean();
		Luckybean6.setBeanNums(666);
		Luckybean6.setProbability(15);
		Luckybean6.setNote("顺顺顺");
		activityConfig.setLuckyBean(Luckybean6);
		
		Luckybean Luckybean7 = new Luckybean();
		Luckybean7.setBeanNums(888);
		Luckybean7.setProbability(15);
		Luckybean7.setNote("发发发");
		activityConfig.setLuckyBean(Luckybean7);
		
		Luckybean Luckybean8 = new Luckybean();
		Luckybean8.setBeanNums(1721);
		Luckybean8.setProbability(15);
		Luckybean8.setNote("一起爱你");
		activityConfig.setLuckyBean(Luckybean8);
		
		Luckybean Luckybean9 = new Luckybean();
		Luckybean9.setBeanNums(2627);
		Luckybean9.setProbability(8);
		Luckybean9.setNote("爱来爱去");
		activityConfig.setLuckyBean(Luckybean9);

		Luckybean Luckybean10 = new Luckybean();
		Luckybean10.setBeanNums(3344);
		Luckybean10.setProbability(2);
		Luckybean10.setNote("生生世世");
		activityConfig.setLuckyBean(Luckybean10);
		
		Luckybean Luckybean11 = new Luckybean();
		Luckybean11.setBeanNums(3731);
		Luckybean11.setProbability(2);
		Luckybean11.setNote("真心真意");
		activityConfig.setLuckyBean(Luckybean11);
		
		Luckybean Luckybean12 = new Luckybean();
		Luckybean12.setBeanNums(5999);
		Luckybean12.setProbability(1);
		Luckybean12.setNote("大满贯");
		activityConfig.setLuckyBean(Luckybean12);
		
		Luckybean tempLuckybean;
		int probability = -1;
		for (int i = 0; i < activityConfig.getLuckyBeans().size(); i++) {
			tempLuckybean = activityConfig.getLuckyBeans().get(i);
			probability = probability + tempLuckybean.getProbability();
			activityConfig.getLuckyBeans().get(i).setRandomNumRange(probability);
			System.out.println(activityConfig.getLuckyBeans().get(i).getRandomNumRange());
		}
		
		System.out.println();
		System.out.println();
		
		Random random = new Random();
		random.setSeed(Calendar.getInstance().getTime().getTime());
		int randomNum = random.nextInt(100);
		System.out.println(randomNum);
		for (int i = 0; i < activityConfig.getLuckyBeans().size(); i++) {
			tempLuckybean = activityConfig.getLuckyBeans().get(i);
			if (randomNum <= tempLuckybean.getRandomNumRange()) {
				
				System.out.println(tempLuckybean.getBeanNums());
				System.out.println(tempLuckybean.getNote());
				break;
			}
		}
	}
}
