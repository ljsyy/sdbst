package com.unifs.sdbst.app.bean.other;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 企业投票调查题目
 *
 * @author pxy 2018-08-31
 */
@Data
public class QyPollsTheme extends DataEntity<QyPollsTheme> {

    private static final long serialVersionUID = 1L;
    private String id;
    private String title;            //题目
    private Integer sort;            //排序
    private Integer countA = 0;        //A.非常满意，选择次数
    private Integer countB = 0;        //B.满意，选择次数
    private Integer countC = 0;        //C.可以接受，选择次数
    private Integer countD = 0;        //D.非常不满意，选择次数
    private Integer countAdvice = 0;    //建议的条数

}
