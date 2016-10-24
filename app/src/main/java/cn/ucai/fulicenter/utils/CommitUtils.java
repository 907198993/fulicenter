//package cn.ucai.fulicenter.utils;
//
//import android.content.Context;
//
//import java.util.List;
//
//import cn.ucai.fulicenter.db.DaoManager;
//
///**
// * Created by Administrator on 2016/10/24.
// */
//public class CommitUtils {
//    private DaoManager manager;
//
//    public CommitUtils(Context context) {
//        manager = DaoManager.getInstance();
//        manager.init(context);
//    }
//
//    /**
//     * 完成对数据库表的插入操作
//     *
//     * @param
//     * @return
//     */
//    public boolean insertUser(User user) {
//        boolean flag = false;
//        flag = manager.getDaoSession().insert(user) != -1 ? true : false;
//        return flag;
//    }
//
//    /**
//     * 完成对数据库的多次插入
//     *
//     * @param Users
//     * @return
//     */
//    public boolean insertMultUser(final List<User> Users) {
//        boolean flag = false;
//
//        try { // 启动一个线程，执行多次插入
//
//            manager.getDaoSession().runInTx(new Runnable() {
//                @Override
//                public void run() {
//                    for (User User : Users) {
//                        manager.getDaoSession().insertOrReplace(User);
//                    }
//
//                }
//            });
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    /**
//     * 更新对User某一条记录的修改
//     * @param User
//     * @return
//     */
//    public boolean updateUser(User User) {
//        boolean flag = false;
//        try {
//            manager.getDaoSession().update(User);
//            flag = true;
//        } catch (Exception e)
//
//        {
//            e.printStackTrace();
//        }
//
//
//        return flag;
//    }
//
//
//    /**
//     * 删除一条数据
//     * @param User
//     * @return
//     */
//    public boolean deleteUser(User User) {
//
//        boolean flag = false;
//        try {
//            // 删除一条记录
//            manager.getDaoSession().delete(User);
//
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        manager.getDaoSession().deleteAll(User.class);
//
//        return flag;
//
//    }
////
//
//    /**
//     * 返回多行记录
//     * @return
//     */
//    public List<User> ListAll() {
//        return manager.getDaoSession().loadAll(User.class);
//    }
//
//    /**
//     * 按照主键返回单行记录
//     * @param key
//     * @return
//     */
//    public User ListOneUser(long key) {
//        return manager.getDaoSession().load(User.class, key);
//    }
//
//
//    /**
//     * 查询数据 条件查询
//     * @return
//     */
//    public   List<User> Query1() {
//
//
//        return manager.getDaoSession().queryRaw(User.class, "where name like ? and _id > ?", new String[]{"%张三%", "2"});
//
//    }
//
//    /**
//     * 查询数据 对于数据库不熟悉可使用这种方式
//     * @return
//     */
////    public List<User> Query() {
////
////        QueryBuilder<User> builder = manager.getDaoSession().queryBuilder(User.class);
////        List<User> list = builder.where(UserDao.Properties.Age.between(23, 26))
////                .where(UserDao.Properties.Address.like("北京")).list();
//////        builder.orderAsc(UserDao.Properties.Age);
////
////        return list;
////    }
//
//}