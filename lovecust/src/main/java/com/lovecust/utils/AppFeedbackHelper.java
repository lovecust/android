package com.lovecust.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.fisher.utils.AppUtil;
import com.lovecust.app.R;
import com.lovecust.network.entities.AppFeedback;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by fisher at 3:17 AM on 12/19/16.
 */

public class AppFeedbackHelper {

	public static final String LOVECUST_ADMIN = AppFeedback.LOVECUST_ADMIN;

	private static int colorName = AppUtil.getColor(R.color.text_feedback_name);
	private static int colorContent = AppUtil.getColor(R.color.text_feedback_content);
	private static int colorSystem = AppUtil.getColor(R.color.text_feedback_system);

	public static SpannableString getSpannableString(AppFeedback feedback) {
		int a = feedback.getNick().length() + 2;
		int b = feedback.getNick().length() + 2 + feedback.getValue().length();
		SpannableString spannableString = new SpannableString(feedback.getNick() + ": " + feedback.getValue() + "\n");
		spannableString.setSpan(new ForegroundColorSpan(colorName), 0, a, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		if (LOVECUST_ADMIN.equals(feedback.getFrom()))
			spannableString.setSpan(new ForegroundColorSpan(colorSystem), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		else
			spannableString.setSpan(new ForegroundColorSpan(colorContent), a, b, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static void renderHistory(TextView textView) {
		List<AppFeedback> feedbacks = DataSupport.findAll(AppFeedback.class);
		if (null == feedbacks)
			return;
		for (int i = 0; i < feedbacks.size(); i++) {
			textView.append(AppFeedbackHelper.getSpannableString(feedbacks.get(i)));
		}
	}

	public static SpannableString getSystemSpannable(String text) {
		SpannableString message = new SpannableString(text + "\n");
		message.setSpan(new ForegroundColorSpan(colorSystem), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return message;
	}
}
