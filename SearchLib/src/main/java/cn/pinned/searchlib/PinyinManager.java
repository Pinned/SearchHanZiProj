package cn.pinned.searchlib;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.pinned.searchlib.tools.DebugLog;

/**
 * Created by knero on 11/26/2014.
 */
public class PinyinManager {

    private Map<String, Set<PinyinModel>> translateTab = null;
    private HanyuPinyinOutputFormat outputFormat = null;

    public PinyinManager() {
        translateTab = new HashMap<String, Set<PinyinModel>>();
        outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }


    public Set<PinyinModel> getPinYin(String word) {
        Set<PinyinModel> translate = null;
        synchronized (translateTab) {
            // 查找是否已经有解析过这个字符串成Pinyin，如果有直接返回解析后的结果
            translate = translateTab.get(word);
            if (translate != null) {
                return translate;
            } // end if
            translate = new HashSet<PinyinModel>();
            for (int i = 0; i < word.length(); i++) {
                translate = mergeWord(translate, translate(word.charAt(i)));
            }
            translateTab.put(word, translate);
            return translate;
        }

    }

    private Set<PinyinModel> translate(char word) {
        Set<PinyinModel> ret = new HashSet<PinyinModel>();
        try {
            String [] pinyins =  PinyinHelper.toHanyuPinyinStringArray(word, outputFormat);
            ret.add(new PinyinModel(String.valueOf(word)));
            if (pinyins != null) {
                for (String pinyin : pinyins) {
                    ret.add(new PinyinModel(pinyin));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    private Set<PinyinModel> mergeWord(Set<PinyinModel> a, Set<PinyinModel> b) {
        Set<PinyinModel> res = new HashSet<PinyinModel>();
        if (a == null || a.size() <= 0) {
            for (PinyinModel pinyin : b) {
                res.add(pinyin);
            }
        } else {
            for (PinyinModel prefix : a) {
                for (PinyinModel pinyin : b) {
                    res.add(new PinyinModel(prefix, pinyin));
                }
            }
        }
        return res;
    }


}
