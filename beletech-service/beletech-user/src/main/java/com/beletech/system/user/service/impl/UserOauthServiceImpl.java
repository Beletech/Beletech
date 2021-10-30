package com.beletech.system.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import com.beletech.system.user.entity.UserOauth;
import com.beletech.system.user.mapper.UserOauthMapper;
import com.beletech.system.user.service.IUserOauthService;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author XueBing
 */
@Service
@AllArgsConstructor
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

}
