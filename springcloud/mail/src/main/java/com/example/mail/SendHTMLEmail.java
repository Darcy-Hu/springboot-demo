package com.example.mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendHTMLEmail {
    public static void main(String [] args){
        // 收件人电子邮箱
        String to = "1611818749@qq.com";

        // 发件人电子邮箱
        String from = "1289357001@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "localhost";  //QQ 邮件服务器
        String port = "8080";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        properties.put("mail.smtp.auth", "false");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties);
        /*Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("128xxxx", "xxxxx"); //发件人邮件用户名、授权码
            }
        });*/

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

            // 设置消息体
            message.setText("This is actual message");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
