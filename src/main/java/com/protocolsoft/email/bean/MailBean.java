package com.protocolsoft.email.bean;

/**
 * 
 * @ClassName: MailBean
 * @author 刘斌
 *
 */
public class MailBean {

    /**
     * 邮件标题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
    
    /**
     * 添加附件
     */
    private String addFile;
    
    /**
     * 附件发送名称
     */
    private String realName;
    
    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     */
    public MailBean(){

    }

    /**
     * 
     * <p>Title: </p>
     * <p>Description: </p>
     * @param subject
     * @param content
     * @param addFile
     * @param realName
     */
    public MailBean(String subject, String content, String addFile,
            String realName) {
        super();
        this.subject = subject;
        this.content = content;
        this.addFile = addFile;
        this.realName = realName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddFile() {
        return addFile;
    }

    public void setAddFile(String addFile) {
        this.addFile = addFile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}
