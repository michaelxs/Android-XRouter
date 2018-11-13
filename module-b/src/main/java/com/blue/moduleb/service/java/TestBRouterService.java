package com.blue.moduleb.service.java;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.blue.xrouter.XRouterParams;
import com.blue.xrouter.XRouterResult;
import com.blue.xrouter.annotation.Router;

/**
 * java provides service
 * Created by blue on 2018/10/12.
 */

public class TestBRouterService {

    @Router("toast_java")
    public static void toast(XRouterParams routerParams) {
        Toast.makeText(routerParams.getContext(), "toast from other module", Toast.LENGTH_SHORT).show();
    }

    @Router("getSum_java")
    public static void getSum(XRouterParams routerParams) {
        int a = routerParams.getData().getInt("a");
        int b = routerParams.getData().getInt("b");
        int result = a + b;

        Bundle data = new Bundle();
        data.putInt("result", result);
        if (routerParams.getCallback() != null) {
            routerParams.getCallback().onRouterSuccess(new XRouterResult(data));
        }
    }

    @Router("getFragment_java")
    public static void getFragment(XRouterParams routerParams) {
        Fragment fragment = new Fragment();
        if (routerParams.getCallback() != null) {
            routerParams.getCallback().onRouterSuccess(new XRouterResult(null, fragment));
        }
    }
}
