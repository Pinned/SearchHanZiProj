package cn.pinned.searchlib;

import android.os.Debug;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Set;

import cn.pinned.searchlib.tools.DebugLog;

/**
 * Created by knero on 11/26/2014.
 */
public class PinyinManagerTest extends AndroidTestCase {
    public void testGetPinYin() {
        DebugLog.DEBUG = true;
        PinyinManager manager = new PinyinManager();
        Set<String> pinyins = manager.getPinYin("罗昭成");
        if (pinyins == null || pinyins.size() <= 0) {
            DebugLog.d("end");
            return;
        }
        for (String pinyin : pinyins) {
            DebugLog.d(pinyin);
        }
    }
}
