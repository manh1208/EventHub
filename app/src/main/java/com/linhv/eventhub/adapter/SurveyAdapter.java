package com.linhv.eventhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.linhv.eventhub.R;
import com.linhv.eventhub.enumeration.SurveyEnum;
import com.linhv.eventhub.model.SurveyQuestion;
import com.linhv.eventhub.model.SurveyQuestionGroup;
import com.linhv.eventhub.model.SurveyQuestionOption;

import java.util.List;

/**
 * Created by ManhNV on 8/5/16.
 */
public class SurveyAdapter extends ArrayAdapter<SurveyQuestionGroup> {
    private Context mContext;
    private List<SurveyQuestionGroup> surveyQuestionGroups;

    public SurveyAdapter(Context context, int resource, List<SurveyQuestionGroup> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.surveyQuestionGroups = objects;
    }

    public void setSurveyQuestionGroups(List<SurveyQuestionGroup> surveyQuestionGroups) {
        this.surveyQuestionGroups = surveyQuestionGroups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return surveyQuestionGroups.size();
    }

    @Override
    public SurveyQuestionGroup getItem(int position) {
        return surveyQuestionGroups.get(position);
    }


    @Override
    public int getItemViewType(int position) {
        SurveyQuestionGroup surveyQuestionGroup = getItem(position);
        SurveyQuestion surveyQuestion = surveyQuestionGroup.getSurveyQuestion();
        return surveyQuestion.getSurveyQuestionTypeId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SurveyQuestionGroup surveyQuestionGroup = getItem(position);
        SurveyQuestion surveyQuestion = surveyQuestionGroup.getSurveyQuestion();
        List<SurveyQuestionOption> surveyQuestionOptions = surveyQuestionGroup.getSurveyQuestionOptions();
        if (this.getItemViewType(position)==SurveyEnum.SINGLECHOICE.getValue()) {
            SingleChoiceViewHolder viewHolder;
            if (true) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_survey_single_choice, parent, false);
                viewHolder = new SingleChoiceViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (SingleChoiceViewHolder) convertView.getTag();
            }
            viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
            int i = 0;
            for (SurveyQuestionOption option : surveyQuestionOptions
                    ) {
                i++;
                final RadioButton radioButton = new RadioButton(mContext);
                radioButton.setId(radioButton.getId() + i);
                radioButton.setText(option.getContent() != null ? option.getContent() : "Khác");
                viewHolder.radioGroup.addView(radioButton);
                if (option.getContent() == null) {
                    viewHolder.txtAnother.setVisibility(View.VISIBLE);
                }

            }
            return convertView;
        } else if (this.getItemViewType(position) == SurveyEnum.MULTIPLECHOICE.getValue()) {
            MultipleChoiceViewHolder viewHolder;
            if (true) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_survey_multiple_choice, parent, false);
                viewHolder = new MultipleChoiceViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (MultipleChoiceViewHolder) convertView.getTag();
            }
            viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));

            int i = 0;
            for (SurveyQuestionOption option : surveyQuestionOptions
                    ) {
                i++;
                CheckBox checkBox = new CheckBox(mContext);
                checkBox.setId(viewHolder.llGroupCheckbox.getId() + i);
                checkBox.setText(option.getContent() != null ? option.getContent() : "Khác");
                viewHolder.llGroupCheckbox.addView(checkBox);
                if (option.getContent() == null) {
                    viewHolder.txtAnother.setVisibility(View.VISIBLE);
                }

            }
            return convertView;
        } else {
            final FreeTextViewHolder viewHolder;
            if (true) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_survey_free_text, parent, false);
                viewHolder = new FreeTextViewHolder(convertView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (FreeTextViewHolder) convertView.getTag();
            }
            viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
            return convertView;
        }
    }


    private class FreeTextViewHolder {
        TextView txtContent;
        EditText txtAnswer;

        FreeTextViewHolder(View convertView) {
            txtContent = (TextView) convertView.findViewById(R.id.txt_freetext_content);
            txtAnswer = (EditText) convertView.findViewById(R.id.txt_freetext_answer);
        }
    }

    private class SingleChoiceViewHolder {
        TextView txtContent;
        RadioGroup radioGroup;
        EditText txtAnother;

        SingleChoiceViewHolder(View convertView) {
            txtContent = (TextView) convertView.findViewById(R.id.txt_singlechoice_content);
            radioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group);
            txtAnother = (EditText) convertView.findViewById(R.id.txt_another);
        }
    }

    private class MultipleChoiceViewHolder {
        TextView txtContent;
        LinearLayout llGroupCheckbox;
        EditText txtAnother;

        MultipleChoiceViewHolder(View convertView) {
            txtContent = (TextView) convertView.findViewById(R.id.txt_multiplechoice_content);
            llGroupCheckbox = (LinearLayout) convertView.findViewById(R.id.ll_group_checkbox);
            txtAnother = (EditText) convertView.findViewById(R.id.txt_another);
        }
    }
}
