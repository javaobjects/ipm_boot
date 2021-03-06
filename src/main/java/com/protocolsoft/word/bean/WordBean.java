/**<p>Copyright © 北京协软科技有限公司版权所有。</p>
 * @author Administrator
 *      2018年3月20日
 */
package com.protocolsoft.word.bean;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


/**
 * @ClassName: WordBean
 * @Description: word报告首页表格对象
 * @author 刘斌
 *
 */
public class WordBean {
    /**
     * @Fields name : 报告名
     */
    private String name;
    /**
     * @Fields examNum : 报告时间
     */
    private String examNum;
    /**
     * @Fields idCard : 报告性质
     */
    private String idCard;
    /**
     * @Fields timeDepartment : 报告部门
     */
    private String timeDepartment;
    /**
     * @Fields carModel : 报告人
     */
    private String carModel;
    /**
     * @Fields timeStart : 报告开始时间
     */
    private String timeStart;
    /**
     * @Fields timeEnd : 报告结束时间
     */
    private String timeEnd;
    /**
     * @Fields parameter : 万能参数
     */
    private String parameter;
    /**
     * @Fields map : 图片名字与json文件名map
     */
    private Map<String, String> map;
    
    /**
     * @Fields modalId : 是否使用模板
     */
    private Integer modalId;
    
    /**
     * @Fields map : 图片名字与json文件名map
     */
    private List<JSONObject> jsonObjects;
    
    /**
     * <p>Title: </p>
     * <p>Description: 有参构造器</p>
     * @param name
     * @param examNum
     * @param iDCard
     * @param timeDepartment
     * @param carModel
     * @param timeStart
     * @param timeEnd
     */ 
    public WordBean(String name, String examNum, String iDCard,
                        String timeDepartment, String carModel, String timeStart,
                        String timeEnd) {
                super();
        this.name = name;
        this.examNum = examNum;
        this.idCard = iDCard;
        this.timeDepartment = timeDepartment;
        this.carModel = carModel;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
        
    /**
     * <p>Title: </p>
     * <p>Description: 有参构造器</p>
     * @param name
     * @param examNum
     * @param idCard
     * @param timeDepartment
     * @param carModel
     * @param timeStart
     * @param timeEnd
     * @param parameter
     */ 
    public WordBean(String name, String examNum, String idCard,
            String timeDepartment, String carModel, String timeStart,
            String timeEnd, String parameter) {
        super();
        this.name = name;
        this.examNum = examNum;
        this.idCard = idCard;
        this.timeDepartment = timeDepartment;
        this.carModel = carModel;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.parameter = parameter;
    }


    /**
     * <p>Title: </p>
     * <p>Description: 无参构造构造器</p>
     */ 
    public WordBean() {
                super();
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getExamNum() {
        return examNum;
    }
    public void setExamNum(String examNum) {
        this.examNum = examNum;
    }
    public String getIDCard() {
        return idCard;
    }
    public void setIDCard(String iDCard) {
        idCard = iDCard;
    }
    public String getTimeDepartment() {
        return timeDepartment;
    }
    public void setTimeDepartment(String timeDepartment) {
        this.timeDepartment = timeDepartment;
    }
    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
    public String getTimeStart() {
        return timeStart;
    }
    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }
    public String getTimeEnd() {
        return timeEnd;
    }
    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getIdCard() {
        return idCard;
    }


    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }


    public String getParameter() {
        return parameter;
    }


    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public Integer getModalId() {
        return modalId;
    }

    public void setModalId(Integer modalId) {
        this.modalId = modalId;
    }

    @Override
    public String toString() {
        return "WordBean [name=" + name + ", examNum=" + examNum + ", idCard="
                + idCard + ", timeDepartment=" + timeDepartment + ", carModel="
                + carModel + ", timeStart=" + timeStart + ", timeEnd="
                + timeEnd + ", parameter=" + parameter + ", map=" + map
                + ", modalId=" + modalId + "]";
    }

    public List<JSONObject> getJsonObjects() {
        return jsonObjects;
    }

    public void setJsonObjects(List<JSONObject> jsonObjects) {
        this.jsonObjects = jsonObjects;
    }
         
}
