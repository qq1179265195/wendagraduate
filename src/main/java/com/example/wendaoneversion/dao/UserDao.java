package com.example.wendaoneversion.dao;

import com.example.wendaoneversion.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDao {
    String TABLE_NAME=" user";
    String INSERT_FIELDS=" name, password, salt, head_url ";
    String SELECT_FIELDS=" id," +INSERT_FIELDS;
    @Insert({"insert into ",TABLE_NAME,"(",INSERT_FIELDS,") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where id=#{id}"})
    User selectByid(int id);

    @Select({"select ",SELECT_FIELDS," from ",TABLE_NAME," where name=#{username}"})
    User selectByname(String username);

    @Update({"update ",TABLE_NAME," set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME," WHERE id = #{id}"})
    void deleteUser(int id);
}
