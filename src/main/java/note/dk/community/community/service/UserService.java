package note.dk.community.community.service;

import note.dk.community.community.mapper.UserMapper;
import note.dk.community.community.model.User;
import note.dk.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users=userMapper.selectByExample(userExample);//从数据库中找对应的user 赋值给数据库dbuser
        if (users.size()==0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());//获取创建时间
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            //更新数据库中所存的user信息
            User dbuser=users.get(0);
            User updateUser=new User();
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser, example);

        }
    }
}
