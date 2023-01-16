package com.template.app.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.result.UpdateResult;
import com.template.app.dto.LoginDTO;
import com.template.app.dto.UserDTO;
import com.template.app.dto.UserRegisterVO;
import com.template.app.entity.User;
import com.template.app.entity.enums.CustomConstants;
import com.template.app.service.IUserService;
import com.template.common.BizException;
import com.template.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Doug Liu
 * @since 2022-12-20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    /**
     * 过期时间24小时
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60;

    /**
     * 一个月过期时间
     */
    private static final long LONG_EXPIRE_TIME = 30 * 24 * 60 * 60;

    private final RedisUtil redisUtil;

    private final MongoTemplate mongoTemplate;

    @Override
    public UserDTO getUserDetail(String userId) {
        User user = mongoTemplate.findById(new ObjectId(userId), User.class);
        BusinessCheck.trueThrow(user == null || user.getDeleteFlag() == 1, 20002);
        UserDTO userDTO = MongoBeanUtil.copy(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public LoginDTO register(UserRegisterVO userRegisterVO) {
        User user = mongoTemplate.findOne(
                new Query(Criteria.where("nickname").is(userRegisterVO.getNickname())
                        .and("deleteFlag").is(0)), User.class);
        BusinessCheck.trueThrow(user != null, 20012);
        user = new User();
        user.setNickname(userRegisterVO.getNickname());
        setPassword(user, userRegisterVO.getPassword());
        saveUser(user);
//        return createUserToRedis(user, false);
        return MongoBeanUtil.copy(user, LoginDTO.class);
    }

    @Override
    public LoginDTO doLogin(String nickname, String password, Boolean rememberMe) {
        if (StringUtils.isNotBlank(nickname) && StringUtils.isNotBlank(password)) {
            User user = mongoTemplate.findOne(
                    new Query(Criteria.where("nickname").is(nickname)
                            .and("deleteFlag").is(0)), User.class);
            BusinessCheck.trueThrow(user == null, 20002);
            BusinessCheck.trueThrow(!PasswordUtil.validatePassword(password, user.getSalt(), user.getPassword()), 20010);
            return createUserToRedis(user, rememberMe);
        } else {
            throw new BizException(20009);
        }
    }

    @Override
    public void updateUser(UserRegisterVO userRegisterVO) {
        User user = mongoTemplate.findById(new ObjectId(userRegisterVO.getId()), User.class);
        BusinessCheck.trueThrow(user == null || user.getDeleteFlag() == 1, 20002);
        if (!user.getNickname().equals(userRegisterVO.getNickname())) {
            User checkName = mongoTemplate.findOne(
                    new Query(Criteria.where("nickname").is(userRegisterVO.getNickname())
                            .and("deleteFlag").is(0)), User.class);
            BusinessCheck.trueThrow(checkName != null, 20012);
        }
        user.setNickname(userRegisterVO.getNickname());
        updateById(user);
    }

    @Override
    public void checkNickname(String nickname) {
        User user = mongoTemplate.findOne(
                new Query(Criteria.where("nickname").is(nickname)
                        .and("deleteFlag").is(0)), User.class);
        BusinessCheck.trueThrow(user != null, 20012);
    }

    @Override
    public Boolean deleteUser(String id) {
        Query query = new Query(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();
        update.set("deleteFlag", 1);
        Date today = new Date();
        update.set("updateTime", today);
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        return result.getMatchedCount() > 0;
    }

    @Override
    public String saveUserInfo(Object userInfo) {
        Date today = new Date();
        JSON userInfoJson = JSONUtil.parse(userInfo);
        Document user = Document.parse(userInfoJson.toString());
        user.put("createTime", today);
        user.put("updateTime", today);
        user.put("deleteFlag", 0);
        mongoTemplate.insert(user, "user");
        return user.get("_id").toString();
    }

    /**
     * 创建用户至redis
     * @param user       用户信息
     * @param rememberMe 记忆用户信息
     * @return LoginDTO
     */
    private LoginDTO createUserToRedis(User user, Boolean rememberMe) {
        UUID uuid = UUID.randomUUID();
        String token = CustomConstants.User.TOKEN_KEY + uuid;
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(user.getId().toString());
        loginDTO.setNickname(user.getNickname());
        loginDTO.setToken(uuid.toString());
        //普通用户权限
//        loginDTO.setApiList(userUriList);
        //普通用户菜单
//        loginDTO.setMenuList(userMenuList);
        Map<String, Object> map = BeanUtil.beanToMap(loginDTO);
        if (rememberMe != null && rememberMe) {
            redisUtil.hmset(token, map, LONG_EXPIRE_TIME);
        } else {
            redisUtil.hmset(token, map, EXPIRE_TIME);
        }
        //给前端返回的时候清空api列表
        loginDTO.setApiList(null);
        return loginDTO;
    }

    /**
     * 保存用户
     * @param user 用户信息
     */
    private void saveUser(User user) {
        Date today = new Date();
        user.setCreateTime(today);
        user.setUpdateTime(today);
        user.setDeleteFlag(0);
        mongoTemplate.save(user);
    }

    /**
     * 根据用户ID更新
     * @param user 用户信息
     * @return Boolean
     */
    public Boolean updateById(User user) {
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        if (StringUtils.isNotBlank(user.getNickname())) {
            update.set("nickname", user.getNickname());
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            update.set("password", user.getPassword());
        }
        if (StringUtils.isNotBlank(user.getSalt())) {
            update.set("salt", user.getSalt());
        }
        if (user.getBirthday() != null) {
            update.set("birthday", user.getBirthday());
        }
        update.set("updateTime", new Date());
        UpdateResult result = mongoTemplate.updateFirst(query, update, User.class);
        return result.getMatchedCount() > 0;
    }

    /**
     * 设置用户密码
     * @param user     用户信息
     * @param password 密码
     */
    private void setPassword(User user, String password) {
        user.setPassword(password);
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(encrypt(user));
    }

    /**
     * 加密用户密码
     * @param user 用户信息
     * @return String
     */
    private String encrypt(User user) {
        return PasswordUtil.encryptPassword(user.getPassword(), user.getSalt());
    }

}
