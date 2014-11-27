package cn.pinned.searchlib;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by knero on 11/27/2014.
 */
public class PinyinModel {
    private List<String>  mPinyinDatas;
    public PinyinModel() {
        mPinyinDatas = new ArrayList<String>();
    }
    public PinyinModel(String word) {
        this();
        mPinyinDatas.add(word);
    }

    public PinyinModel(PinyinModel prefix, PinyinModel end) {
        this();
        mPinyinDatas.addAll(prefix.getDatas());
        mPinyinDatas.addAll(end.getDatas());
    }

    public List<String> getDatas() {
        return mPinyinDatas;
    }

    public void appendWord(String word) {
        mPinyinDatas.add(word);
    }
    public void getWord(int index) {
        mPinyinDatas.get(index);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
