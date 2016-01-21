package ma.org.nio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;


public class MyUtils {
	public static final char hexChar[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String hexStr[] = new String[256];
	public static String digitalStr[] = new String[100];

	static {
		for (int i = 0; i < hexStr.length; i++)
			hexStr[i] = String.format("%02X", i);

		for (int i = 0; i < digitalStr.length; i++)
			digitalStr[i] = String.format("%02d", i);

	}

	/**
	 * 将0～99之间的数值，转为一个2位宽带的字符串
	 * 
	 * @param d
	 * @return
	 */
	public static String toDecString(byte d) {
		if (d >= 100 || d < 0)
			return "";
		else
			return digitalStr[d];
	}

	/**
	 * 将1个字节的数据转为16进制字符串
	 */
	public static String toHexString(byte b) {
		return hexStr[b & 0xFF];
	}

	/**
	 * 将1个Short型的数据转为16进制字符串
	 */
	public static String toHexString(short b) {
		byte[] buff = new byte[2];

		buff[0] = (byte) ((b >> 8) & 0xFF);
		buff[1] = (byte) (b & 0xFF);

		return toHexString(buff, 0, buff.length);
	}

	/**
	 * 将字节数组转为16进制字符串
	 */
	public static String toHexString(byte b[]) {
		return toHexString(b, 0, b.length);
	}

	/**
	 * 将字节数组转为16进制字符串
	 */
	public static String toHexString(byte b[], int pos) {
		return toHexString(b, pos, b.length - pos);
	}

	/**
	 * 将字节数组转为16进制字符串
	 */
	public static String toHexString(byte b[], int pos, int len) {
		StringBuffer sb = new StringBuffer(len * 2);

		for (int i = pos; i < pos + len; i++)
			sb.append(hexStr[b[i] & 0xFF]);

		return sb.toString();
	}

	/**
	 * 将16进制字符串转为字节数值
	 */
	public static byte[] toArray(String str) {
		if (str == null || "".equals(str))
			return null;

		// 确定字节长度
		int len = str.length() / 2;

		// 将字符串转为数值
		byte[] b = new byte[len];
		for (int i = 0; i < b.length; i++) {
			String s = str.substring(2 * i, 2 * i + 2);
			b[i] = Integer.valueOf(s, 16).byteValue();
		}
		return b;

	}

	/**
	 * 将日期时间的值去除时间，仅仅保留日期并将日期设置为1号
	 */
	public static long trimFirstDayOfMonth(long dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateTime);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 将日期时间的值去除时间，仅仅保留日期
	 */
	public static long trimDateTime(long dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateTime);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 将日期时间的值去除时间的分钟、秒和毫秒，仅仅保留日期、小时
	 */
	public static long trimDateTimeMin(long dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateTime);

		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 将日期时间的值去除时间的 (分钟  % 10)、秒和毫秒，仅仅保留日期、小时
	 */
	public static long trimDateTimeMin10(long dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateTime);

		int min = cal.get(Calendar.MINUTE);
		cal.set(Calendar.MINUTE, min - min % 10);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}


	/**
	 * 将日期时间的值去除时间的秒和毫秒，仅仅保留日期
	 */
	public static long trimDateTimeSecond(long dateTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(dateTime);

		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();
	}

	/**
	 * 创建一个数组，长度为len，每个元素的值为val
	 */
	public static byte[] createArray(int len, byte val) {
		byte[] b = new byte[len];
		for (int i = 0; i < b.length; i++)
			b[i] = val;

		return b;
	}


	/**
	 * 读取二进制文件
	 */
	public static byte[] readBinaryFile(String strFile) throws IOException {
		File file = new File(strFile);
		if (!file.exists())
			throw new IOException("Binary file:" + strFile + " is not exists");

		if (!file.isFile())
			throw new IOException(strFile + " is not a file");

		long len = file.length();
		if (len <= 0 || len >= 1024 * 1024)
			throw new IOException("Length of file (length: " + len
					+ ") is NOT valid");

		byte[] b = new byte[(int) len];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			fis.read(b);
			return b;
		} finally {
			if (fis != null)
				fis.close();
		}
	}


}
