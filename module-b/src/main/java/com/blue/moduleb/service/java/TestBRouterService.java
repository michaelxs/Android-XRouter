package com.blue.moduleb.service.java;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.blue.xrouter.XRouterCallback;
import com.blue.xrouter.XRouterParams;
import com.blue.xrouter.XRouterResult;
import com.blue.xrouter.annotation.Router;

/**
 * java provides service
 * Created by blue on 2018/10/12.
 */

public class TestBRouterService {

    @Router(value = "toast_java")
    public static XRouterResult toast(Context context, XRouterParams routerParams) {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show();
        return new XRouterResult.Builder().build();
    }

    @Router(value = "getSum_java", async = true)
    public static void getSum(Context context, XRouterParams routerParams, XRouterCallback callback) {
        Fragment fragment = (Fragment) routerParams.getObj();
        // TODO
        int a = routerParams.getData().getInt("a");
        int b = routerParams.getData().getInt("b");
        int result = a + b;

        if (callback != null) {
            callback.onRouterSuccess(new XRouterResult.Builder().data("result", result).build());
        }
    }

    @Router("getFragment_java")
    public static XRouterResult getFragment(Context context, XRouterParams routerParams) {
        Fragment fragment = new Fragment();
        return new XRouterResult.Builder().obj(fragment).build();
    }
}
