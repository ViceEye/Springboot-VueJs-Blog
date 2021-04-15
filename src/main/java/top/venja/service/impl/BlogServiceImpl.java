package top.venja.service.impl;

import top.venja.entity.Blog;
import top.venja.mapper.BlogMapper;
import top.venja.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joka.Venja
 * @since 2021-03-29
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
