package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.constants.HttpStateConstants;
import com.flrjcx.xypt.common.constants.MessageConstants;
import com.flrjcx.xypt.common.model.result.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * 邮件发送工具类
 *
 * @author Flrjcx
 */
@Slf4j
@Component
public class EmailSendUtils {
    @Value("${spring.mail.username}")
    private String account;
    @Resource
    private JavaMailSender javaMailSender;
    /**
     * 发送普通自定义内容邮件
     * @param address:对方邮件地址
     * @param subject:标题
     * @param body:内容
     * @return
     */
    public ResponseData sendMail(String address, String subject, String body, Integer validateCode) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(account);
            helper.setTo(address);
            helper.setSubject(subject);//主题
            mimeMessage.setContent("<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Document</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div>\n" +
                            "        <includetail>\n" +
                            "            <div align=\"center\">\n" +
                            "                <div class=\"open_email\" style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\">\n" +
                            "                    <div>\n" +
                            "                        <br>\n" +
                            "                        <span class=\"genEmailContent\">\n" +
                            "                            <div id=\"cTMail-Wrap\"\n" +
                            "                                 style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\">\n" +
                            "                                <div class=\"main-content\" style=\"\">\n" +
                            "                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                        <tbody>\n" +
                            "                                        <tr style=\"font-weight:300\">\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                            <td style=\"max-width:600px;\">\n" +
                            "                                                <div id=\"cTMail-logo\" style=\"width:92px; height:30px;\">\n" +
                            "                                                    <a href=\"\">\n" +
                            "                                                        <img border=\"0\" src=\"https://oss.flrjcx.cn/common/emailLogoTest.jpeg\"\n" +
                            "                                                             style=\"width:46px; height:5px; display:block\">\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p>\n" +
                            "    \n" +
                            "                                                <div id=\"cTMail-inner\" style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:480px;text-align:left;\">\n" +
                            "                                                                <h1 id=\"cTMail-title\" style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">\n" +
                            "                                                                    【校园达论】\n" +
                            "                                                                </h1>\n" +
                            "    \n" +
                            "                                                                <p id=\"cTMail-userName\" style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">\n" +
                            "                                                                    您好，感谢您使用校园达论！\n" +
                            "                                                                </p>\n" +
                            "    \n" +

                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">"+body+"<br/><b>"+validateCode+"</b>\n" +
                            "                                                                        <br/><span >如非本人操作请忽略！</span>\n" +
                            "                                                                    </span>\n" +
                            "                                                                </p>\n" +
//                            "                                                                <p class=\"cTMail-content\"\n" +
//                            "                                                                   style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                     <a href=\""+code+"\" title=\"\"\n" +
//                            "                                                                           style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">\n" +
//                            "                                                                            "+code+"" +
//                            "                                                                        </a>\n" +
//                            "                                                                </p>\n" +
//                            "    \n" +
//                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 3px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                        无法正常激活？请将链接复制到浏览器手动激活\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                       \n" +
//                            "                                                                    </span>\n" +
//                            "                                                                </p>\n" +
                            "                                                                <dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\">\n" +
                            "                                                                    <dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\">\n" +
                            "                                                                        <p id=\"cTMail-sender\" style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">\n" +
                            "                                                                            此致\n" +
                            "                                                                            <br>\n" +
                            "                                                                            <strong>校园达论团队</strong>\n" +
                            "                                                                        </p>\n" +
                            "                                                                    </dd>\n" +
                            "                                                                </dl>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                                <div id=\"cTMail-copy\" style=\"text-align:center; font-size:12px; line-height:18px; color:#999\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:540px;\">\n" +
                            "    \n" +
                            "                                                                <p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">\n" +
                            "                                                                    此为系统邮件，请勿回复。\n" +
                            "                                                                    <a href=\"\"\n" +
                            "                                                                       style=\"text-decoration:none;word-break:break-all;word-wrap:normal; color: #333;\" target=\"_blank\">\n" +
                            "                                                                        取消订阅\n" +
                            "                                                                    </a>\n" +
                            "                                                                </p>\n" +
                            "                                                                <p id=\"cTMail-rights\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">\n" +
                            "                                                                    \n" +
                            "                                                                    关注服务号，掌握最新校园动态\n" +
                            "                                                                    <br>\n" +
                            "                                                                   <p id=\"cTMail-rights2\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">CopyRight @ 2021-2022 </p>\n" +
                            "                                                                </p>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                            </td>\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                        </tr>\n" +
                            "                                        </tbody>\n" +
                            "                                    </table>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </includetail>\n" +
                            "    </div>\n" +
                            "    \n" +
                            "    </form>\n" +
                            "</body>\n" +
                            "</html>\n"
                    , "text/html ;charset=gbk");
            javaMailSender.send(mimeMessage);
            return ResponseData.buildResponse( MessageConstants.SEND_EMAIL_PASS,HttpStateConstants.HTTP_STATE_200);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.buildErrorResponse(HttpStateConstants.HTTP_STATE_402,MessageConstants.SEND_EMAIL_FAIL);
        }
    }
    public ResponseData sendMail(String address, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(account);
            helper.setTo(address);
            helper.setSubject(subject);//主题
            mimeMessage.setContent("<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Document</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div>\n" +
                            "        <includetail>\n" +
                            "            <div align=\"center\">\n" +
                            "                <div class=\"open_email\" style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\">\n" +
                            "                    <div>\n" +
                            "                        <br>\n" +
                            "                        <span class=\"genEmailContent\">\n" +
                            "                            <div id=\"cTMail-Wrap\"\n" +
                            "                                 style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\">\n" +
                            "                                <div class=\"main-content\" style=\"\">\n" +
                            "                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                        <tbody>\n" +
                            "                                        <tr style=\"font-weight:300\">\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                            <td style=\"max-width:600px;\">\n" +
                            "                                                <div id=\"cTMail-logo\" style=\"width:92px; height:30px;\">\n" +
                            "                                                    <a href=\"\">\n" +
                            "                                                        <img border=\"0\" src=\"https://oss.flrjcx.cn/common/emailLogoTest.jpeg\"\n" +
                            "                                                             style=\"width:46px; height:5px; display:block\">\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p>\n" +
                            "    \n" +
                            "                                                <div id=\"cTMail-inner\" style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:480px;text-align:left;\">\n" +
                            "                                                                <h1 id=\"cTMail-title\" style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">\n" +
                            "                                                                    【校园达论】\n" +
                            "                                                                </h1>\n" +
                            "    \n" +
                            "                                                                <p id=\"cTMail-userName\" style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">\n" +
                            "                                                                    您好，感谢您使用校园达论！\n" +
                            "                                                                </p>\n" +
                            "    \n" +

                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">"+body+"<br/><b>"+"</b>\n" +
                            "                                                                        <br/><span ></span>\n" +
                            "                                                                    </span>\n" +
                            "                                                                </p>\n" +
//                            "                                                                <p class=\"cTMail-content\"\n" +
//                            "                                                                   style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                     <a href=\""+code+"\" title=\"\"\n" +
//                            "                                                                           style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">\n" +
//                            "                                                                            "+code+"" +
//                            "                                                                        </a>\n" +
//                            "                                                                </p>\n" +
//                            "    \n" +
//                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 3px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                        无法正常激活？请将链接复制到浏览器手动激活\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                       \n" +
//                            "                                                                    </span>\n" +
//                            "                                                                </p>\n" +
                            "                                                                <dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\">\n" +
                            "                                                                    <dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\">\n" +
                            "                                                                        <p id=\"cTMail-sender\" style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">\n" +
                            "                                                                            此致\n" +
                            "                                                                            <br>\n" +
                            "                                                                            <strong>校园达论团队</strong>\n" +
                            "                                                                        </p>\n" +
                            "                                                                    </dd>\n" +
                            "                                                                </dl>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                                <div id=\"cTMail-copy\" style=\"text-align:center; font-size:12px; line-height:18px; color:#999\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:540px;\">\n" +
                            "    \n" +
                            "                                                                <p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">\n" +
                            "                                                                    此为系统邮件，请勿回复。\n" +
                            "                                                                    <a href=\"\"\n" +
                            "                                                                       style=\"text-decoration:none;word-break:break-all;word-wrap:normal; color: #333;\" target=\"_blank\">\n" +
                            "                                                                        取消订阅\n" +
                            "                                                                    </a>\n" +
                            "                                                                </p>\n" +
                            "                                                                <p id=\"cTMail-rights\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">\n" +
                            "                                                                    \n" +
                            "                                                                    关注服务号，掌握最新校园动态\n" +
                            "                                                                    <br>\n" +
                            "                                                                   <p id=\"cTMail-rights2\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">CopyRight @ 2021-2022 </p>\n" +
                            "                                                                </p>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                            </td>\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                        </tr>\n" +
                            "                                        </tbody>\n" +
                            "                                    </table>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </includetail>\n" +
                            "    </div>\n" +
                            "    \n" +
                            "    </form>\n" +
                            "</body>\n" +
                            "</html>\n"
                    , "text/html ;charset=gbk");
            javaMailSender.send(mimeMessage);
            return ResponseData.buildResponse( MessageConstants.SEND_EMAIL_PASS,HttpStateConstants.HTTP_STATE_200);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.buildErrorResponse(HttpStateConstants.HTTP_STATE_402,MessageConstants.SEND_EMAIL_FAIL);
        }
    }

    /**
     * 发送固定内容
     * @param address:收件地址
     * @param code:验证码
     * @return
     */
    public ResponseData sendFixedMail(String address,String code) {
        String subject = "校园达论注册";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(account);
            helper.setTo(address);
            helper.setSubject(subject);//主题
            mimeMessage.setContent("<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "    <title>Document</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "    <div>\n" +
                            "        <includetail>\n" +
                            "            <div align=\"center\">\n" +
                            "                <div class=\"open_email\" style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\">\n" +
                            "                    <div>\n" +
                            "                        <br>\n" +
                            "                        <span class=\"genEmailContent\">\n" +
                            "                            <div id=\"cTMail-Wrap\"\n" +
                            "                                 style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\">\n" +
                            "                                <div class=\"main-content\" style=\"\">\n" +
                            "                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                        <tbody>\n" +
                            "                                        <tr style=\"font-weight:300\">\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                            <td style=\"max-width:600px;\">\n" +
                            "                                                <div id=\"cTMail-logo\" style=\"width:92px; height:30px;\">\n" +
                            "                                                    <a href=\"\">\n" +
                            "                                                        <img border=\"0\" src=\"https://oss.flrjcx.cn/common/emailLogoTest.jpeg\"\n" +
                            "                                                             style=\"width:46px; height:5px; display:block\">\n" +
                            "                                                    </a>\n" +
                            "                                                </div>\n" +
                            "                                                <p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%;margin-top:20px;\"></p>\n" +
                            "    \n" +
                            "                                                <div id=\"cTMail-inner\" style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:480px;text-align:left;\">\n" +
                            "                                                                <h1 id=\"cTMail-title\" style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">\n" +
                            "                                                                    【校园达论】欢迎注册\n" +
                            "                                                                </h1>\n" +
                            "    \n" +
                            "                                                                <p id=\"cTMail-userName\" style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">\n" +
                            "                                                                    您好，感谢您在校园达论注册账户！\n" +
                            "                                                                </p>\n" +
                            "    \n" +

                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">输入验证码完成注册<br/>您的本次验证码为:<b>"+code+"</b>\n" +
                            "                                                                        <br/><span >验证码30分钟过期,如非本人操作请忽略！</span>\n" +
                            "                                                                    </span>\n" +
                            "                                                                </p>\n" +
//                            "                                                                <p class=\"cTMail-content\"\n" +
//                            "                                                                   style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                     <a href=\""+code+"\" title=\"\"\n" +
//                            "                                                                           style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">\n" +
//                            "                                                                            "+code+"" +
//                            "                                                                        </a>\n" +
//                            "                                                                </p>\n" +
//                            "    \n" +
//                            "                                                                <p class=\"cTMail-content\" style=\"line-height: 24px; margin: 3px 0px 0px; overflow-wrap: break-word; word-break: break-all;\">\n" +
//                            "                                                                    <span style=\"color: rgb(51, 51, 51); font-size: 14px;\">\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                        无法正常激活？请将链接复制到浏览器手动激活\n" +
//                            "                                                                        <br>\n" +
//                            "                                                                       \n" +
//                            "                                                                    </span>\n" +
//                            "                                                                </p>\n" +
                            "                                                                <dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\">\n" +
                            "                                                                    <dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\">\n" +
                            "                                                                        <p id=\"cTMail-sender\" style=\"font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">\n" +
                            "                                                                            此致\n" +
                            "                                                                            <br>\n" +
                            "                                                                            <strong>校园达论团队</strong>\n" +
                            "                                                                        </p>\n" +
                            "                                                                    </dd>\n" +
                            "                                                                </dl>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                                <div id=\"cTMail-copy\" style=\"text-align:center; font-size:12px; line-height:18px; color:#999\">\n" +
                            "                                                    <table style=\"width:100%;font-weight:300;margin-bottom:10px;border-collapse:collapse\">\n" +
                            "                                                        <tbody>\n" +
                            "                                                        <tr style=\"font-weight:300\">\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                            <td style=\"max-width:540px;\">\n" +
                            "    \n" +
                            "                                                                <p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">\n" +
                            "                                                                    此为系统邮件，请勿回复。\n" +
                            "                                                                    <a href=\"\"\n" +
                            "                                                                       style=\"text-decoration:none;word-break:break-all;word-wrap:normal; color: #333;\" target=\"_blank\">\n" +
                            "                                                                        取消订阅\n" +
                            "                                                                    </a>\n" +
                            "                                                                </p>\n" +
                            "                                                                <p id=\"cTMail-rights\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">\n" +
                            "                                                                    \n" +
                            "                                                                    关注服务号，掌握最新校园动态\n" +
                            "                                                                    <br>\n" +
                            "                                                                   <p id=\"cTMail-rights2\" style=\"max-width: 100%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\">CopyRight @ 2021-2022 </p>\n" +
                            "                                                                </p>\n" +
                            "                                                            </td>\n" +
                            "                                                            <td style=\"width:3.2%;max-width:30px;\"></td>\n" +
                            "                                                        </tr>\n" +
                            "                                                        </tbody>\n" +
                            "                                                    </table>\n" +
                            "                                                </div>\n" +
                            "                                            </td>\n" +
                            "                                            <td style=\"width:3%;max-width:30px;\"></td>\n" +
                            "                                        </tr>\n" +
                            "                                        </tbody>\n" +
                            "                                    </table>\n" +
                            "                                </div>\n" +
                            "                            </div>\n" +
                            "                        </span>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </includetail>\n" +
                            "    </div>\n" +
                            "    \n" +
                            "    </form>\n" +
                            "</body>\n" +
                            "</html>\n"
                    , "text/html ;charset=gbk");
            javaMailSender.send(mimeMessage);
            return ResponseData.buildResponse( MessageConstants.SEND_EMAIL_PASS,HttpStateConstants.HTTP_STATE_200);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseData.buildErrorResponse(HttpStateConstants.HTTP_STATE_402,MessageConstants.SEND_EMAIL_FAIL);
        }
    }



    /**
     * 发送带附件的邮件
     * @param address:对方邮件地址
     * @param subject:标题
     * @param body:内容
     * @param attach:附件
     * @return
     * @throws MessagingException
     * @throws IOException
     */
    public String sendAttachmentMail(String address,String subject,String body,MultipartFile attach) throws MessagingException, IOException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom(account);
        mimeMessageHelper.setTo(new String[]{address});
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        //文件路径
        byte[] bytes = attach.getBytes();
        String name = attach.getName();
        mimeMessageHelper.addAttachment(name, new ByteArrayResource(bytes));
        log.info("fileName:{}", name);
        javaMailSender.send(mimeMailMessage);
        return "发送成功";
    }


}
