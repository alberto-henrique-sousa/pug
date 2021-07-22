/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */
package com.pugsource.gwt.library.client;

import java.util.Date;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SimpleHtmlSanitizer;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.pugsource.gwt.library.client.ui.RichTextPug;

public class Tools {

	public static int COOKIE_EXPIRE_DAYS = 365;
	public static final long MILLISECS_PER_DAY = 1000L * 60L * 60L * 24L;
	public static String COOKIE_USERNAME = "pugcookie1";
	public static String COOKIE_PASSWORD = "pugcookie2";
	public static String historyAction = "";

	public static native boolean validCharDomain(int a) /*-{
		var N = /[a-zA-Z0-9_.]/; 
		var c = String.fromCharCode(a);
		return !!(a==0||a==8||a==9||a==13||c.match(N)); 	
	}-*/;

	public static native boolean validDomain(String text) /*-{
		var CHECK_NAME = /^([a-zA-Z0-9_.$]+)$/;
		return text.match(CHECK_NAME) != null;
	}-*/;

	public static native String replaceAll(String content, String searchTerm, String replaceTerm) /*-{
		var regExp = new RegExp("(" + searchTerm + ")", "gi");
		return content.replace(regExp, replaceTerm);    
	}-*/;	

	public static native boolean mobile()/*-{
		var a = navigator.userAgent||navigator.vendor||window.opera;
		var b = '';		
		if (/android|ipad|avantgo|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|e\-|e\/|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\-|2|g)|yas\-|your|zeto|zte\-/i.test(a.substr(0,4)))
			return true;
		else
			return false;
	}-*/;				

	public static native String urlEncode(String url)/*-{
		return escape(url);
	}-*/;			

	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;
	
	public static native void reload()/*-{
		$wnd.location.reload();
	}-*/;	

	public static native void windowOpen(String url)/*-{
		$wnd.window.open(url);
	}-*/;				

	public static native String urlLocation()/*-{
		var URL = $wnd.document.location.toString();
		return URL;
	}-*/;

	public static native boolean validEmail(String text) /*-{
		var CHECK_EMAIL = /^[\w!#$%&'*+\/=?^`{|}~-]+(\.[\w!#$%&'*+\/=?^`{|}~-]+)*@(([\w-]+\.)+[A-Za-z]{2,6}|\[\d{1,3}(\.\d{1,3}){3}\])$/;
		return text.match(CHECK_EMAIL) != null;
	}-*/;

	public static native boolean isDate(int day, int month, int year) /*-{
		var date = new Date();
		var blnRet = false;
		var blnDay;
		var blnMonth;
		var blnYear;

		if (day > 0 && month > 0 && year > 0) {
			date.setFullYear(year, month -1, day);

			blnDay   = (date.getDate()      == day);
			blnMonth = (date.getMonth()     == month -1);
			blnYear  = (date.getFullYear()  == year);

			blnRet = (blnDay && blnMonth && blnYear);
		}	

		return blnRet;		
	}-*/;

	public static native Date strToDate(int day, int month, int year) /*-{
		var date = new Date();
		var blnRet = false;
		var blnDay;
		var blnMonth;
		var blnYear;

		if (day > 0 && month > 0 && year > 0) { 
			date.setFullYear(year, month -1, day);

			blnDay   = (date.getDate()      == day);
			blnMonth = (date.getMonth()     == month -1);
			blnYear  = (date.getFullYear()  == year);

			blnRet = (blnDay && blnMonth && blnYear);
		}	

		return blnRet ? date : null;		
	}-*/;
	
	@SuppressWarnings("deprecation")
	public static Date lastDayMonth(Date dateCurrent) {
		dateCurrent.setMonth(dateCurrent.getMonth() + 1);
		dateCurrent.setDate(dateCurrent.getDate() - 1);
		return dateCurrent;
	}


	public static String htmlSanitizer(String html) {
		SafeHtml snippetHtml = SimpleHtmlSanitizer.sanitizeHtml(html);
		return snippetHtml.asString();		
	}


	public static void setCookie(String name, String value, int days) {
		if (value == null) {
			Cookies.removeCookie(name);
			return;
		}
		Date d = new Date();
		d.setTime(d.getTime() + MILLISECS_PER_DAY * days);
		Cookies.setCookie(name, value, d);
	}

	public static String getCookie(String name) {
		return Cookies.getCookie(name);
	}

	public static void setUserCookie(String username, String password) {
		Tools.setCookie(COOKIE_USERNAME, Tools.stringToHex(username), COOKIE_EXPIRE_DAYS);
		Tools.setCookie(COOKIE_PASSWORD, Tools.stringToHex(password), COOKIE_EXPIRE_DAYS);
	}

	public static void removeUserCookie() {
		Tools.setCookie(COOKIE_USERNAME, null, 0);
		Tools.setCookie(COOKIE_PASSWORD, null, 0);
	}

	public static String getCookieUsername() {
		String str = Tools.getCookie(Tools.COOKIE_USERNAME);
		return str == null ? null : Tools.hexToString(str);
	}

	public static String getCookiePassword() {
		String str = Tools.getCookie(Tools.COOKIE_PASSWORD);
		return str == null ? null : Tools.hexToString(str);
	}

	public static String byteToStr(byte[] bytes)
	{
		StringBuilder s = new StringBuilder(bytes.length);
		for(int i = 0; i < bytes.length; i++) {
			s.append((char)bytes[i]);
		}
		return s.toString();    
	}	

	public static String stringToHex(String str){
		try {
			char[] chars = str.toCharArray();

			StringBuffer hex = new StringBuffer();
			for(int i = 0; i < chars.length; i++){
				hex.append(Integer.toHexString((int)chars[i]));
			}

			return hex.toString();			
		} catch (Exception e) {
			return null;
		}
	}

	public static String hexToString(String hex){
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder temp = new StringBuilder();
			//49204c6f7665204a617661 split into two characters 49, 20, 4c...
			for( int i=0; i<hex.length()-1; i+=2 ){
				//grab the hex in pairs
				String output = hex.substring(i, (i + 2));
				//convert hex to decimal
				int decimal = Integer.parseInt(output, 16);
				//convert the decimal to character
				sb.append((char)decimal);
				temp.append(decimal);
			}
			return sb.toString();			
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * Converts a time in UTC to a gwt Date object which is in the timezone of
     * the current browser.
     */
    public static final Date utc2date(Long time) {

        // don't accept negative values
        if (time < 0) return null;
        
        // add the timezone offset
        time += timezoneOffsetMillis(new Date(time));

        return new Date(time);
    }

    /**
     * Converts a gwt Date in the timezone of the current browser to a time in UTC.
     */
    public static final Long date2utc(Date date) {

        // use -1 to mean a bogus date
        if (date == null) return null;
        
        long time = date.getTime();
        
        // remove the timezone offset        
        time -= timezoneOffsetMillis(date);
        
        return time;
    }
	
    @SuppressWarnings("deprecation")
    public static final long timezoneOffsetMillis(Date date) {
        return date.getTimezoneOffset()*60*1000;        
    }

	public static boolean validate(Boolean valid, boolean condition, Label lblTitle, FocusWidget boxInput, Label lblMsg, String msg) {
		if (!condition) {
			lblTitle.setStyleName("gwt-title-error-validation");
			lblMsg.setText(msg);
			lblMsg.setVisible(true);
			boxInput.setFocus(true);
		} else {
			lblTitle.setStyleName("");
			lblMsg.setText("");
			lblMsg.setVisible(false);
		}
		return !valid ? valid : condition;
	}
	
	public static boolean validate(Boolean valid, boolean condition, Label lblTitle, DateBox boxInput, Label lblMsg, String msg) {
		if (!condition) {
			lblTitle.setStyleName("gwt-title-error-validation");
			lblMsg.setText(msg);
			lblMsg.setVisible(true);
			boxInput.setFocus(true);
		} else {
			lblTitle.setStyleName("");
			lblMsg.setText("");
			lblMsg.setVisible(false);
		}
		return !valid ? valid : condition;
	}
	
	public static boolean validate(Boolean valid, boolean condition, Label lblTitle, RichTextPug boxInput, Label lblMsg, String msg) {
		if (!condition) {
			lblTitle.setStyleName("gwt-title-error-validation");
			lblMsg.setText(msg);
			lblMsg.setVisible(true);
			boxInput.setFocus();
		} else {
			lblTitle.setStyleName("");
			lblMsg.setText("");
			lblMsg.setVisible(false);
		}
		return !valid ? valid : condition;
	}	
	
	public static int findItemListBox(ListBox listBox, String str) {
		int index = -1;
		if (listBox != null && str != null) {
			str = str.toLowerCase();
			for (int i = 0; i < listBox.getItemCount() && index == -1; i++) {
				if (listBox.getItemText(i).toLowerCase().equals(str)) 
					index = i;
			}
		}	
		return index;
	}
	
	public static int findValueListBox(ListBox listBox, String value) {
		int index = -1;
		if (listBox != null && value != null) {
			value = value.toLowerCase();
			for (int i = 0; i < listBox.getItemCount() && index == -1; i++) {
				if (listBox.getValue(i).toLowerCase().equals(value)) 
					index = i;
			}
		}	
		return index;
	}			
    
}