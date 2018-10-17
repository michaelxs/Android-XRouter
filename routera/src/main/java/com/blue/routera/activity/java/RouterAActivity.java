package com.blue.routera.activity.java;

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

import com.blue.routera.R;
import com.blue.routera.databinding.RouteraActivityMainBinding;
import com.blue.xrouter.XRouter;
import com.blue.xrouter.XRouterCallBack;
import com.blue.xrouter.annotation.Router;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by blue on 2018/10/12.
 */
@Router("java.RouterAActivity.com")
public class RouterAActivity extends AppCompatActivity {

    private RouteraActivityMainBinding binding;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.routera_activity_main);

        context = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getListData());
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        XRouter.with(context)
                                .target("www.RouterBActivity.com")
                                .jump();
                        break;
                    case 1:
                        XRouter.with(context)
                                .target("hello.world")
                                .jump();
                        break;
                    case 2:
                        XRouter.with(context)
                                .target("www.RouterBActivity.com?name=tom&age=24")
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
                                .jump(new XRouterCallBack() {
                                    @Override
                                    public void onRouterSuccess(@NotNull Context context, @Nullable Bundle data) {
                                    }

                                    @Override
                                    public void onRouterError(@NotNull Context context) {
                                        Toast.makeText(context, "target activity is not find", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        break;
                    case 5:
                        XRouter.with(context)
                                .target("www.RouterBActivity.com")
                                .requestCode(1001)
                                .jump();
                        break;
                    case 6:
                        XRouter.with(context)
                                .target("toast_java")
                                .call();
                        break;
                    case 7:
                        XRouter.with(context)
                                .target("getSum_java")
                                .data("a", 1)
                                .data("b", 2)
                                .call(new XRouterCallBack() {
                                    @Override
                                    public void onRouterSuccess(@NotNull Context context, @Nullable Bundle data) {
                                        if (data != null) {
                                            binding.tv.setText(String.format("getSum() result is : %s", data.getInt("result")));
                                        }
                                    }
                                });
                        break;
                    case 8:
                        XRouter.with(context)
                                .target("getFragment_java")
                                .call(new XRouterCallBack() {
                                    @Override
                                    public void onRouterSuccess(@NotNull Context context, @Nullable Bundle data) {
                                    }

                                    @Override
                                    public void onRouterSuccess(@NotNull Context context, @Nullable Bundle data, @Nullable Object others) {
                                        if (others != null && others instanceof Fragment) {
                                            Fragment fragment = (Fragment) others;
                                            binding.tv.setText(String.format("getFragment() result is : %s", fragment));
                                        }
                                    }
                                });
                        break;
                }
            }
        });
    }

    private List<String> getListData() {
        List<String> data = new ArrayList<>();
        data.add("route RouterBActivity\nby \"www.RouterBActivity.com\"");
        data.add("route RouterBActivity\nby \"hello.world\"");
        data.add("route RouterBActivity with data in url");
        data.add("route RouterBActivity with data in bundle");
        data.add("route RouterBActivity with callback");
        data.add("route RouterBActivity for result");
        data.add("route method : toast()");
        data.add("route method : getSum()\nwith params and callback");
        data.add("route method : getFragment()\nwith callback");
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
