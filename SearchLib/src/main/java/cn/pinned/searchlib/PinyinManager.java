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

/**
 * Created by knero on 11/26/2014.
 */
public class PinyinManager {

    private Map<String, Set<String>> translateTab = null;
    private HanyuPinyinOutputFormat outputFormat = null;

    public PinyinManager() {
        translateTab = new HashMap<String, Set<String>>();
        outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }

    public Set<String> getPinYin(String word) {
        Set<String> translate = null;
        synchronized (translateTab) {
            // 查找是否已经有解析过这个字符串成Pinyin，如果有直接返回解析后的结果
            translate = translateTab.get(word);
            if (translate != null) {
                return translate;
            } // end if
            translate = new HashSet<String>();
            for (int i = 0; i < word.length(); i++) {
                translate = mergeWord(translate, translate(word.charAt(i)));
            }
            translateTab.put(word, translate);
        }
        return null;
    }

    private Set<String> translate(char word) {
        Set<String> ret = new HashSet<String>();
        try {
            String [] pinyins =  PinyinHelper.toHanyuPinyinStringArray(word, outputFormat);
            ret.add(String.valueOf(word));
            if (pinyins != null) {
                for (String pinyin : pinyins) {
                    ret.add(pinyin);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
        }
        return ret;
    }

    private Set<String> mergeWord(Set<String> a, Set<String> b) {
        Set<String> res = new HashSet<String>();
        if (a == null) {
            for (String pinyin : b) {
                res.add(pinyin);
            }
        } else {
            for (String prefix : a) {
                for (String pinyin : b) {
                    res.add(prefix + pinyin);
                }
            }
        }
        return res;
    }


}
