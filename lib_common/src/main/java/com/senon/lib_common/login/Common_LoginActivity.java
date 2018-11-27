package com.senon.lib_common.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.senon.lib_common.AppConfig;
import com.senon.lib_common.ConstantLoginArouter;
import com.senon.lib_common.R;
import com.senon.lib_common.base.BaseActivity;
import com.senon.lib_common.net.bean.BaseResponse;
import com.senon.lib_common.ComUtil;
import com.senon.lib_common.utils.ToastUtil;

/**
 * 所有模块统一登录页面
 */
@Route(path = ConstantLoginArouter.PATH_COMMON_LOGINACTIVITY)
public class Common_LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements
        LoginContract.View {

    @Autowired
    String targetUrl;
    private EditText account_edt,password_edt;
    private RelativeLayout password_rlay;
    private LinearLayout login_llay;


    @Override
    public int getLayoutId() {
        return R.layout.activity_common__login;
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
        password_rlay = findViewById(R.id.password_rlay);
        login_llay = findViewById(R.id.login_llay);

        phoneNumberVerify();
        ComUtil.judgePwdFormat(password_edt, 18, "密码长度最少6位，最多18位");
    }

    public void phoneNumberVerify() {
        account_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 11) {
                    String getphone = account_edt.getText().toString();
                    if (!ComUtil.isNumeric(getphone)) {
                        ToastUtil.initToast("请输入正确的电话号码");
                        return;
                    }
                    getPresenter().getVerifyPhoneIsRsg(
                            ComUtil.getMd5Str(new String[]{"mobile"},
                            new String[]{getphone}),
                            true, true);
                }
            }
        });
    }

    @Override
    public void resultLogin(BaseResponse<Login> data) {
        AppConfig.saveLoginInfo(data);

        //登录成功时  跳转到该模块首页
        ARouter.getInstance().build(targetUrl).navigation();
        finish();
    }

    @Override
    public void resultVerifyPhoneIsRsg(BaseResponse<Login> data) {
        if (data.getStatus() == 1) {
            ToastUtil.initToast("对不起，您还未注册！");
        } else if (data.getStatus() == 2) {
            password_rlay.setVisibility(View.VISIBLE);
            login_llay.setVisibility(View.VISIBLE);
        } else {
            ToastUtil.initToast("对不起，暂不支持三方账号登录！");
        }
    }

    public void loginClick(View view) {
        String phone = account_edt.getText().toString();
        String password = password_edt.getText().toString();
        if (phone.isEmpty()) {
            ToastUtil.initToast("请输入手机号码");
            return;
        }
        if (!ComUtil.isNumeric(phone.trim())) {
            ToastUtil.initToast("请输入正确的电话号码");
            return;
        }
        if (password.isEmpty()) {
            ToastUtil.initToast("请输入密码");
            return;
        }
        if (password.length() < 6) {
            ToastUtil.initToast("密码长度必须大于或等于6位");
            return;
        }
        getPresenter().getLogin(
                ComUtil.getMd5Str(new String[]{"mobile", "password"},
                        new String[]{phone.trim(), password.trim()})
                , true, true);
    }
}
