package top.venja.service.impl;

import org.springframework.validation.annotation.Validated;
import top.venja.entity.User;
import top.venja.mapper.UserMapper;
import top.venja.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joka.Venja
 * @since 2021-02-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
