package com.muhan.smart.controller;

import com.muhan.smart.consts.SmartConst;
import com.muhan.smart.form.UserLoginForm;
import com.muhan.smart.form.UserRegisterForm;
import com.muhan.smart.pojo.User;
import com.muhan.smart.service.IUserService;
import com.muhan.smart.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author: Muhan.Zhou
 * @Description 用户Controller
 * @Date 2022/1/28 15:07
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册
     * @param userForm
     * @return
     */
    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm){
        //表单验证，username是否为空
        //统一异常处理
        /*if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
           }*/

        //进行注册功能
        //拷贝对象
        User user = new User();

        BeanUtils.copyProperties(userForm,user);
        return userService.register(user);
    }

    /**
     * 登录
     * @param userLoginForm
     * @param session
     * @return
     */
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  HttpSession session){
        //表单验证，username是否为空
        //已经做了统一异常处理，可以不用判断
        /*if (bindingResult.hasErrors()) {
            return ResponseVo.error(ResponseEnum.PARAM_ERROR, bindingResult);
        }*/
        //登录成功
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());

        //session设置
        session.setAttribute(SmartConst.CURRENT_USER, userResponseVo.getData());

        //返回json数据，格式为状态码+data对象
        return userResponseVo;
    }

    /**
     * 获取用户登录信息
     * @param session
     * @return
     */
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session ){
        //通过携带sessionId判断是否登录
       User user =  (User) session.getAttribute(SmartConst.CURRENT_USER);
        /*
       //判断是否登录
        if (user == null){
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }*/

        //返回json
        return ResponseVo.success(user);
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session){
        //通过sessionID获取
        /*User user =  (User) session.getAttribute(SmartConst.CURRENT_USER);
        //判断是否登录
        if (user == null){
            return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }*/
        session.removeAttribute(SmartConst.CURRENT_USER);
        return ResponseVo.success();

    }
}
