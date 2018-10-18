package com.eraop.vadmin.controller;

import com.eraop.vadmin.entity.SysUser;
import com.eraop.vadmin.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author weiyi
 */
@Controller
public class HomeController {
    @Resource
    private ISysUserService sysUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("user", new SysUser());
        return "login";
    }


    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid SysUser user, boolean rememberMe, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:login";
        }
        String email = user.getEmail();
        if (StringUtils.isBlank(user.getEmail()) || StringUtils.isBlank(user.getPswd())) {
            System.out.println("用户名或密码为空! ");
            redirectAttributes.addFlashAttribute("message", "用户名或密码为空!");
            return "redirect:login";
        }
        //验证
        UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPswd(), rememberMe);
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            System.out.println("对用户[" + email + "]进行登录验证..验证开始");
            currentUser.login(token);
            System.out.println("对用户[" + email + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,未知账户");
            redirectAttributes.addFlashAttribute("message", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,错误的凭证");
            redirectAttributes.addFlashAttribute("message", "密码不正确");
        } catch (LockedAccountException lae) {
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,错误次数大于5次,账户已锁定");
            redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数大于5次,账户已锁定");
        } catch (DisabledAccountException sae) {
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,帐号已经禁止登录");
            redirectAttributes.addFlashAttribute("message", "帐号已经禁止登录");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            System.out.println("对用户[" + email + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            System.out.println("用户[" + email + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            //把当前用户放入session
            Session session = currentUser.getSession();
            SysUser tUser = sysUserService.getUser(email);
            session.setAttribute("currentUser", tUser);
            return "redirect:welcome";
        } else {
            token.clear();
            return "redirect:login";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        //使用权限管理工具进行用户的退出，跳出登录，给出提示信息
        SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");
        return "redirect:login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "errorPermission";
    }
}
