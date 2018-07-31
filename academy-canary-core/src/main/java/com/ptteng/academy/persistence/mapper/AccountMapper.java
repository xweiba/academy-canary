package com.ptteng.academy.persistence.mapper;



import com.ptteng.academy.business.dto.AccountDto;
import com.ptteng.academy.business.query.AccountQuery;
import com.ptteng.academy.persistence.beans.Account;
import com.ptteng.academy.plugin.BaseMapper;
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

    @Select("SELECT id, username, role_id")
    AccountDto findAccountById(Long id);
}
