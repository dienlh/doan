package com.hotel.app.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class MypayPayment {

	protected HttpServletResponse response;
	
	static final char[] HEX_TABLE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	static final String MYPAY_PAYGATE_URL = "https://mypay.vn/homePayGate/paygate.htm?";
	static final String MYPAY_PAYGATE_URL_SANDBOX = "http://sandbox.mypay.vn/homePayGate/paygate.htm?";
	static final String MYPAY_NOTIFICATION_URL = "https://mypay.vn/notification/merchantNotify.htm?";
	static final String MYPAY_NOTIFICATION_URL_SANDBOX = "http://sandbox.mypay.vn/notification/merchantNotify.htm?";
	
	static final String accountId = "16-000-0405-995"; //Ma tai khoan lay tu mypay
	static final String secureCode = "123456"; //Mat khau giao tiep lay tu mypay
	
	public String description = "";	
	public String urlSucess;
	public String urlFail;
	public String orderId;
	public String amount;	
	public String tranid;
	public String message;
	public boolean sandbox = true; //true: thanh toan test tren sandbox.mypay.vn, false: thanh toan that tren mypay.vn
	
	public String secureSecret;
	public String reAccountId;
	public String orderStatus;
	
	public MypayPayment(){}
	
	public MypayPayment(String orderId, String description, String urlSucess, String urlFail, String amount, boolean sandbox){
		super();
		this.orderId = orderId;
		this.description = description;
		this.urlSucess = urlSucess;
		this.urlFail = urlFail;
		this.amount = amount;
		this.sandbox = sandbox;
	}
	
	/**
	 * 
	 * Request to MyPay Payment
	 * 
	 * @return
	 * @throws IOException
	 * 
	 */
	public String sendRedirectURL() throws IOException{
		Map<String, String> fields = new HashMap<String, String>();
		fields.put("orderId", orderId);
		fields.put("description", description);
		fields.put("accountId", accountId);
		fields.put("urlSucess", urlSucess);
		fields.put("urlFail", urlFail);
		fields.put("amount", String.valueOf(amount));
		
		String secure = hashAllFields(fields, secureCode);
		fields.put("secureSecret", secure);
		
		StringBuffer buf = new StringBuffer();
		
		if(sandbox)
			buf.append(MYPAY_PAYGATE_URL_SANDBOX);
		else
			buf.append(MYPAY_PAYGATE_URL);
		
		appendQueryFields(buf, fields);
		
		return buf.toString();
	}
	
	/**
     * Phan tich ket qua tra ve tu Mypay
     */
    public boolean getPaymentReturn(String tranid, String accountId, String secureSecret, String orderId) {
    	
    	setSecureSecret(secureSecret);    	
    	Double dtranid=Double.valueOf(tranid);
    	Map<String, String> fields = new HashMap<String, String>();
	    fields.put("tranid", String.valueOf(tranid));
	    fields.put("orderId", orderId);
		fields.put("accountId", accountId);
    	
    	if(!validateParameters(fields)){
    		setMessage("Xác thực không hợp lệ");
    		return false;
    	}
    	if (dtranid == -12) {
            setMessage("Khách hàng hủy giao dịch");
            return false;
        }else if(dtranid == -11){
        	setMessage("Giao dịch trên cổng thanh toán đang tạm khóa");
            return false;
        }else if(dtranid == -10){
        	setMessage("Có lỗi trong quá trình giao dịch");
            return false;
        }else if(dtranid == -9){
        	setMessage("Tham số đầu vào chưa hợp lệ");
            return false;
        }else if(dtranid == -8){
        	setMessage("Trường description quá dài");
            return false;
        }else if(dtranid == -7){
        	setMessage("Mã đơn hàng đã được thực hiện giao dịch thanh toán");
            return false;
        }else if(dtranid == -6){
        	setMessage("Mã xác thực không đúng");
            return false;
        }else if(dtranid == -5){
        	setMessage("Số tài khoản hoặc website chưa được đăng ký, chưa xác thực hoặc bị khóa");
            return false;
        }else if(dtranid == -4){
        	setMessage("Tiền trong tài khoản không đủ thanh toán");
            return false;
        }else if(dtranid == -3){
        	setMessage("Lỗi thanh toán bằng tài khoản của Mypay của người bán hàng");
            return false;
        }else if(dtranid == -2){
        	setMessage("Địa chỉ yêu cầu thanh toán không phù hợp với địa chỉ đăng ký");
            return false;
        }else if(dtranid == 0){//Ap dung cho cac truong hop chuyen tien offline va chuyen tien qua internet banking
        	setMessage("Đã chấp nhận thực hiện giao dịch chuyển tiền qua ngân hàng");
            return true;
        }else if(dtranid > 0){//Bao gom ca thanh toan ngay va thanh toan tra cham
        	setMessage("Giao dịch thanh toán thành công");
            return true;
        }
    	return false;
    }
	
    /**
     * Ham nay dung de doc trang thai dong bo tu Mypay
     * @return boolean True neu tham so hop le, False neu nguoc lai
     */
    public boolean getNotification(String orderId, String orderStatus, String accountId, String secureSecret){
    	setSecureSecret(secureSecret);    	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId);
		params.put("accountId", accountId);
		params.put("orderStatus", String.valueOf(orderStatus));
		if(validateParameters(params))
    		return true;
		else
			return false;
    }    
    /**
     * return URL to confirm status to Mypay
     */
    public String sendConfirmStatus(String orderStatus, boolean sandbox){
    	Map<String, String> fields = new HashMap<String, String>();
		fields.put("orderId", orderId);
		fields.put("accountId", accountId);
		fields.put("orderStatus", String.valueOf(orderStatus));
		String secure = hashAllFields(fields, secureCode);
		fields.put("secureSecret", secure);
		
		StringBuffer buf = new StringBuffer();
		
		if(sandbox)
			buf.append(MYPAY_PAYGATE_URL_SANDBOX);
		else
			buf.append(MYPAY_PAYGATE_URL);
		
		appendQueryFields(buf, fields);		
		return buf.toString();
		
    }
    
	//================================================//
	/**
     * Dung de kiem tra xem thong so tra ve tu Mypay co hop le hay khong
     */
	public boolean validateParameters(Map fields) {		
		if (getSecureSecret().length() > 0) {
			String secure = hashAllFields(fields, secureCode);
			
            // Validate the Secure Hash (remember MD5 hashes are not case sensitive)
		    if (secureSecret.toUpperCase().equals(secure.toUpperCase())) {
		    // Secure Hash validation succeeded, add a data field to be displayed later.
                return true;
		    } else {
		        // Secure Hash validation failed, add a data field to be displayed later.
		        return false;
		    }
		} else {
		    // Secure Hash was not validated, add a data field to be displayed later.
		    return true;
		}
	}	
	
	// Create MD5 secure hash and insert it into the hash map if it was created	
	String hashAllFields(Map fields, String SECURE_SECRET) {
		// create a list and sort it
		List fieldNames = new ArrayList(fields.keySet());
		Collections.sort(fieldNames);

		// create a buffer for the md5 input and add the secure secret first
		StringBuffer buf = new StringBuffer();
		buf.append(SECURE_SECRET);

		// iterate through the list and add the remaining field values
		Iterator itr = fieldNames.iterator();

		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				buf.append(fieldValue);
			}
		}
		return toMd5String(buf.toString()).toUpperCase();
	}
	
	/**
	 * encode string to md5
	 * 
	 * @param aClear
	 * @return string was encoded
	 * @throws java.lang.Exception
	 */
	public static String toMd5String(String aClear) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] b;
			b = md.digest(aClear.getBytes());
			StringBuffer md5;
			md5 = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				int u = b[i] & 0xFF; // unsigned conversion
				if (u < 0x10)
					md5.append("0");
				md5.append(Integer.toHexString(u));
			}
			return md5.toString();
		} catch (Exception ex) {
			return aClear;
		}
	}
	
	void appendQueryFields(StringBuffer buf, Map fields) {
		// create a list
		List fieldNames = new ArrayList(fields.keySet());
		Iterator itr = fieldNames.iterator();

		// move through the list and create a series of URL key/value pairs
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) fields.get(fieldName);

			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// append the URL parameters
				buf.append(URLEncoder.encode(fieldName));
				buf.append('=');
				buf.append(URLEncoder.encode(fieldValue));
			}

			// add a '&' to the end if we have more fields coming.
			if (itr.hasNext()) {
				buf.append('&');
			}
		}
	}
	
	static String hex(byte[] input) {
		// create a StringBuffer 2x the size of the hash array
		StringBuffer sb = new StringBuffer(input.length * 2);

		// retrieve the byte array data, convert it to hex
		// and add it to the StringBuffer
		for (int i = 0; i < input.length; i++) {
			sb.append(HEX_TABLE[(input[i] >> 4) & 0xf]);
			sb.append(HEX_TABLE[input[i] & 0xf]);
		}
		return sb.toString();
	}
	
	//=================== SET - GET ==================//
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrlSucess() {
		return urlSucess;
	}
	public void setUrlSucess(String urlSucess) {
		this.urlSucess = urlSucess;
	}
	public String getUrlFail() {
		return urlFail;
	}
	public void setUrlFail(String urlFail) {
		this.urlFail = urlFail;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTranid() {
		return tranid;
	}
	public void setTranid(String tranid) {
		this.tranid = tranid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSandbox() {
		return sandbox;
	}
	public void setSandbox(boolean sandbox) {
		this.sandbox = sandbox;
	}

	public String getSecureSecret() {
		return secureSecret;
	}

	public void setSecureSecret(String secureSecret) {
		this.secureSecret = secureSecret;
	}

	public String getReAccountId() {
		return reAccountId;
	}

	public void setReAccountId(String reAccountId) {
		this.reAccountId = reAccountId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}	
}
