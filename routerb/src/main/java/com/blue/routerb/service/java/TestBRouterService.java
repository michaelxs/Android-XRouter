package com.blue.routerb.service.java;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.blue.xrouter.XRouterCallBack;
import com.blue.xrouter.annotation.Router;

/**
 * Created by blue on 2018/10/12.
 */

public class TestBRouterService {

    @Router("toast_java")
    public static void toast(Context context, Bundle data, XRouterCallBack callBack) {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show();
    }

    @Router("getSum_java")
    public static void getSum(Context context, Bundle data, XRouterCallBack callBack) {
        int a = data.getInt("a");
        int b = data.getInt("b");
        int result = a + b;

        data.putInt("result", result);
        callBack.onRouterSuccess(context, data);
    }

    @Router("getFragment_java")
    public static void getFragment(Context context, Bundle data, XRouterCallBack callBack) {
        Fragment fragment = new Fragment();
        callBack.onRouterSuccess(context, data, fragment);
    }
}
