package cn.tanlw.smallapp.file.extractQQ;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 邮件发送.Util
 *
 * @author xuxueli 2016-3-12 15:06:20
 */
public class MailUtil {
    private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

    /**
     * @param toAddress   收件人邮箱
     * @param mailSubject 邮件主题
     * @param mailBody    邮件正文
     * @return
     */
    public static boolean sendMail(String toAddress, String mailSubject, String mailBody) {

        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();

            //email.setDebug(true);		// 将会打印一些log
            //email.setTLS(true);		// 是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
            //email.setSSL(true);

            email.setHostName("smtp.qq.com");

            email.setSmtpPort(25);
            email.setCharset("UTF-8");


            email.setAuthentication("***@qq.com", "***");
            email.setCharset("UTF-8");

            email.setFrom("***@163.com", "aliyun");
            email.addTo(toAddress);
            email.setSubject(mailSubject);
            email.setMsg(mailBody);
            //email.attach(attachment);	// add the attachment

            email.send();                // send the email
            return true;
        } catch (EmailException e) {
            logger.error(e.getMessage(), e);

        }
        return false;
    }

}
