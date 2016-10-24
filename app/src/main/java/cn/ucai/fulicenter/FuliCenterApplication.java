package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;

import test.greenDAO.dao.DaoMaster;
import test.greenDAO.dao.DaoSession;


public class FuLiCenterApplication extends Application {
    public static FuLiCenterApplication application;
    private static FuLiCenterApplication instance;

    private static String username;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;



    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        instance = this;
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            test.greenDAO.dao.DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,"FULI.db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static FuLiCenterApplication getInstance(){
        if(instance==null){
            instance = new FuLiCenterApplication();
        }
        return instance;
    }





    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        FuLiCenterApplication.username = username;
    }
}
