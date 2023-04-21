package com.flrjcx.xypt.common.utils;

/**
 * @author
 * @title CheckIdCardUtils
 * @description 信息校验工具
 * @dat/                                                                                                                                                            e 2022/3/30 11:16
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUsersUtils {
    /**
     * 身份证号码中的出生日期的格式
     */
    private final static String BIRTH_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 正则表达式
     */
    private static final String REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
    private static final String REGEX_EMAIL =  "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final String REGEX_NICK_NAME = "^[\\u4E00-\\u9FA5A-Za-z0-9_-]{2,10}$";
    private static final String REGEX_REAL_NAME = "^[\\u4E00-\\u9FA5]{2,6}$";
    private static final String REGEX_ACCOUNT = "^[a-zA-Z0-9_-]{4,16}$";
    private static final String REGEX_PWD = "^[a-zA-Z0-9_-]{4,16}$";
    /**
     * 日期正则表达式的匹配格式为 yyyy-mm-dd
     * 匹配规则（按以下序号为顺序）：
     *
     *     1.匹配除了2月份之外的1-30日
     *
     *     2.若1无法匹配，则匹配1，3，5，7，8，10，12月份的31日
     *
     *     3.若2无法匹配，则匹配2月份的1-28日
     *
     *     4.若以上都无法匹配，那只可能剩下一天，那就是闰年2月份的最后一天2月29日
     */
    private static final String REGEX_DATE = "((((19|20)\\d{2})-(0?(1|[3-9])|1[012])-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-(0?[13578]|1[02])-31)|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-29))$";
    /** 手机号长度 */
    private static int PHONE_LENGTH = 11;
    /**身份证的最小出生日期,1900年1月1日*/
    private final static Date MINIMAL_BIRTH_DATE = new Date(-2209017600000L);

    private final static int NEW_CARD_NUMBER_LENGTH = 18;

    private final static int OLD_CARD_NUMBER_LENGTH = 15;
    /**
     * 18位身份证中最后一位校验码
     */
    private final static char[] VERIFY_CODE = {'1', '0', 'X', '9', '8', '7',
            '6', '5', '4', '3', '2'};
    /**
     * 18位身份证中，各个数字的生成校验码时的权值
     */
    private final static int[] VERIFY_CODE_WEIGHT = {7, 9, 10, 5, 8, 4, 2, 1,
            6, 3, 7, 9, 10, 5, 8, 4, 2};


    /**
     * 检验表格文本长度身份证手机号等方法
     */


    /**
     * 如果是15位身份证号码，则自动转换为18位
     *
     * @param cardNumber
     * @return
     */
    public static boolean checkIdCard(String cardNumber) {
        if (null != cardNumber) {
            cardNumber = cardNumber.trim();
            if (OLD_CARD_NUMBER_LENGTH == cardNumber.length()) {
                cardNumber = convertToNewCardNumber(cardNumber);
            }
            return validate(cardNumber);
        }
        return false;
    }

    public static boolean validate(String cardNumber) {
        boolean result = true;
        // 身份证号不能为空
        result = result && (null != cardNumber);
        // 身份证号长度是18(新证)
        result = result && NEW_CARD_NUMBER_LENGTH == cardNumber.length();
        // 身份证号的前17位必须是阿拉伯数字
        for (int i = 0; result && i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            result = result && ch >= '0' && ch <= '9';
        }
        // 身份证号的第18位校验正确
        result = result
                && (calculateVerifyCode(cardNumber) == cardNumber
                .charAt(NEW_CARD_NUMBER_LENGTH - 1));
        // 出生日期不能晚于当前时间，并且不能早于1900年
        try {
            Date birthDate = new SimpleDateFormat(BIRTH_DATE_FORMAT)
                    .parse(getBirthDayPart(cardNumber));
            result = result && null != birthDate;
            result = result && birthDate.before(new Date());
            result = result && birthDate.after(MINIMAL_BIRTH_DATE);
            /**
             * 出生日期中的年、月、日必须正确,比如月份范围是[1,12],
             * 日期范围是[1,31]，还需要校验闰年、大月、小月的情况时，
             * 月份和日期相符合
             */
            String birthdayPart = getBirthDayPart(cardNumber);
            String realBirthdayPart = new SimpleDateFormat(BIRTH_DATE_FORMAT)
                    .format(birthDate);
            result = result && (birthdayPart.equals(realBirthdayPart));
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static String getBirthDayPart(String cardNumber) {
        String birthDate = cardNumber.substring(6, 14);
        return new StringBuilder()
                .append(birthDate, 0, 4)
                .append("-")
                .append(birthDate, 4, 6)
                .append("-")
                .append(birthDate, 6, 8)
                .toString();
    }

    /**
     * 校验码（第十八位数）：
     * <p>
     * 十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0...16 ，先对前17位数字的权求和；
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2;
     * 计算模 Y = mod(S, 11)< 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10
     * 校验码: 1 0 X 9 8 7 6 5 4 3 2
     *
     * @param cardNumber
     * @return cardNumber
     */
    private static char calculateVerifyCode(CharSequence cardNumber) {
        int sum = 0;
        for (int i = 0; i < NEW_CARD_NUMBER_LENGTH - 1; i++) {
            char ch = cardNumber.charAt(i);
            sum += ((int) (ch - '0')) * VERIFY_CODE_WEIGHT[i];
        }
        return VERIFY_CODE[sum % 11];
    }

    /**
     * 把15位身份证号码转换到18位身份证号码<br>
     * 15位身份证号码与18位身份证号码的区别为：<br>
     * 1、15位身份证号码中，"出生年份"字段是2位，转换时需要补入"19"，表示20世纪<br>
     * 2、15位身份证无最后一位校验码。18位身份证中，校验码根据根据前17位生成
     *
     * @param oldCardNumber
     * @return oldCardNumber
     */
    private static String convertToNewCardNumber(String oldCardNumber) {
        StringBuilder buf = new StringBuilder(NEW_CARD_NUMBER_LENGTH);
        buf.append(oldCardNumber.substring(0, 6));
        buf.append("19");
        buf.append(oldCardNumber.substring(6));
        buf.append(calculateVerifyCode(buf));
        return buf.toString();
    }


    public static String hasDigit(String content) {
        String reg = "\\d{17}[\\d|x|X]|\\d{15}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 校验手机号格式是否正确
     *
     * @param phone
     * @return
     * @author tqf
     * @Description 手机号格式校验
     * @Version 1.0
     * @since 2022-08-09 23:28
     */
    public static Boolean regexPhone(String phone) {
        return Pattern.compile(REGEX_PHONE).matcher(phone).matches();
    }

    /**
     * 校验用户昵称
     * 规则:
     *      1.特殊字符只允许'_'和'-'
     *      2.长度2-10位
     * @param nickName
     * @return
     */
    public static Boolean regexNickName(String nickName) {
        return Pattern.compile(REGEX_NICK_NAME).matcher(nickName).matches();
    }

    /**
     * 校验用户帐号
     * 规则:
     *      1.特殊字符只允许'_'和'-'
     *      2.长度2-10位
     * @param account
     * @return
     */
    public static Boolean regexAccount(String account) {
        return Pattern.compile(REGEX_ACCOUNT).matcher(account).matches();
    }

    /**
     * 校验用户密码
     * 规则:
     *      1.特殊字符只允许'_'和'-'
     *      2.长度2-10位
     * @param password
     * @return
     */
    public static Boolean regexPassword(String password) {
        return Pattern.compile(REGEX_PWD).matcher(password).matches();
    }

    /**
     * 校验日期格式
     * yyyy-mm-dd
     * 详细规则请查看REGEX_DATE的注释
     * @param date 日期 yyyy-mm-dd格式
     * @return
     */
    public static Boolean regexDate(String date) {
        return Pattern.compile(REGEX_DATE).matcher(date).matches();
    }

    /**
     * 查询字段限制长度length
     * @param value
     * @return
     */
    public static boolean txtLength(int fieldTypeId, String value,int fieldValueLength) {
        return value.length() <= fieldValueLength;
    }

    public static boolean checkDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            /*判断格式（精确到秒）*/
            sdf.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean regexEmail(String email) {
        return Pattern.compile(REGEX_EMAIL).matcher(email).matches();
    }

    /**
     * 校验用户真实姓名
     * @param realName 真实姓名
     * @return
     */
    public static boolean regexRealName(String realName){
        return Pattern.compile(REGEX_REAL_NAME).matcher(realName).matches();
    }

}