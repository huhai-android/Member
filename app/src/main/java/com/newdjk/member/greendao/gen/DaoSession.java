package com.newdjk.member.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.newdjk.member.ui.entity.ChatHistoryEntity;
import com.newdjk.member.ui.entity.ImDataEntity;
import com.newdjk.member.ui.entity.PushDataDaoEntity;

import com.newdjk.member.greendao.gen.ChatHistoryEntityDao;
import com.newdjk.member.greendao.gen.ImDataEntityDao;
import com.newdjk.member.greendao.gen.PushDataDaoEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig chatHistoryEntityDaoConfig;
    private final DaoConfig imDataEntityDaoConfig;
    private final DaoConfig pushDataDaoEntityDaoConfig;

    private final ChatHistoryEntityDao chatHistoryEntityDao;
    private final ImDataEntityDao imDataEntityDao;
    private final PushDataDaoEntityDao pushDataDaoEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        chatHistoryEntityDaoConfig = daoConfigMap.get(ChatHistoryEntityDao.class).clone();
        chatHistoryEntityDaoConfig.initIdentityScope(type);

        imDataEntityDaoConfig = daoConfigMap.get(ImDataEntityDao.class).clone();
        imDataEntityDaoConfig.initIdentityScope(type);

        pushDataDaoEntityDaoConfig = daoConfigMap.get(PushDataDaoEntityDao.class).clone();
        pushDataDaoEntityDaoConfig.initIdentityScope(type);

        chatHistoryEntityDao = new ChatHistoryEntityDao(chatHistoryEntityDaoConfig, this);
        imDataEntityDao = new ImDataEntityDao(imDataEntityDaoConfig, this);
        pushDataDaoEntityDao = new PushDataDaoEntityDao(pushDataDaoEntityDaoConfig, this);

        registerDao(ChatHistoryEntity.class, chatHistoryEntityDao);
        registerDao(ImDataEntity.class, imDataEntityDao);
        registerDao(PushDataDaoEntity.class, pushDataDaoEntityDao);
    }
    
    public void clear() {
        chatHistoryEntityDaoConfig.clearIdentityScope();
        imDataEntityDaoConfig.clearIdentityScope();
        pushDataDaoEntityDaoConfig.clearIdentityScope();
    }

    public ChatHistoryEntityDao getChatHistoryEntityDao() {
        return chatHistoryEntityDao;
    }

    public ImDataEntityDao getImDataEntityDao() {
        return imDataEntityDao;
    }

    public PushDataDaoEntityDao getPushDataDaoEntityDao() {
        return pushDataDaoEntityDao;
    }

}