package com.ptteng.academy.persistence.mapper;



import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.plugin.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: canary
 * @description: 账号
 * @author: xiaoweiba1028@gmail.com
 * @create: 2018-07-26 18:39
 **/
@Repository
public interface AccountMapper extends BaseMapper<Account> {
    List<AccountDto> findAccountByQuery(AccountQuery accountQuery);

    @Select("SELECT id, username, role_id FROM account WHERE id = #{id}")
    AccountDto findAccountById(Long id);

    @Select("SELECT id, username, password, role_id FROM account WHERE username = #{username}")
    AccountDto findAccountByName(@Param("username") String username);

    @Select("SELECT id, password FROM account WHERE password = #{password} AND id = #{id}")
    Boolean findAccountByPassword(@Param("id") Long id, @Param("password") String password);

    @Select("SELECT ac.id, ac.username, ro.role_name FROM account ac, role ro WHERE ac.username = #{accountName} GROUP BY ac.id")
    AccountDto findAccountLoginById(@Param("accountName") String accountName);
}
