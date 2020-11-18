package com.unifs.sdbst.app.service.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.annotation.ServiceLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.bean.user.UserSwt;
import com.unifs.sdbst.app.bean.user.vo.PasswordVo;
import com.unifs.sdbst.app.bean.user.vo.RegisterVo;
import com.unifs.sdbst.app.bean.user.vo.UpdateVo;
import com.unifs.sdbst.app.dao.primary.user.UserMapper;
import com.unifs.sdbst.app.dao.primary.user.UserSwtMapper;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 　　*
 *
 * @version V1.0
 * @title: UserService
 * @projectName sdbst
 * @description: 用户业务逻辑处理类
 * @author： 张恭雨
 * @date 2019/8/13 10:43
 */


@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSwtMapper swtMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${AES.KEY}")
    private String key;
    @Value("${aes.key}")
    private String aseKey;
    @Value("${dexin.logig.url}")
    private String dexinLoginUrl;
    @Value("${AES.EPIDEMIC.KEY}")
    private String epidemicKey;
    //特殊符号正则式
    private static final String DEFAULT_QUERY_REGEX = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";


    //app用户注册业务逻辑处理
    public void register(RegisterVo vo) throws Exception {
        //校验用户名
        if (vo.getLoginName().length() < 6 || vo.getLoginName().length() > 20) {
            throw new MyException("用户名长度需为6-20位");
        }
        //验证是否存在特殊符号
        Pattern p = Pattern.compile(DEFAULT_QUERY_REGEX);
        Matcher m = p.matcher(vo.getLoginName());
        if (m.find()) {
            throw new MyException("用户名不能包含特殊符");
        }
        // 解密密码
        vo.setPassword(AES.decrypt(vo.getPassword(), key));
        vo.setConfirmPassword(AES.decrypt(vo.getConfirmPassword(), key));
        //判断两次输入的密码是否相等，
        if (!vo.getPassword().equals(vo.getConfirmPassword())) {
            throw new MyException("两次输入密码不一致", 0);
        }
        //判断该用户名是否存在
        User user = userMapper.getByLoginName(vo.getLoginName());
        if (user != null) {
            //该用户名已经存在
            throw new MyException("该用户名已存在", 0);
        }
        //判断该手机号是否已被绑定
        User tempUser = userMapper.selectByPhone(vo.getPhoneNumber());
        if (tempUser != null) {
            //该手机号已经存在
            throw new MyException("该手机号已绑定，请更换手机号", 0);
        }

        //插入预处理
        User saveUser = new User();
        saveUser.setLoginName(vo.getLoginName());
        saveUser.setPassword(EncryptUtil.entryptPassword(vo.getPassword()));
        saveUser.setPhone(vo.getPhoneNumber());
        saveUser.preInsert();

        userMapper.insert(saveUser);
    }


    public String registerByPhone(String phone, String code) throws Exception {
        //验证手机号是否正确
        if (!phone.matches("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")) {
            throw new MyException("手机号不正确！", 0);
        }
        //获取保存在服务器验证码
        String sysCode = redisUtil.get(phone);
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        if (!sysCode.equals(code)) {
            throw new MyException("验证码错误！", 0);
        }

        //判断该用户名是否存在
        User user = userMapper.getByLoginName(phone);
        if (user != null) {
            //该用户名已经存在
            throw new MyException("该手机号已被注册，请更换手机号", 0);
        }
        //判断该手机号是否已被绑定
        User tempUser = userMapper.selectByPhone(phone);
        if (tempUser != null) {
            //该手机号已经存在
            throw new MyException("该手机号已被注册，请更换手机号", 0);
        }

        //初始化user对象
        User saveUser = new User();
        saveUser.setPhone(phone);
        //将手机号作为登录名
        saveUser.setLoginName(phone);
        //随机生成默认密码
        String password = UUID.randomUUID().toString();
        saveUser.setPassword(EncryptUtil.entryptPassword(password));
        saveUser.preInsert();
        userMapper.insert(saveUser);
        return AES.encrypt(password, key);
    }


    public User verifyInfo(String loginName, String password, String loginFlag, HttpServletRequest request) throws Exception {
        //解密密码
        password = AES.decrypt(password, key);
        //查询该用户信息
        User user = userMapper.getByLoginName(loginName);
        if (user == null) {
            throw new MyException("该用户不存在！", 0);
        }
        //验证密码是否正确
        boolean isSame = EncryptUtil.validatePassword(password, user.getPassword());
        if (!isSame) {
            throw new MyException("密码错误！", 0);
        }
        //设置登录IP与登录时间
        user.setLoginIp(HttpUtil.getClientIp(request));
        user.setLoginDate(new Date());
        //设置登录类型，android/ios
        if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
            user.setLoginFlag(loginFlag);
        }
        //更新登录次数
        if (user.getLoginCount() == null) {
            user.setLoginCount(1);
        } else {
            user.setLoginCount(user.getLoginCount() + 1);
        }

        //记入数据库
        userMapper.update(user);
        return user;
    }


    public User verifyInfo(String loginName, String password, HttpServletRequest request) throws Exception {

        //查询该用户信息
        User user = userMapper.getByLoginName(loginName);
        if (user == null) {
            redisUtil.del("RANDOMVALIDATECODEKEY");
            throw new MyException("登录失败！", 0);
        }
        //验证密码是否正确
        boolean isSame = EncryptUtil.validatePassword(password, user.getPassword());
        if (!isSame) {
            redisUtil.del("RANDOMVALIDATECODEKEY");
            throw new MyException("登录错误！", 0);
        }
        //设置登录IP与登录时间
        user.setLoginIp(HttpUtil.getClientIp(request));
        user.setLoginDate(new Date());
        user.setLoginFlag("3");
        //更新登录次数
        if (user.getLoginCount() == null) {
            user.setLoginCount(1);
        } else {
            user.setLoginCount(user.getLoginCount() + 1);
        }

        //记入数据库
        userMapper.update(user);
        return user;
    }


    public boolean updatePhone(UpdateVo vo, HttpServletRequest request) throws Exception {
        //获取用户对象
        User user = getUser(request);
        //获取该号码收到的验证码
        String code = redisUtil.get(vo.getPhone());
        if (StringUtils.isEmpty(code)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        if (!vo.getCode().equals(code)) {
            throw new MyException("验证码错误！", 0);
        }
        //判断该手机号是否已被绑定
        User tempUser = userMapper.selectByPhone(vo.getPhone());
        if (tempUser != null) {
            //该手机号已经存在
            throw new MyException("该手机号已绑定，请更换手机号", 0);
        }

        user.preUpdate(request);
        //判断该用户的帐号名是否就是手机号，是则一同变更
        if (user.getLoginName().equals(user.getPhone())) {
            user.setLoginName(vo.getPhone());
            user.setPhone(vo.getPhone());
            userMapper.updatePhone(user);
        } else {
            user.setPhone(vo.getPhone());
            userMapper.update(user);
        }
        //获取更新后的对象放入redis 1小时失效
        User newUser = userMapper.selectById(user.getId());
        redisUtil.set(newUser.getId(), newUser.toString(), 60 * 60);
        return true;
    }

    public void changePassword(PasswordVo vo, HttpServletRequest request) throws Exception {
        //解密密码
        vo.setPassword(AES.decrypt(vo.getPassword(), key));
        //获取用户对象
        User user;
        user = getUser(request);
        //redis中不存在用户信息
        if (user == null) {
            //忘记密码修改模式，通过loginName获取user对象
            user = userMapper.getByLoginName(vo.getLoginName());
            if (user == null) {
                throw new MyException("该用户名不存在！");
            }
        }
        //获取验证码
        String verificationCode = redisUtil.get(user.getPhone());

        // 验证验证码是否过期
        if (StringUtils.isEmpty(verificationCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!vo.getCode().equals(verificationCode)) {
            throw new MyException("验证码错误！", 0);
        }
        //更新密码
        user.setPassword(EncryptUtil.entryptPassword(vo.getPassword()));
        userMapper.update(user);
    }

    //手机号验证码登录模式
    public User phoneLogin(String phone, String code, String loginFlag, HttpServletRequest request) {
        //获取验证码
        String sysCode = redisUtil.get(phone);

        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(code)) {
            throw new MyException("验证码错误！", 0);
        }
        //获取用户对象
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            throw new MyException("该手机号用户不存在", 0);
        }
        //设置登录IP与登录时间
        user.setLoginIp(HttpUtil.getClientIp(request));
        user.setLoginDate(new Date());
        //设置登录类型，android/ios
        if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
            user.setLoginFlag(loginFlag);
        }
        //更新登录次数
        if (user.getLoginCount() == null) {
            user.setLoginCount(1);
        } else {
            user.setLoginCount(user.getLoginCount() + 1);
        }
        //记入数据库
        userMapper.update(user);
        return user;
    }

    //手机号验证码登录模式-新逻辑
    public User phoneLogin(String phone, String code, String loginFlag, String token, HttpServletRequest request) {

        //获取用户对象
        User user = userMapper.selectById(token);
//        User user = selectIdRedisOrDB(token);
        if (user == null) {
            throw new MyException("该手机号用户不存在", 0);
        }
        //设置登录IP与登录时间
        user.setLoginIp(HttpUtil.getClientIp(request));
        user.setLoginDate(new Date());
        //设置登录类型，android/ios
        if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
            user.setLoginFlag(loginFlag);
        }
        //更新登录次数
        if (user.getLoginCount() == null) {
            user.setLoginCount(1);
        } else {
            user.setLoginCount(user.getLoginCount() + 1);
        }
        //记入数据库
        userMapper.update(user);
//        updateRedisOrDB(user);
        return user;
    }

    public User EmergencyLogin(String phone, String identity, String loginFlag,
                               String identityType, HttpServletRequest request) {
        User user = userMapper.selectByFactor(phone, identity, null);
        if (user == null) {
            throw new MyException("该用户不存在", 0);
        }
        //设置登录IP与登录时间
        user.setLoginIp(HttpUtil.getClientIp(request));
        user.setLoginDate(new Date());
        user.setIdentityType(identityType);
        //设置登录类型，android/ios
        if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
            user.setLoginFlag(loginFlag);
        }
        //更新登录次数
        if (user.getLoginCount() == null) {
            user.setLoginCount(1);
        } else {
            user.setLoginCount(user.getLoginCount() + 1);
        }
        //记入数据库
        userMapper.update(user);
        return user;
    }

    public User autoLogin(String loginName, String number, String identity, String loginFlag, HttpServletRequest request) throws Exception {
        User user = new User();
        //解密相关信息
        user.setLoginName(AES.decrypt(loginName, key));
        user.setPhone(AES.decrypt(number, key));
        user.setIdentityNumber(AES.decrypt(identity, key));
        //设置登录类型，android/ios
        if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
            user.setLoginFlag(loginFlag);
        }
        //验证身份证号是否已存在，不存在插入后登录，存在则直接登录
        User dbUser = userMapper.identityIsExist(user.getIdentityNumber());
        if (dbUser == null || "".equals(dbUser)) {
            // 随机生成默认密码
            String password = UUID.randomUUID().toString();
            user.setPassword(EncryptUtil.entryptPassword(password));
            user.setLoginCount(1);
            user.setLoginDate(new Date());
            user.setLoginIp(HttpUtil.getClientIp(request));
            user.preInsert();
            //插入记录
            userMapper.insert(user);
            return user;
        }
        //更新登录记录
        dbUser.setLoginCount(dbUser.getLoginCount() + 1);
        dbUser.setLoginDate(new Date());
        dbUser.setLoginIp(HttpUtil.getClientIp(request));
        userMapper.updateByIdentity(dbUser);
        //dbUser不为空
        return dbUser;

    }

    public User register(String loginName, String number, String identity,
                         String code, String loginFlag, String identityType, HttpServletRequest request) throws Exception {

        //验证身份证号不为空
        if (identity == null || "".equals(identity)) {
            throw new MyException("证件号不能为空！", 0);
        }
        //验证用户名不为空
        if (loginName == null || "".equals(loginName)) {
            throw new MyException("姓名不能为空！", 0);
        }

        User user = new User();
        //初始化值
        user.setLoginName(loginName);
        user.setPhone(number);
        user.setIdentityNumber(identity);
        user.setIdentityType(identityType);

        //获取验证码
        String sysCode = redisUtil.get(user.getPhone());

        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(code)) {
            throw new MyException("验证码错误！", 0);
        }

        //验证身份证号是否已存在，不存在插入
        User dbUser = userMapper.identityIsExist(user.getIdentityNumber());
//        User dbUser = identityIsExistRedisOrDB(identity);
        if (dbUser == null) {
            //生成默认密码
            String password = UUID.randomUUID().toString();
            user.setPassword(EncryptUtil.entryptPassword(password));
            user.setLoginCount(1);
            user.setLoginDate(new Date());
            user.setLoginIp(HttpUtil.getClientIp(request));
            //设置登录类型，android/ios
            if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
                user.setLoginFlag(loginFlag);
            }
            user.preInsert();
            //插入记录
            userMapper.insert(user);
//            saveRedisOrDB(user);
            return user;
        } else {
            //用户信息提示
            StringBuffer sb = new StringBuffer();
            String msg = "该证件号已被注册";
            if (dbUser.getPhone() != null && dbUser.getPhone().length() == 11) {
                sb.append((dbUser.getPhone()).substring(0, 3));
                sb.append("****");
                sb.append((dbUser.getPhone()).substring(7, 11));
                msg = "该证件号已被手机号" + sb.toString() + "注册";
            }
            throw new MyException(msg, 0);
        }
    }

    public User register(String loginName, String number, String identity, String loginFlag,
                         String identityType, HttpServletRequest request) throws Exception {
        //验证身份证号不为空
        if (identity == null || "".equals(identity)) {
            throw new MyException("证件号不能为空！", 0);
        }
        //验证用户名不为空
        if (loginName == null || "".equals(loginName)) {
            throw new MyException("姓名不能为空！", 0);
        }

        User user = new User();
        //解密相关信息
        user.setLoginName(loginName);
        user.setPhone(number);
        user.setIdentityNumber(identity);
        user.setIdentityType(identityType);

        //验证身份证号是否已存在，不存在插入
        User dbUser = userMapper.identityIsExist(user.getIdentityNumber());
        if (dbUser == null) {
            //生成默认密码
            String password = UUID.randomUUID().toString();
            user.setPassword(EncryptUtil.entryptPassword(password));
            //设置登录类型，android/ios
            if ("android".equals(loginFlag) || "ios".equals(loginFlag)) {
                user.setLoginFlag(loginFlag);
            }
            user.setLoginCount(1);
            user.setLoginDate(new Date());
            user.setLoginIp(HttpUtil.getClientIp(request));
            user.preInsert();
            //插入记录
            userMapper.insert(user);
            return user;
        } else {
            //用户信息提示
            StringBuffer sb = new StringBuffer();
            String msg = "该证件号已被注册";
            if (dbUser.getPhone() != null && dbUser.getPhone().length() == 11) {
                sb.append((dbUser.getPhone()).substring(0, 3));
                sb.append("****");
                sb.append((dbUser.getPhone()).substring(7, 11));
                msg = "该证件号已被手机号" + sb.toString() + "注册。";
            }
            throw new MyException(msg, 0);
        }
    }

    //    修改证件信息
    public void modifyInfo(String token, String identity, String identityType) {
        //验证参数是否为空
        if (token == null || "".equals(token)
                || identity == null || "".equals(identity)) {
            throw new MyException("参数不能为空！", 0);
        }

        User dbUser = new User();
        dbUser.setId(token);
        dbUser.setIdentityNumber(identity);
        dbUser.setIdentityType(identityType);
        dbUser.setUpdateDate(new Date());
        userMapper.update(dbUser);

        //验证该身份证号是否已经存在
        //验证身份证号是否已存在，不存则可更改
//        User user = userMapper.selectByIdentity(identity);

        /*if (user == null || "".equals(user)) {

        } else {
            throw new MyException("该证件号已被绑定！", 0);
        }*/
    }

    //    修改用户信息
    public void modifyInfo(String info) throws ParseException {
        //加密参数解析及获取
        info = AES.decrypt(info, aseKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String token = jsonObject.getString("token");
        String identity = jsonObject.getString("identity");
        String identityType = jsonObject.getString("identityType");
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        String loginName = jsonObject.getString("loginName");

        //验证参数是否为空
        if (token == null || "".equals(token)) {
            throw new MyException("token不能为空！", 0);
        }
        //判断手机号是否为空，如果不为空，验证验证码
        if (phone != null && !"".equals(phone.trim())) {
            //获取验证码
            String sysCode = redisUtil.get(phone);
            // 验证验证码是否过期
            if (StringUtils.isEmpty(sysCode)) {
                throw new MyException("验证码已失效！请重新获取", 0);
            }
            //判断验证码是否正确
            if (!sysCode.equals(code)) {
                throw new MyException("验证码错误！", 0);
            }
            //日期处理
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            /*手机号修改次数限制逻辑*/
            //获取该用户手机号修改次数限制
            String values = redisUtil.hget("phoneModifyList", token);
            if (values != null) {
                String[] str = values.split("\\|");
                String time = str[0];
                int numbers = Integer.parseInt(str[1]);

                String currentStr = sdf.format(new Date());
                Date oldDate = sdf.parse(time);
                Date currentDate = sdf.parse(currentStr);
                //验证修改限制日期是否已过期
                int days = DateUtils.differentDays(oldDate, currentDate);
                if (days > 0) {
                    //日期已过，移除该条缓存记录信息
                    redisUtil.hdel("phoneModifyList", token);
                    //重新初始化限制值
                    String value = sdf.format(new Date()) + "|1";
                    redisUtil.hset("phoneModifyList", token, value);
                } else {
                    //日期未过期，验证修改次数是否超出限制。
                    if (numbers >= 3) {
                        throw new MyException("手机号修改次数已达上限，请改日再试！", 0);
                    } else {
                        numbers++;
                        String tempStr = time + "|" + numbers;
                        redisUtil.hset("phoneModifyList", token, tempStr);
                    }
                }
            } else {
                String value = sdf.format(new Date()) + "|1";
                //初始化手机限制信息
                redisUtil.hset("phoneModifyList", token, value);
            }
        }

        User dbUser = new User();
        dbUser.setId(token);
        dbUser.setIdentityNumber(identity);
        dbUser.setIdentityType(identityType);
        dbUser.setUpdateDate(new Date());
        dbUser.setPhone(phone);
        dbUser.setLoginName(loginName);
        userMapper.update(dbUser);


    }

    //    删除证件信息
    public void dropIdentity(String token) {
        if (token == null || "".equals(token)) {
            throw new MyException("参数不能为空", 0);
        }
        userMapper.dropInfo(token);
    }

    private User getUser(HttpServletRequest request) throws Exception {
        //获取user对象
        String userId = CookieUtil.getCookie(request, "userId");
        //判断userId是否存在
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        //从redis中获取user对象
        String userStr = redisUtil.get(userId);
        Map userMap = JSON.parseObject(userStr);
        return (User) TypeTransformUtil.mapToObject(userMap, User.class);
    }

    //获取所有用户数据
    public List<User> allUser() {
        return userMapper.selectAll();
    }

    //获取该号码所包含的用户列表
    public List<User> userList(String phone, String code) {
        //获取验证码
        String sysCode = redisUtil.get(phone);

        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(code)) {
            throw new MyException("验证码错误！", 0);
        }
        //获取本系统该号码的用户信息
        List<User> users = userMapper.selectUserByPhone(phone);
//        List<User> users = selectPhoneRedisOrDB(phone);

        if (users == null || users.size() == 0) {
            //集合为空，调用德信接口获取用户信息
            String param = "phoneNum=" + phone;
            String result = HttpUtil.sendGet(dexinLoginUrl, param, "utf-8");
            result = AES.decrypt(result, epidemicKey);

            if (StringUtils.isNotBlank(result)) {
                JSONArray userArr = JSONArray.parseArray(result);
                if (userArr != null && userArr.size() > 0) {
                    User saveUser = new User();
                    for (int i = 0; i < userArr.size(); i++) {
                        JSONObject user = userArr.getJSONObject(i);
                        System.out.println(user.toString());
                        //判断用户名不为空
                        if (user.getString("USER_NAME") != null) {
                            //用户姓名
                            saveUser.setLoginName(user.getString("USER_NAME"));
                            //用户手机号
                            saveUser.setPhone(user.getString("PHONE_NUM"));
                            //身份证号
                            saveUser.setIdentityNumber(user.getString("TYSHXYDM"));
                            saveUser.preInsert();
                            //保存用户信息
                            userMapper.insert(saveUser);
                            //将用户信息添加到集合中
                            users.add(saveUser);
                        }
                    }
                }
            }
        }
        return users;
    }

    //通过userId获取用户信息
    public User getUser(String userId) {
        return userMapper.selectById(userId);
    }

    //判断是用户数据是写缓存还是直接写数据库
    private void saveRedisOrDB(User user) {
        //写入数据库与写入redis状态切换
        String filePath = "application.properties";
        String status = PropertiesUtil.getKey(filePath, "user.save.open");
        if (status.equals("redis")) {
            //设置身份证号集合
            redisUtil.hset("userIdentityList", user.getIdentityNumber(), JSONObject.toJSONString(user));
            //设置id集合
            redisUtil.hset("userIdList", user.getId(), JSONObject.toJSONString(user));
            //设置手机号集合
            String baseStr = redisUtil.hget("userPhoneList", user.getPhone());
            if (baseStr == null) {
                //集合中没有该手机用户，直接设置
                redisUtil.hset("userPhoneList", user.getPhone(), JSONObject.toJSONString(user));
            } else {
                /*已存在该手机用户，和已有手机用户信息合并*/
                //字符串对象处理
                User baseUser = JSONObject.parseObject(baseStr, User.class);
                baseUser.setIdentityNumber(baseUser.getIdentityNumber() + "|" + user.getIdentityNumber());
                baseUser.setLoginName(baseUser.getLoginName() + "|" + user.getLoginName());
                baseUser.setId(baseUser.getId() + "|" + user.getId());
                redisUtil.hset("userPhoneList", user.getPhone(), JSONObject.toJSONString(baseUser));
            }
        } else {
            userMapper.insert(user);
        }
    }

    //更新操作，更新redis或直接更新数据库
    private void updateRedisOrDB(User user) {
        //写入数据库与写入redis状态切换
        String filePath = "application.properties";
        String status = PropertiesUtil.getKey(filePath, "user.save.open");
        if (status.equals("redis")) {
            //获取id用户集合
            String idStr = redisUtil.hget("userIdList", user.getId());
            User idUser = JSONObject.parseObject(idStr, User.class);
            //获取手机号用户集合
            String phoneStr = redisUtil.hget("userPhoneList", idUser.getPhone());
            User phoneUser = JSONObject.parseObject(phoneStr, User.class);

            //遍历集合，获取当前更新用户的位置
            int site = 0;
            String ids[] = phoneUser.getId().split("\\|");
            for (int i = 0; i < ids.length; i++) {
                if (ids[i].equals(user.getId())) {
                    site = i;
                }
            }
            String identitys[] = phoneUser.getIdentityNumber().split("\\|");
            String names[] = phoneUser.getLoginName().split("\\|");
            identitys[site] = user.getIdentityNumber();
            names[site] = user.getLoginName();

            StringBuffer identitySB = new StringBuffer();
            StringBuffer nameSB = new StringBuffer();
            //遍历集合，将更新数据重新封装
            for (int i = 0; i < ids.length; i++) {
                identitySB.append(identitys[i]);
                identitySB.append("|");

                nameSB.append(names[i]);
                nameSB.append("|");
            }
            identitySB.deleteCharAt(identitySB.lastIndexOf("|"));
            nameSB.deleteCharAt(nameSB.lastIndexOf("|"));

            phoneUser.setLoginName(nameSB.toString());
            phoneUser.setIdentityNumber(identitySB.toString());

            if (!phoneUser.getPhone().equals(user.getPhone())) {
                //清除旧key，
                redisUtil.hdel("userPhoneList", phoneUser.getPhone());
                phoneUser.setPhone(user.getPhone());
            }
            //设置新key
            redisUtil.hset("userPhoneList", phoneUser.getPhone(), phoneUser.toString());
            BeanUtils.copyProperties(user, idUser);
            redisUtil.hset("userIdList", idUser.getId(), JSONObject.toJSONString(idUser));
            redisUtil.hset("userIdentityList", idUser.getIdentityNumber(), JSONObject.toJSONString(idUser));
        } else {
            userMapper.update(user);
        }
    }

    //判断是查缓存还是直接写数据库
    private List<User> selectPhoneRedisOrDB(String phone) {
        List<User> users = new ArrayList<>();
        //写入数据库与写入redis状态切换
        String filePath = "application.properties";
        String status = PropertiesUtil.getKey(filePath, "user.save.open");
        if (status.equals("redis")) {
            //获取值
            String baseStr = redisUtil.hget("userPhoneList", phone);
            //字符串对象处理
            User baseUser = JSONObject.parseObject(baseStr, User.class);
            if (baseStr != null) {
                String identityList[] = baseUser.getIdentityNumber().split("\\|");
                String loginNameList[] = baseUser.getLoginName().split("\\|");
                String userIdList[] = baseUser.getId().split("\\|");
                for (int i = 0; i < identityList.length; i++) {
                    User user = new User();
                    user.setId(userIdList[i]);
                    user.setLoginName(loginNameList[i]);
                    user.setIdentityNumber(identityList[i]);
                    user.setPhone(baseUser.getPhone());
                    users.add(user);
                }
            }
        } else {
            users = userMapper.selectUserByPhone(phone);
        }
        return users;
    }

    //判断是查缓存还是直接写数据库-通过id查询
    private User selectIdRedisOrDB(String userId) {
        //读取查询或写入状态
        String filePath = "application.properties";
        String status = PropertiesUtil.getKey(filePath, "user.save.open");
        if (status.equals("redis")) {
            //获取值
            String baseStr = redisUtil.hget("userIdList", userId);
            //字符串对象处理
            return JSONObject.parseObject(baseStr, User.class);
        } else {
            return userMapper.selectById(userId);
        }
    }

    //判断是查缓存还是直接写数据库-通过身份证号查询
    private User identityIsExistRedisOrDB(String identityNumber) {
        //读取查询或写入状态
        String filePath = "application.properties";
        String status = PropertiesUtil.getKey(filePath, "user.save.open");
        if (status.equals("redis")) {
            //获取值
            String baseStr = redisUtil.hget("userIdentityList", identityNumber);
            if (baseStr == null) {
                return null;
            }
            return JSONObject.parseObject(baseStr, User.class);
        } else {
            return userMapper.identityIsExist(identityNumber);

        }
    }

     public User selectByFactor( String phone,String identity, String loginName){
        return  userMapper.selectByFactor(phone, identity, loginName);
     }

}