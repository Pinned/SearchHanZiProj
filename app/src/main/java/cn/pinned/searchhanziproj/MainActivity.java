package cn.pinned.searchhanziproj;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cn.pinned.searchlib.SearchManager;
import cn.pinned.searchlib.tools.DebugLog;


public class MainActivity extends Activity implements SearchManager.SearchListener{

    private Set<String> mCitys;

    private EditText mInput;
    private TextView mShow;
    private SearchManager mSearchManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initData();
        this.initView();
        this.initListener();
    }

    private void initListener() {
        this.mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = mInput.getText().toString();
                DebugLog.d("key:" + key);
                mSearchManager.doSearch(key);
            }
        });
    }

    private void initView() {
        this.setContentView(R.layout.activity_main);
        this.mInput = (EditText) this.findViewById(R.id.input);
        this.mShow = (TextView) this.findViewById(R.id.show);

        this.mSearchManager = new SearchManager(mCitys);
        this.mSearchManager.setOnSearchListener(this);
    }

    private void initData() {
        this.mCitys = new HashSet<String>();
        String cityStr = getString(R.string.citys);
        this.mCitys.addAll(Arrays.asList(cityStr.split("、")));
    }

    @Override
    public void onSearch(Set<String> values) {
        StringBuffer sb = new StringBuffer();
        for (String value : values) {
            sb.append(value);
            sb.append("\n");
        }
        this.mShow.setText(sb.toString());
    }
}
