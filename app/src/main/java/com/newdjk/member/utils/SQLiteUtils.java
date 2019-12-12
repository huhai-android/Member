package com.newdjk.member.utils;


import com.newdjk.member.MyApplication;
import com.newdjk.member.greendao.gen.DaoSession;
import com.newdjk.member.greendao.gen.ImDataEntityDao;
import com.newdjk.member.greendao.gen.PushDataDaoEntityDao;
import com.newdjk.member.ui.entity.ImDataEntity;
import com.newdjk.member.ui.entity.PushDataDaoEntity;

import java.util.ArrayList;
import java.util.List;

public class SQLiteUtils {
    private static SQLiteUtils instance;
    PushDataDaoEntityDao pushDataDaoEntityDao;
    ImDataEntityDao imDataEntityDao;
    DaoSession daoSession;

    private SQLiteUtils() {
        pushDataDaoEntityDao = MyApplication.getInstance().getDaoSession().getPushDataDaoEntityDao();
        imDataEntityDao = MyApplication.getInstance().getDaoSession().getImDataEntityDao();
        daoSession = MyApplication.getInstance().getDaoSession();
    }

    public static SQLiteUtils getInstance() {
        if (instance == null) {
            synchronized (SQLiteUtils.class) {
                if (instance == null) {
                    instance = new SQLiteUtils();
                }
            }
        }
        return instance;
    }

    //批量增加ImData
    public void addAllImDatas(final List<ImDataEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        imDataEntityDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i < list.size();i++) {
                    ImDataEntity imDataEntity =   list.get(i);
                    imDataEntityDao.insertOrReplace(imDataEntity);
                }
            }
        });
    }
    //增加单条ImData
    public void addImData(ImDataEntity dataBean) {
        imDataEntityDao.insert(dataBean);
    }
    //删除IM表中内容
    public void deleteAllImData() {
        imDataEntityDao.deleteAll();
    }

    //条件查询IM表中单条数据
    public ImDataEntity selectImDataEntity(String identifier) {
        List<ImDataEntity> list = imDataEntityDao.queryBuilder().where(ImDataEntityDao.Properties.Identifier.eq(identifier)).list();
        return list == null ?null :list.get(0);
    }

    //增加
    public void addPushData(PushDataDaoEntity testBean) {
        pushDataDaoEntityDao.insert(testBean);
    }

    //删除
    public void deletePushData(PushDataDaoEntity testBean) {
        pushDataDaoEntityDao.delete(testBean);
    }

    //修改
    public void updatePushData(PushDataDaoEntity testBean) {
        pushDataDaoEntityDao.update(testBean);
    }

    //条件查询
    public List<PushDataDaoEntity> selectAllContactsByDoctorId(String accountId) {
        pushDataDaoEntityDao.detachAll();//清除缓存

        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.AccountId.eq(accountId)).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }
    //条件查询系统消息
    public List<PushDataDaoEntity> selectAllSystemContactsByDoctorId(String accountId) {
        pushDataDaoEntityDao.detachAll();//清除缓存

        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.AccountId.eq(accountId),PushDataDaoEntityDao.Properties.Title.notEq("新患者报道")).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }
    //条件查询新患者报道消息
    public List<PushDataDaoEntity> selectAllReportsContactsByDoctorId(String accountId) {
        pushDataDaoEntityDao.detachAll();//清除缓存

        List<PushDataDaoEntity> list1 = pushDataDaoEntityDao.queryBuilder().where(PushDataDaoEntityDao.Properties.AccountId.eq(accountId),PushDataDaoEntityDao.Properties.Title.eq("新患者报道")).orderDesc(PushDataDaoEntityDao.Properties.Time).list();
        return list1 == null ? new ArrayList<PushDataDaoEntity>() : list1;
    }

    //删除表中内容
    public void deleteAllPushData() {
        pushDataDaoEntityDao.deleteAll();
    }

}
