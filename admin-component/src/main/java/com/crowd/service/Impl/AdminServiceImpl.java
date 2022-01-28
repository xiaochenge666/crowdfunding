package com.crowd.service.Impl;

import com.crowd.constant.CrowdConstant;
import com.crowd.dao.AdminMapper;
import com.crowd.entity.Admin;
import com.crowd.exception.AddAdminException;
import com.crowd.exception.LoginFailedException;
import com.crowd.exception.UserHasExistedException;
import com.crowd.service.api.AdminService;
import com.crowd.utils.CrowdUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional(transactionManager = "transactionManager")
public class AdminServiceImpl implements AdminService {

    private Logger logger=LoggerFactory.getLogger(this.getClass());

    @Resource
    AdminMapper adminMapper;

    public void saveAdmin(Admin admin) {
        if (admin!=null&&admin.getUserPswd()!=null){
            //保存用户时需要对密码进行md5加密处理
            String userPswd = admin.getUserPswd();
            admin.setUserPswd(CrowdUtils.md5(userPswd));
        }
        String pwd=CrowdUtils.md5("111");
        admin = new Admin(null,"1999",pwd,"222","223",new Date());



        int code = adminMapper.insert(admin);
    }

    public List<Admin> queryAll() {
        return adminMapper.queryAll();
    }

    public Admin getAdminByLoginAcct(String loginCount, String pwd) {
        // 1.查用户
        logger.info("开始判断用户是否存在！");
        Admin admin=adminMapper.findUserByName(loginCount);
        // 2.判断有无，无抛异常
        logger.info("adminName:"+admin);
        if(admin==null){
            logger.info("用户不存在！");
            throw new LoginFailedException(CrowdConstant.LOGIN_FAIL);
        }
        // 3.取出密码
        String pwdDatabase = admin.getUserPswd();

        //4.表单提交过来的密码进行加密
        String pwdForm = CrowdUtils.md5(pwd);
        //5.两个密码进行对比
        if(pwdDatabase.equals(pwdForm)){
            return admin;
        }
        //6.不一样抛异常，一样返回admin
        throw new LoginFailedException(CrowdConstant.LOGIN_FAIL);
    }

    public PageInfo<Admin> getPageInfo(String keyword, int pageNo, int pageSize) {
        //开启分页插件的功能
        PageHelper.startPage(pageNo,pageSize);
        //查询数据
        List<Admin> adminsList=adminMapper.selectAdminListByKeyWord(keyword);
        //将adminList封装为PageInfo对象
        PageInfo<Admin> pinfo = new PageInfo<Admin>(adminsList);
        return pinfo;
    }

    public int deleteAdmin(int id) {

        int res = adminMapper.deleteUser(id);
        if(res>0){
            return 1;
        }
        //抛异常
        throw new RuntimeException("删除失败！");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addAdmin(Admin admin) {
        //获取用户名是否重复
        if(admin==null){
            //抛异常
            throw new NullPointerException("用户信息空指针");
        }
        Admin res = adminMapper.findUserByName(admin.getLoginAcct());
        if (res!=null){
            //抛异常，该用户已存在
            throw new UserHasExistedException("当前用户名已存在！请重试！");
        }
        //使用MD5对密码进行加密
        String pwd = admin.getUserPswd();
        String pwdMd5 = CrowdUtils.md5(pwd);
        admin.setUserPswd(pwdMd5);
        admin.setCreateTime(new Date());
        //存入数据库中
        int insertRes = adminMapper.insert(admin);
        //返回结果
        if (insertRes<=0){
            //抛异常
            throw new AddAdminException("添加失败");
        }
    }

    public Admin queryAdmin(String logincount) {

        Admin admin=adminMapper.findUserByName(logincount);
        if(admin==null){
            throw new RuntimeException("编辑失败，服务器异常");
        }
        return admin;
    }

    public void updateAdmin(Admin admin) {
        //异常处理
        if(admin==null){
            throw new RuntimeException("编辑失败，服务器异常");
        }
        //更新
        adminMapper.updateAdmin(admin);
    }

}
