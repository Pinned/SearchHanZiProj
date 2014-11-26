package cn.pinned.searchlib;

import android.os.AsyncTask;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 11/27/14.
 */
public class SearchManager{

    // 需要查找的数据
    private Set<String> mDatas;
    private SearchTask mSearchTask;

    //数据查找回调
    private SearchListener mListener = null;

    public SearchManager(Set<String> datas) {
        mDatas = new HashSet<String>();
        mDatas.addAll(datas);
    }

    public void setOnSearchListener(SearchListener listener) {
        this.mListener = listener;
    }

    public void doSearch(String key) {
        // TODO 启动线程进行查找操作
        mSearchTask = new SearchTask();
        mSearchTask.execute(key);
    }

    private class SearchTask extends AsyncTask<String, String, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            String searchKey = strings[0];
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            String word = values[0];
            if (mListener != null) {
                mListener.onSearch(word);
            } // end if
        }
    }

    interface SearchListener {
        void onSearch(String value);
    }



}
