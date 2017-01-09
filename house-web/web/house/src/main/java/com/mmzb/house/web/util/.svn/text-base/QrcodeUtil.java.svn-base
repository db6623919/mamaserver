package com.mmzb.house.web.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 * 
 * @author lijiaqi
 *
 */
public class QrcodeUtil {

	
	/**
	 * 
	 * @param content
	 * @param imgPath
	 * @return
	 */
	 public static byte[] createQRCode(String content) { 
		 byte[] res = null;

	       try {  

	           Qrcode qrcodeHandler = new Qrcode();  

	           qrcodeHandler.setQrcodeErrorCorrect('M');  

	           qrcodeHandler.setQrcodeEncodeMode('B');  

	           qrcodeHandler.setQrcodeVersion(7);  

	           // System.out.println(content);  

	           byte[] contentBytes = content.getBytes("gb2312");  

	           //构造一个BufferedImage对象 设置宽、高

	           BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);  

	           Graphics2D gs = bufImg.createGraphics();  

	           gs.setBackground(Color.WHITE);  

	           gs.clearRect(0, 0, 140, 140);  

	           // 设定图像颜色 > BLACK  

	           gs.setColor(Color.BLACK);  

	           // 设置偏移量 不设置可能导致解析出错  

	           int pixoff = 2;  

	           // 输出内容 > 二维码  

	           if (contentBytes.length > 0 && contentBytes.length < 120) {  

	               boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  

	               for (int i = 0; i < codeOut.length; i++) {  

	                   for (int j = 0; j < codeOut.length; j++) {  

	                       if (codeOut[j][i]) {  

	                           gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  

	                       }  

	                   }  

	               }  

	           } else {  

	               System.err.println("QRCode content bytes length = "+ contentBytes.length + " not in [ 0,120 ]. ");  

	               return res;

	           }  

	           Image img = ImageIO.read(new File(QrcodeUtil.class.getClassLoader().getResource("erweima.png").getPath()));//实例化一个Image对象。

	           gs.drawImage(img, 44, 55, 49, 30, null);

	           gs.dispose();  

	           bufImg.flush(); 

	           ByteArrayOutputStream out = new ByteArrayOutputStream();
	           // 生成二维码QRCode图片  
	           ImageIO.write(bufImg, "png", out);
	           
	           res = out.toByteArray();

	       }catch (Exception e){  

	           e.printStackTrace();  

	           return res;

	       }  

	       return res;

	   }
	 
	 public static void main(String[] args) {
		byte[] a = createQRCode("123");
		System.out.println(a.length);
	}
}
