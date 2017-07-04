package cn.smlcx.template.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.smlcx.template.R;
import cn.smlcx.template.bean.NotePad;
import cn.smlcx.template.utils.StringUtil;

/**
 * Created by lcx on 2017/6/8.
 */

public class NewsListAdapter extends BaseQuickAdapter<NotePad,BaseViewHolder> {
	public NewsListAdapter(@Nullable List data) {
		super(R.layout.item_notepad, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, NotePad item) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm-dd hh:MM");
		helper.setText(R.id.tv_title,item.getNpTitle())
		.setText(R.id.tv_content,item.getNpContent())
		.setText(R.id.tv_updatedate, sdf.format(StringUtil.longToDate(item.getNpUpdatedate())));
	}
}
