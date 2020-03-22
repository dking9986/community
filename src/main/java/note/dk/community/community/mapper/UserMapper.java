package note.dk.community.community.mapper;


import note.dk.community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {//该接口用来执行sql语句
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);//将参数的token放到上面#括号里去 如果这个参数不是类就需要加一个注解@Param（。。。）表示这是参数
    @Select("select * from user where account_id=#{accountId} limit 1")
    User findByAccountId(@Param("accountId") String accountId);
}
