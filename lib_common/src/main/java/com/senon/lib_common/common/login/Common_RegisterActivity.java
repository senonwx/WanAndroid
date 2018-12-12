package com.senon.lib_common.common.login;

import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.ConstantArouter;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.R;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.net.bean.BaseResponse;
import com.senon.lib_common.utils.ToastUtil;

/**
 * 所有模块统一注册页面
 */
@Route(path = ConstantArouter.PATH_COMMON_REGISTERACTIVITY)
public class Common_RegisterActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements
        LoginContract.View {

    @Autowired
    String targetUrl;
    private EditText account_edt,password_edt,password_re_edt;
    private TextView login_tv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_common__register;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        if (targetUrl == null) {
            //默认跳转到MainActivity
            targetUrl = ConstantLoginArouter.PATH_APP_MAINACTIVITY;
        }
        account_edt = findViewById(R.id.account_edt);
        password_edt = findViewById(R.id.password_edt);
        password_re_edt = findViewById(R.id.password_re_edt);
        login_tv = findViewById(R.id.login_tv);

        login_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void getLoginResult(BaseResponse<Login> data) {
    }

    @Override
    public void getRegisterResult(BaseResponse<Login> data) {
        //登录成功时
        //保存参数


        //跳转到目标页
        ARouter.getInstance().build(targetUrl).navigation();
        finish();
    }

    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.register_btn){
            String account = account_edt.getText().toString();
            String password = password_edt.getText().toString();
            String repassword = password_re_edt.getText().toString();
            if (account.isEmpty()) {
                ToastUtil.initToast("请输入账号");
                return;
            }
            if (password.isEmpty()) {
                ToastUtil.initToast("请输入密码");
                return;
            }
            if (repassword.isEmpty()) {
                ToastUtil.initToast("请输入确认密码");
                return;
            }
            if (!password.equals(repassword)) {
                ToastUtil.initToast("确认密码与密码不同");
                return;
            }
            getPresenter().getRegister(ComUtil.getMd5Str(
                    new String[]{"username", "password", "repassword"},
                    new String[]{account.trim(), password.trim(),repassword.trim()})
                    , true, true);
        }else if(id == R.id.login_tv){
            finish();
        }

    }

}
