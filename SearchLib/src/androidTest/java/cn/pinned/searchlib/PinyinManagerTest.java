package cn.pinned.searchlib;

import android.os.Debug;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.Set;

/**
 * Created by knero on 11/26/2014.
 */
public class PinyinManagerTest extends AndroidTestCase {
    public void testGetPinYin() {
        PinyinManager manager = new PinyinManager();
        Set<String> pinyins = manager.getPinYin("罗昭成");
        for (String pinyin : pinyins) {
            Log.d("Test",pinyin);
        }
    }
}
