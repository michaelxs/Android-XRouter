package com.blue.modulea.java;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.blue.modulea.R;
import com.blue.modulea.databinding.ModuleaActivityMainBinding;
import com.blue.xrouter.XRouter;
import com.blue.xrouter.XRouterCallback;
import com.blue.xrouter.XRouterResult;
import com.blue.xrouter.annotation.Router;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blue on 2018/10/12.
 */
@Router("www.JavaModuleActivity.com")
public class JavaModuleActivity extends AppCompatActivity {

    private ModuleaActivityMainBinding binding;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.modulea_activity_main);

        context = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getListData());
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        XRouter.with(context)
                                .target("www.ModuleBActivity.com")
                                .intentFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .jump();
                        break;
                    case 1:
                        XRouter.with(context)
                                .target("hello.world")
                                .transition(android.R.anim.fade_in, android.R.anim.fade_out)
                                .jump();
                        break;
                    case 2:
                        XRouter.with(context)
                                .target("www.ModuleBActivity.com?name=tom&age=24")
                                .jump();
                        break;
                    case 3:
                        XRouter.with(context)
                                .target("hello.world")
                                .data("name", "blue")
                                .data("age", 18)
                                .jump();
                        break;
                    case 4:
                        XRouter.with(context)
                                .target("www.google.com")
                                .jump(new XRouterCallback() {
                                    @Override
                                    public void onRouterSuccess(@NotNull XRouterResult routerResult) {

                                    }

                                    @Override
                                    public void onRouterError(@NotNull XRouterResult routerResult) {
                                        Toast.makeText(context, "target activity is not find", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        break;
                    case 5:
                        XRouter.with(context)
                                .target("www.ModuleBActivity.com")
                                .requestCode(1001)
                                .jump();
                        break;
                    case 6:
                        XRouter.with(context)
                                .target("toast_java")
                                .route();
                        break;
                    case 7:
                        XRouter.with(context)
                                .target("getSum_java")
                                .data("a", 1)
                                .data("b", 2)
                                .obj(new Fragment())
                                .route(new XRouterCallback() {
                                    @Override
                                    public void onRouterSuccess(@NotNull XRouterResult routerResult) {
                                        binding.tv.setText(String.format("getSum() result is : %s", routerResult.getData().getInt("result")));
                                    }
                                });
                        break;
                    case 8:
                        XRouterResult result = XRouter.with(context).target("getFragment_java").route();
                        if (result.getObj() != null && result.getObj() instanceof Fragment) {
                            Fragment fragment = (Fragment) result.getObj();
                            binding.tv.setText(String.format("getFragment() result is : %s", fragment));
                        }
                        break;
                }
            }
        });
    }

    private List<String> getListData() {
        List<String> data = new ArrayList<>();
        data.add("route ModuleBActivity\nby \"www.RouterBActivity.com\"");
        data.add("route ModuleBActivity\nby \"hello.world\" with transition");
        data.add("route ModuleBActivity with data in url");
        data.add("route ModuleBActivity with data in bundle");
        data.add("route ModuleBActivity with callback");
        data.add("route ModuleBActivity for result");
        data.add("route method async : toast()");
        data.add("route method async : getSum()\nwith params and callback");
        data.add("route method sync : getFragment()");
        return data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "finish for result", Toast.LENGTH_SHORT).show();
        }
    }
}
