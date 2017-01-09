package com.mama.server.main.dao.model;

import com.meidusa.fastjson.JSONArray;
import com.meidusa.fastjson.JSONObject;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class HotelCouponGivePo
{
  private long id;
  private String memberId;
  private String hotelCouponIds;
  private String receivedHotelCouponIds;
  private int status;
  private String giveCode;
  private String showDetail;
  private Date createTime;
  private List<HotelCouponGiveReceivePo> receives;
  private int receivedCount;
  private int receivedTime;

  public int getReceivedCount()
  {
    if ((this.receivedCount <= 0) && (StringUtils.isNotBlank(this.receivedHotelCouponIds))) {
      int copiesLimit = JSONObject.parseObject(this.showDetail).getIntValue("copiesLimit");
      this.receivedCount = (JSONArray.parseArray(this.receivedHotelCouponIds).size() / copiesLimit);
    }
    return this.receivedCount;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMemberId() {
    return this.memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public String getHotelCouponIds() {
    return this.hotelCouponIds;
  }

  public void setHotelCouponIds(String hotelCouponIds) {
    this.hotelCouponIds = hotelCouponIds;
  }

  public int getStatus() {
    return this.status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getShowDetail() {
    return this.showDetail;
  }

  public void setShowDetail(String showDetail) {
    this.showDetail = showDetail;
  }

  public Date getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getReceivedHotelCouponIds() {
    return this.receivedHotelCouponIds;
  }

  public void setReceivedHotelCouponIds(String receivedHotelCouponIds) {
    this.receivedHotelCouponIds = receivedHotelCouponIds;
  }

  public void setReceivedCount(int receivedCount) {
    this.receivedCount = receivedCount;
  }

  public int getReceivedTime() {
    return this.receivedTime;
  }

  public void setReceivedTime(int receivedTime) {
    this.receivedTime = receivedTime;
  }

  public List<HotelCouponGiveReceivePo> getReceives() {
    return this.receives;
  }

  public void setReceives(List<HotelCouponGiveReceivePo> receives) {
    this.receives = receives;
  }

  public String getGiveCode() {
    return this.giveCode;
  }

  public void setGiveCode(String giveCode) {
    this.giveCode = giveCode;
  }
}