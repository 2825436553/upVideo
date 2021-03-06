package com.pikachu.upvideo.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pikachu.upvideo.R;
import com.pikachu.upvideo.activity.camera.CameraActivity;
import com.pikachu.upvideo.activity.list.ListActivity;
import com.pikachu.upvideo.cls.CameraStartData;
import com.pikachu.upvideo.cls.VideoUpJson;
import com.pikachu.upvideo.util.AppInfo;
import com.pikachu.upvideo.util.tools.ToolAddProjects;
import com.pikachu.upvideo.util.tools.ToolOther;

import java.util.List;

public class ProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        RecyclerAdapter.OnClickAndLongClickListener {


    private IndexActivity indexActivity;
    private View inflate;
    private SwipeRefreshLayout proSw;
    private RecyclerView proRe;
    private FragmentActivity activity;
    private List<VideoUpJson> videoUpJsons;
    private RecyclerAdapter recyclerAdapter;
    private ToolAddProjects addProject;


   /* private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;*/



    public static ProjectFragment newInstance(IndexActivity indexActivity ,
                                              List<VideoUpJson> videoUpJsons) {
        /* Bundle args = new Bundle();
        args.put(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        */
        return new ProjectFragment(indexActivity ,  videoUpJsons);
    }

    public ProjectFragment(){

    }


    public ProjectFragment( IndexActivity indexActivity , List<VideoUpJson> videoUpJsons) {
        this.indexActivity = indexActivity;
        this.videoUpJsons = videoUpJsons;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_project, container, false);
        activity = getActivity();
        initView();
        init();
        return inflate;
    }

    private void init() {
        addProject = ToolAddProjects.getAddProject(activity);
        //读取显示项目
        proRe.setLayoutManager(new LinearLayoutManager(activity));
        recyclerAdapter = new RecyclerAdapter(videoUpJsons, this,addProject);
        proRe.setAdapter(recyclerAdapter);
        proSw.setOnRefreshListener(this);

    }

    private void initView() {
        proSw =  inflate.findViewById(R.id.pro_sw);
        proRe =  inflate.findViewById(R.id.pro_re);
    }



    //刷新监听
    @Override
    public void onRefresh() {
        refresh();
    }


    //刷新数据
    public void  refresh(){
        videoUpJsons = indexActivity.reFragment();
        recyclerAdapter.reData(this.videoUpJsons);
        proSw.setRefreshing(false);
    }

    //刷新数据
    public void  refresh(List<VideoUpJson> videoUpJsons){
        this.videoUpJsons = videoUpJsons;
        recyclerAdapter.reData(this.videoUpJsons);
    }



    //列表点击事件
    @Override
    public void onClick(View view,VideoUpJson videoUpJson, int position) {

        Intent intent = new Intent();

        if ( videoUpJson.getListSon().size() <= 0){
            addProject.addSonProject(videoUpJson, (videoUpJson1, sonProject, sonPath) -> {


                intent.setClass(activity,CameraActivity.class);
                intent.putExtra(AppInfo.START_ACTIVITY_KEY_1, videoUpJson1);
                intent.putExtra(AppInfo.START_ACTIVITY_KEY_2,
                        new CameraStartData(1,true, sonPath, videoUpJson1, sonProject, null));
                startActivity(intent);
            });
        }else {
            intent.setClass(activity,ListActivity.class);
            intent.putExtra(AppInfo.START_ACTIVITY_KEY_1, videoUpJson);
            startActivity(intent);
        }



    }



    /**
     * 删除已选项目
     */
    public void deleteSelectedProject() {
        recyclerAdapter.deleteSelectedProject();
        videoUpJsons = indexActivity.reFragment();
        recyclerAdapter.reData(this.videoUpJsons);
    }


    //列表长按事件
    @Override
    public boolean onLongClick(View view,VideoUpJson videoUpJson, int position) {
        addProject.deleteProject(videoUpJson.getProjectName());
        refresh(); //刷新数据
        ToolOther.tw(activity,"删除成功");
        return true;
    }


    public RecyclerAdapter getRecyclerAdapter() {
        return recyclerAdapter;
    }
}