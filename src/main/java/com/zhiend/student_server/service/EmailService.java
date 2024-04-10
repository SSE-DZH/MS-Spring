package com.zhiend.student_server.service;

import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.zhiend.student_server.dto.EmailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮箱服务类
 * 该类提供发送邮件的功能。
 * 它负责使用配置的电子邮件帐户发送邮件。
 * 电子邮件配置参数从应用程序属性文件中获取。
 *
 * @author zhuhuix
 * @date 2021-07-19
 */
@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailService {

    // 从应用程序属性文件中获取的电子邮件配置参数
    @Value("${spring.mail.email}")
    private String email;
    //private String host = "smtp.163.com";
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    /**
     * 发送邮件
     *
     * @param emailDto 包含邮件详细信息的 EmailDto 对象，如收件人、主题和内容
     */
    public void send(EmailDto emailDto) {

        System.out.println(email);
        // 读取电子邮件配置
        if (email == null || host == null || port == null || username == null || password == null) {
            throw new RuntimeException("缺少电子邮件配置");
        }

        // 设置邮件帐户
        MailAccount account = new MailAccount();
        account.setHost(host);
        account.setPort(Integer.parseInt(port));
        account.setFrom(username + "<" + email + ">");
        account.setUser(username);
        account.setPass(password);
        account.setAuth(true);
        account.setSslEnable(true);
        account.setStarttlsEnable(true);

        log.info("开始发送了！！！！");

        // 发送邮件
        try {
            int size = emailDto.getTos().size();
            Mail.create(account)
                .setTos(emailDto.getTos().toArray(new String[size]))
                .setTitle(emailDto.getSubject())
                .setContent(emailDto.getContent())
                .setHtml(true)
                .setUseGlobalSession(false) // 关闭 session
                .send();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
