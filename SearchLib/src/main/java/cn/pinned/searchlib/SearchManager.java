package cn.pinned.searchlib;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

import cn.pinned.searchlib.tools.DebugLog;

/**
 * Created by root on 11/27/14.
 */
public class SearchManager {

    // 需要查找的数据
    private Set<String> mDatas;
    private SearchTask mSearchTask;
    private Set<String> mSearchedList;
    private PinyinManager mPinyinManager;

    //数据查找回调
    private SearchListener mListener = null;

    public SearchManager(Set<String> datas) {
        mDatas = new HashSet<String>();
        mDatas.addAll(datas);
        mSearchedList = new HashSet<String>();
        mSearchedList.addAll(mDatas);
        mPinyinManager = new PinyinManager();
    }

    public void setOnSearchListener(SearchListener listener) {
        this.mListener = listener;
    }

    public void doSearch(String key) {
        // TODO 启动线程进行查找操作
        mSearchTask = new SearchTask();
        mSearchTask.execute(key);
    }

    private class SearchTask extends AsyncTask<String, Set<String>, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            String searchKey = strings[0];
            //  进行查找比对
            Set<String> notMatchList = new HashSet<String>();
            Set<String> matchList = new HashSet<String>();
            for(String item : mSearchedList) {
                if (match(searchKey, item)) {
                    matchList.add(item);
                } else {
                    notMatchList.add(item);
                }
            }
            mSearchedList.removeAll(notMatchList);
            publishProgress(matchList);
            return null;
        }

        private boolean match(String searchKey, String item) {
            if (TextUtils.isEmpty(item)) {
                return false;
            } // end if

            if (TextUtils.isEmpty(searchKey)) {
                return false;
            } // end if
            if (doSimpleMatch(item, searchKey)) {
                return true;
            } // end if
            Set<String> pinyins = mPinyinManager.getPinYin(item);
            if (pinyins == null || pinyins.size() <= 0) {
                return false;
            }
            for(String pinyin : pinyins) {
                if (doSimpleMatch(pinyin, searchKey)) {
                    return true;
                }
            }
            return false;
        }

        private boolean doSimpleMatch(String word, String key) {

            int wordIndex = 0;
            int keyIndex = 0;
            char [] wordChars = word.toCharArray();
            char [] keyChars = key.toCharArray();
            while (wordIndex < word.length() && keyIndex < key.length()) {
                if (wordChars[wordIndex] == keyChars[keyIndex]) {
                    wordIndex ++;
                    keyIndex ++;
                } else {
                    wordIndex ++;
                }
            }
            return keyIndex == key.length();
        }

        @Override
        protected void onProgressUpdate(Set<String>... values) {
            if (mListener == null ) {
                return;
            } // end if
            for (Set<String> value : values) {
                mListener.onSearch(value);
            }
        }

    }

    public interface SearchListener {
        void onSearch(Set<String> values);
    }


}
