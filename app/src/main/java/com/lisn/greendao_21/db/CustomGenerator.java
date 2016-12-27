package com.lisn.greendao_21.db;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * ****************************
 * 项目名称：GreenDao2.1
 * 创建人：LiShan
 * 邮箱：cnlishan@163.com
 * 创建时间：2016/12/27
 * 版权所有违法必究
 * <p>
 * ****************************
 */


public class CustomGenerator {
    public static void main(String[] args) {
        //创建架构（版本号、数据库名）
        Schema schema=new Schema(1,"user.db");
        //添加实体对象User
        Entity user = schema.addEntity("User");
        user.addIdProperty(); //添加一个id的属性 主键、自增长、自动维护
        user.addStringProperty("name"); //name 的属性
        user.addIntProperty("age");
        user.addStringProperty("tel");

        //添加实体对象User2
        user=schema.addEntity("User2");
        user.addIdProperty();
        user.addStringProperty("name");
        user.addIntProperty("age");
        user.addStringProperty("tel");

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
