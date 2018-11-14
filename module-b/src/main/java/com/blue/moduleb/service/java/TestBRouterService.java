package com.blue.moduleb.service.java;


import android.content.Context;
import android.os.Bundle;
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

    @Router(value = "toast_java", async = true)
    public static void toast(Context context, XRouterParams routerParams, XRouterCallback callback) {
        Toast.makeText(context, "toast from other module", Toast.LENGTH_SHORT).show();
    }

    @Router(value = "getSum_java", async = true)
    public static void getSum(Context context, XRouterParams routerParams, XRouterCallback callback) {
        Fragment fragment = (Fragment) routerParams.getObj();
        // TODO
        int a = routerParams.getData().getInt("a");
        int b = routerParams.getData().getInt("b");
        int result = a + b;

        Bundle data = new Bundle();
        data.putInt("result", result);
        if (callback != null) {
            callback.onRouterSuccess(new XRouterResult(data));
        }
    }

    @Router("getFragment_java")
    public static XRouterResult getFragment(Context context, XRouterParams routerParams) {
        Fragment fragment = new Fragment();
        return new XRouterResult(fragment);
    }
}
