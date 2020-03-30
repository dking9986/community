package note.dk.community.community.service;

import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser=userMapper.findByAccountId(user.getAccountId());//从数据库中找对应的user 赋值给数据库dbuser
        if (dbuser==null){
            //插入
            user.setGmtCreate(System.currentTimeMillis());//获取创建时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新数据库中所存的user信息
            dbuser.setGmtModified(System.currentTimeMillis());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
        }
    }
}
