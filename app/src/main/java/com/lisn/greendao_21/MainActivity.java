package com.lisn.greendao_21;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.dao.async.AsyncSession;
import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import user.db.DaoMaster;
import user.db.DaoSession;
import user.db.User;
import user.db.User2Dao;
import user.db.UserDao;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button bt_add;
    private Button bt_delete;
    private Button bt_update;
    private Button bt_query;
    private LinearLayout activity_main;
    private UserDao userDao;
    private User2Dao user2Dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initGreenDao();

        //数据库升级
//        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, 数据库名称, null);
//        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
    }


    private void initGreenDao() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "user.db", null);
        //获取数据库实例对象
        SQLiteDatabase db = openHelper.getWritableDatabase();

        //获取DaoMaster对象
        DaoMaster daoMaster = new DaoMaster(db);

        //获取DaoSession对象
        DaoSession daoSession = daoMaster.newSession();

        //异步查询，异步查询，异步查询
//        AsyncSession asyncSession = daoSession.startAsyncSession();

        //获取userDao对象
        userDao = daoSession.getUserDao();
//        user2Dao = daoSession.getUser2Dao();
    }

    private void initView() {
        textView = (TextView) findViewById(R.id.textView);
        bt_add = (Button) findViewById(R.id.bt_add);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_query = (Button) findViewById(R.id.bt_query);
        activity_main = (LinearLayout) findViewById(R.id.activity_main);

        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                add();
                break;
            case R.id.bt_delete:
                delete();
                break;
            case R.id.bt_update:
                update();
                break;
            case R.id.bt_query:
                query();
                break;
        }
    }

    //修改
    private void update() {
        //1.新建一个对象，根据id号修改，id不能为空
        User liSi = new User(2L, "LiSi", 27, "188888888888");
        userDao.update(liSi);


        //2.将王五改为大刀王五，如果有多个，此处只修改第一个
        List<User> students = userDao.queryBuilder().where(UserDao.Properties.Name.eq("wangwu")).list();
        User entity = students.get(0);
        entity.setName("大刀王五");
        userDao.update(entity);

    }
    //查询
    private void query() {
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        //1.list()方法查询所有记录
        List<User> userList = queryBuilder.list();
        for (User user : userList) {
            System.out.println("1--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //2.查询指定条件
        QueryBuilder<User> userQueryBuilder = queryBuilder.where(UserDao.Properties.Name.eq("zhangsan"));
        //下边这个是查询唯一的
        // User userQueryBuilder = queryBuilder.where(UserDao.Properties.Name.eq("zhangsan")).unique();

        List<User> list = userQueryBuilder.list();
        for (User user : list) {
            System.out.println("2--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //3.使用SQL语句进行查询
        List<User> users = userDao.queryRaw("where name = ?", "lisi");
        for (User user : users) {
            System.out.println("3--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //4.limit用法是取出查询记录的N条：
        //查询前两条 limit(2)
        List<User> user4 = userDao.queryBuilder().limit(2).list();
        for (User user : user4) {
            System.out.println("4--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //5.offset是设置偏移量，下面是从第二个开始，查询三条数据：
        List<User> user5 = userDao.queryBuilder().limit(3).offset(1).list();
        for (User user : user5) {
            System.out.println("5--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //6.orderAsc是对查询的数据进行升序排序，orderDesc是降序排序，此处根据年龄进行升序排序：
        List<User> user6 = userDao.queryBuilder().orderAsc(UserDao.Properties.Age).list();
        for (User user : user6) {
            System.out.println("6--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //7.懒加载查询所有 注意：closeLazyList集合
        LazyList<User> users7 = userDao.queryBuilder().listLazy();
        for (User user : users7) {
            System.out.println("7--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }
        users7.close();

        //8.通配符模糊查询 通过通配符%，查询所有name以zhangsan开头的对象
        List<User> users8 = userDao.queryBuilder().where(UserDao.Properties.Name.like("zhangsan%")).list();
        for (User user : users8) {
            System.out.println("8--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //9.通过between 查询年龄在18到30之间的对象
        List<User> users9 = userDao.queryBuilder().where(UserDao.Properties.Age.between(18, 30)).list();
        for (User user : users9) {
            System.out.println("9--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //10.通过gt查询年龄>18的对象，lt是小于，notEq是不等于，ge是 >= , le 是 <= 。
        List<User> users10 = userDao.queryBuilder().where(UserDao.Properties.Age.gt(18)).list();
        for (User user : users9) {
            System.out.println("10--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
        }

        //11.子线程查询数据
        queryThread();
    }

    /**
     * 子线程查询数据
     */
    public void queryThread(){
        final Query<User> build = userDao.queryBuilder().build();

        new Thread(){
            @Override
            public void run() {
                //注意：build.forCurrentThread()
                List<User> list = build.forCurrentThread().list();
                for (User user : list) {
                    System.out.println("list--user : "+user.getName() +"::"+user.getAge() +"::"+user.getTel());
                }
            }
        }.start();
    }

    //删除
    private void delete() {
        //1.根据key单个删除，删除id为2的（第二条）数据
        userDao.deleteByKey(2L);

        //删除大刀王五的第一条记录
        QueryBuilder<User> builder = userDao.queryBuilder().where(UserDao.Properties.Name.eq("大刀王五"));
        List<User> users = builder.list();
        userDao.delete(users.get(0));
    }
    //添加
    private void add() {
        User user1 = new User(null, "zhangsan", 12, "13811381234");
        User user2 = new User(null, "lisi", 22, "13822381234");
        User user3 = new User(null, "wangwu", 32, "13833381234");
        User user4 = new User(null, "nigulasi.zhaosi", 42, "13844381234");

        //插入到数据库
        userDao.insert(user1);

        // 开启事务插入
        userDao.insertInTx(user2);

        // 插入，如果存在覆盖之前的记录
        userDao.insertOrReplace(user3);

        userDao.insert(user4);

    }
}
