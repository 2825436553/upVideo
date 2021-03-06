package com.pikachu.upvideo.activity.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pikachu.upvideo.R;
import com.pikachu.upvideo.activity.up.UpZipVideoActivity;
import com.pikachu.upvideo.cls.VideoUpJson;
import com.pikachu.upvideo.util.AppInfo;
import com.pikachu.upvideo.util.tools.ToolAddProjects;
import com.pikachu.upvideo.util.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends BaseActivity implements BaseActivity.OnPermissionListener,
        NavigationView.OnNavigationItemSelectedListener {

    private Toolbar indexToolbar;
    private NavigationView indexNav;
    private DrawerLayout indexDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private SearchView mSearchView;
    private ToolAddProjects addProject;
    private NullFragment nullFragment;
    private List<VideoUpJson> videoUpJsons;
    private FloatingActionButton indexFloatingActionButton;
    private ProgressBar indexPro;
    private ProjectFragment projectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index, R.id.bar_view/*, R.id.index_view1*/);
        initView();
        init();
    }


    @SuppressLint("WrongConstant")
    private void init() {
        setActionBar();
        /*supportFragmentManager = getSupportFragmentManager();*/
        //权限申请
        sendPermission(this);
        //读取项目
        addProject = ToolAddProjects.getAddProject(this);
        //变换fragment
        reFragment();
        //点击添加项目
        indexFloatingActionButton.setOnClickListener(v ->{
            if (isPermission())
                   addProject.addProject(indexPro,indexFloatingActionButton, projectFragment);
            else
                showToast("权限不足");
        });
    }

    public List<VideoUpJson> reFragment() {
        videoUpJsons = addProject.readProject();
        if (projectFragment == null ) {
            projectFragment = ProjectFragment.newInstance(this , videoUpJsons);
            nullFragment = new NullFragment();
        }
        if (videoUpJsons.size() > 0 ){
            if ( !getSupportFragmentManager().getFragments().contains(projectFragment) ){
                showToast("创建 list");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.index_frame,  projectFragment)
                        .commitAllowingStateLoss();
            }else {
                projectFragment.refresh(videoUpJsons);
            }
            //projectFragment.refresh();//刷新列表数据
        } else {
            if ( !getSupportFragmentManager().getFragments().contains(nullFragment) ){
                showToast("创建 null");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.index_frame, nullFragment)
                        .commitAllowingStateLoss();
            }
        }

        return videoUpJsons;

    }


    private void setActionBar() {
        indexToolbar.setTitle(R.string.app_name);
        setSupportActionBar(indexToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) return;
        supportActionBar.setHomeButtonEnabled(true); //设置返回键可用
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        indexNav.setNavigationItemSelectedListener(this);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, indexDrawer,
                indexToolbar, R.string.app_author, R.string.app_name);
        mDrawerToggle.syncState();
        indexDrawer.addDrawerListener(mDrawerToggle);

    }


    private void initView() {
        indexToolbar = findViewById(R.id.bar_toolbar);
        indexNav = findViewById(R.id.index_nav);
        indexDrawer = findViewById(R.id.index_drawer);
        indexFloatingActionButton = findViewById(R.id.index_floatingActionButton);
        indexPro = findViewById(R.id.index_pro);
    }



    private void startTaskActivity(List<VideoUpJson> list){
        Intent intent = new Intent(this, UpZipVideoActivity.class);
        intent.putExtra(AppInfo.START_ACTIVITY_KEY_1, list.toArray(new VideoUpJson[]{}));
        startActivity(intent);
    }



    //toolbar菜单
    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        mSearchView.setMaxWidth(770);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //这里匹配结果
                showToast(newText);
                Log.i("test_t", newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    //菜单点击事件
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                showToast("同步全部项目");
                startTaskActivity(videoUpJsons);
                break;
            case R.id.item2:
                showToast("全选");
                if (projectFragment == null)
                    return false;
                projectFragment.getRecyclerAdapter()
                        .allSelectedData(true);
                break;
            case R.id.item5:
                showToast("全选不选");
                if (projectFragment == null)
                    return false;
                projectFragment.getRecyclerAdapter()
                        .allSelectedData(false);
                break;
            case R.id.item3:
                showToast("删除已选项目");
                projectFragment.deleteSelectedProject();
                break;
            case R.id.item4:
                showToast("同步已选项目");
                if (projectFragment == null)
                    return false;
                List<VideoUpJson> selectedData = projectFragment.getRecyclerAdapter()
                        .getSelectedData();
                startTaskActivity(selectedData);
                break;
        }
        return false;
    }

    //侧滑点击
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item1:
                showToast("1");
                break;
            case R.id.nav_item2:
                if (isPermission())
                    showToast("2");
                else
                    showToast("权限不足");
                break;
            case R.id.nav_item3:
                showToast("3");
                break;
            case R.id.nav_item4:
                showToast("4");
                break;
        }

        return true;
    }


    @Override
    public void onGranted() {
        //权限授予

    }

    @Override
    public void onDenied() {
        showToast("权限不足");
        //finish();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        reFragment();
    }

}