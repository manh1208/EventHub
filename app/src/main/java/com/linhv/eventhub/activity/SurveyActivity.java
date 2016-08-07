package com.linhv.eventhub.activity;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linhv.eventhub.R;
import com.linhv.eventhub.adapter.SurveyAdapter;
import com.linhv.eventhub.enumeration.SurveyEnum;
import com.linhv.eventhub.model.SurveyAnswer;
import com.linhv.eventhub.model.SurveyAnswerWithOptions;
import com.linhv.eventhub.model.SurveyQuestion;
import com.linhv.eventhub.model.SurveyQuestionGroup;
import com.linhv.eventhub.model.SurveyQuestionOption;
import com.linhv.eventhub.model.SurveyQuestionSetting;
import com.linhv.eventhub.model.SurveyResultItem;
import com.linhv.eventhub.model.SurveySubmit;
import com.linhv.eventhub.model.response_model.GetSurveyResponseModel;
import com.linhv.eventhub.model.response_model.SubmitSurveyResponseModel;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SurveyActivity extends AppCompatActivity {
    private ViewHolder viewHolder;
    private RestService restService;
    private int activityId;
    private GetSurveyResponseModel.SurveyResponse surveyResponse;
    private List<SurveyQuestionGroup> surveyQuestionGroups;
    private SurveySubmit surveySubmit;
    private SurveyAnswerWithOptions surveyAnswerWithOptions;
    private List<SurveyResultItem> surveyResultItems;
//    private HashMap<Integer,Boolean> hashItem;
    private List<QuestionView> questionViews;
    private SurveyAnswer surveyAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String actName = getIntent().getStringExtra("activityName");
        toolbar.setTitle(actName);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        getData();
    }

    private void getData() {

    }

    private void init() {
        activityId = getIntent().getIntExtra("activityId", -1);
        viewHolder = new ViewHolder();
        restService = new RestService();
//        viewHolder.lvQuestions = (ListView) findViewById(R.id.lv_survey_question);
        viewHolder.ll_survey = (LinearLayout) findViewById(R.id.ll_survey);
        restService.getActivityService().getSurvey(activityId, new Callback<GetSurveyResponseModel>() {
            @Override
            public void success(GetSurveyResponseModel responseModel, Response response) {
                if (responseModel.isSucceed()) {
                    surveyResponse = responseModel.getSurveyResponse();
                    surveyQuestionGroups = surveyResponse.getSurveyQuestionGroups();
                    updateUI();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        surveySubmit = new SurveySubmit();
        surveyAnswerWithOptions = new SurveyAnswerWithOptions();
        surveyAnswer = new SurveyAnswer();
        surveySubmit.setActivityId(activityId);
        surveySubmit.setSurveyAnswerWithOptions(surveyAnswerWithOptions);
        surveyAnswerWithOptions.setSurveyAnswer(surveyAnswer);


        viewHolder.btnSubmit = (Button) findViewById(R.id.btn_survey_submit);
        viewHolder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surveyResultItems = new ArrayList<>();
//                hashItem = new HashMap<Integer, Boolean>();
                boolean flag = true;
                for(QuestionView questionView:questionViews){
                    SurveyQuestion surveyQuestion = questionView.getSurveyQuestion();
                    if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.SINGLECHOICE.getValue()) {
                        RadioGroup radioGroup = (RadioGroup) questionView.getViews().get(0);
                        int radioButtonId = radioGroup.getCheckedRadioButtonId();
                        if (!(radioButtonId<0)){
                            SurveyResultItem surveyResultItem = new SurveyResultItem();
                            surveyResultItem.setSurveyQuestionId(surveyQuestion.getId());
                            RadioButton button = (RadioButton)radioGroup.findViewById(radioButtonId);
                            int optionId = (Integer) button.getTag();
                            surveyResultItem.setSelectedSurveyQuestionOptionId(optionId);
                            if (button.getText().toString().trim().equals("Khác".trim())){
                                EditText editText = (EditText) questionView.getViews().get(1);
                                if (editText.getText().toString().length()>0) {
                                    surveyResultItem.setAnswerContent(editText.getText().toString());
                                }else{
                                    flag=false;
                                }
                            }
                            surveyResultItems.add(surveyResultItem);
//                            hashItem.put(surveyResultItem.getSurveyQuestionId(),true);
                        }else if (surveyQuestion.isRequired()){
                            flag = false;
                        }
                    }else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.MULTIPLECHOICE.getValue()) {
                        boolean flag1 = false;
                        for (int i = 0;i< questionView.getViews().size()-1;i++){
                            CheckBox checkBox = (CheckBox) questionView.getViews().get(i);
                            if (checkBox.isChecked()){
                                SurveyResultItem surveyResultItem = new SurveyResultItem();
                                surveyResultItem.setSurveyQuestionId(surveyQuestion.getId());
                                surveyResultItem.setSelectedSurveyQuestionOptionId((Integer) checkBox.getTag());

                                flag1=true;
                                if (checkBox.getText().toString().trim().equals("Khác".trim())){
                                    EditText editText = (EditText) questionView.getViews().get(questionView.getViews().size()-1);
                                    if (editText.getText().toString().length()>0) {
                                        surveyResultItem.setAnswerContent(editText.getText().toString());
                                    }else{
                                        flag1=false;
                                    }

                                }
                                surveyResultItems.add(surveyResultItem);
//                                hashItem.put(surveyResultItem.getSurveyQuestionId(),true);
                            }

                        }

                        if (surveyQuestion.isRequired() && !flag1){
                            flag=false;
                        }
                    }else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.SCORE.getValue()) {

                        for (View view: questionView.getViews()){
                            RatingBar rating = (RatingBar) view;
                            if (rating.getRating()>0){
                                SurveyResultItem surveyResultItem = new SurveyResultItem();
                                surveyResultItem.setSurveyQuestionId(surveyQuestion.getId());
                                surveyResultItem.setSelectedSurveyQuestionOptionId((Integer) rating.getTag());
                                surveyResultItem.setAnswerContentNumber((int) rating.getRating());
                                surveyResultItems.add(surveyResultItem);

//                                hashItem.put(surveyResultItem.getSurveyQuestionId(),true);
                            }else if (surveyQuestion.isRequired()){
                                flag = false;
                            }
                        }
                    }else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.FREETEXT.getValue()) {
                        EditText edittext = (EditText) questionView.getViews().get(0);
                        if (edittext!=null && edittext.getText().toString().trim().length()>0){
                            SurveyResultItem surveyResultItem = new SurveyResultItem();
                            surveyResultItem.setSurveyQuestionId(surveyQuestion.getId());
                            surveyResultItem.setAnswerContent(edittext.getText().toString());
                            surveyResultItems.add(surveyResultItem);
//                            hashItem.put(surveyResultItem.getSurveyQuestionId(),true);
                        }else if (surveyQuestion.isRequired()){
                            flag = false;
                        }
                    }

                }
                surveyAnswerWithOptions.setSurveyResultItems(surveyResultItems);
                if (flag) {
                    restService.getActivityService().submitSurvey(surveySubmit, new Callback<SubmitSurveyResponseModel>() {
                        @Override
                        public void success(SubmitSurveyResponseModel responseModel, Response response) {
                            if (responseModel.isSucceed()){
                                Toast.makeText(SurveyActivity.this, "Cảm ơn bạn!!", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }else{
                                Toast.makeText(SurveyActivity.this, responseModel.getErrorsString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            DataUtils.getINSTANCE(getApplicationContext()).ConnectionError();
                        }
                    });
                   
                }else{
                    Toast.makeText(SurveyActivity.this, "Xin hãy hoàn thành những thông tin còn thiếu!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        surveyAdapter = new SurveyAdapter(this,R.layout.item_survey_header,new ArrayList<SurveyQuestionGroup>());

    }

    private void updateUI() {

        surveyAnswer.setSurveyId(surveyResponse.getSurvey().getId());

        questionViews = new ArrayList<>();
        Collections.sort(surveyQuestionGroups);
        viewHolder.ll_survey.removeAllViews();
        final View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.item_survey_header, null, false);
        ((TextView) headerView.findViewById(R.id.txt_survey_name)).setText(surveyResponse.getSurvey().getName() + "ManhNv");
        String url = DataUtils.URL + surveyResponse.getSurvey().getImageUrl();
        ImageView ivImage = (ImageView) headerView.findViewById(R.id.iv_survey_image);
        Picasso.with(SurveyActivity.this).load(Uri.parse(url))
                .placeholder(R.drawable.image_default_avatar)
                .error(R.drawable.image_default_avatar)
                .into(ivImage);
        viewHolder.ll_survey.addView(headerView);
        for (SurveyQuestionGroup surveyQuestionGroup : surveyQuestionGroups) {
            SurveyQuestion surveyQuestion = surveyQuestionGroup.getSurveyQuestion();
            View convertView = null;
            QuestionView questionView = new QuestionView();
            List<View> views = new ArrayList<>();
            questionView.setSurveyQuestion(surveyQuestion);
            questionView.setViews(views);
            List<SurveyQuestionOption> surveyQuestionOptions = surveyQuestionGroup.getSurveyQuestionOptions();
            if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.SINGLECHOICE.getValue()) {
                SingleChoiceViewHolder viewHolder;

                if (true) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.item_survey_single_choice, null, false);
                    viewHolder = new SingleChoiceViewHolder(convertView);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (SingleChoiceViewHolder) convertView.getTag();
                }
                views.add(viewHolder.radioGroup);
                views.add(viewHolder.txtAnother);
                viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
                int i = 0;
                for (SurveyQuestionOption option : surveyQuestionOptions
                        ) {
                    i++;
                    final RadioButton radioButton = new RadioButton(this);
                    radioButton.setId(radioButton.getId() + i);
                    radioButton.setText(option.getContent() != null ? option.getContent() : "Khác");
                    radioButton.setTag(option.getId());
                    viewHolder.radioGroup.addView(radioButton);
                    if (option.getContent() == null) {
                        viewHolder.txtAnother.setVisibility(View.VISIBLE);
                    }


                }


            } else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.MULTIPLECHOICE.getValue()) {
                MultipleChoiceViewHolder viewHolder;
                if (true) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.item_survey_multiple_choice, null, false);
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
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setId(viewHolder.llGroupCheckbox.getId() + i);
                    checkBox.setText(option.getContent() != null ? option.getContent() : "Khác");
                    checkBox.setTag(option.getId());
                    viewHolder.llGroupCheckbox.addView(checkBox);
                    if (option.getContent() == null) {
                        viewHolder.txtAnother.setVisibility(View.VISIBLE);
                    }
                    views.add(checkBox);
                }
                views.add(viewHolder.txtAnother);


            } else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.FREETEXT.getValue()) {
                FreeTextViewHolder viewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.item_survey_free_text, null, false);
                    viewHolder = new FreeTextViewHolder(convertView);

                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (FreeTextViewHolder) convertView.getTag();
                }
                viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
                views.add(viewHolder.txtAnswer);
            } else if (surveyQuestion.getSurveyQuestionTypeId() == SurveyEnum.SCORE.getValue()) {
                ScoreViewholder viewholder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.item_survey_score, null, false);
                    viewholder = new ScoreViewholder(convertView);
                    convertView.setTag(viewholder);
                } else {
                    viewholder = (ScoreViewholder) convertView.getTag();
                }
                viewholder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
                int i = 0;
                SurveyQuestionSetting setting = getSetting(surveyQuestion.getSettings());
                for (SurveyQuestionOption option : surveyQuestionOptions
                        ) {
                    i++;
                    View scoreView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                            R.layout.item_suvey_score_option, null, false);
                    TextView textView = (TextView) scoreView.findViewById(R.id.txt_score_name);
                    textView.setText(option.getContent());
                    RatingBar ratingBar = (RatingBar) scoreView.findViewById(R.id.rb_score_rating);
                    ratingBar.setNumStars(setting.getMaxScore());
                    ratingBar.setStepSize(1.0f);
                    ratingBar.setTag(option.getId());
                    views.add(ratingBar);
//                    checkBox.setText(option.getContent() != null ? option.getContent() : "Khác");
                    viewholder.llGroupScore.addView(scoreView);
//                    if (option.getContent() == null) {
//                        viewHolder.txtAnother.setVisibility(View.VISIBLE);
//                    }
                }
            } else {
                LabelViewHolder viewHolder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(this).inflate(R.layout.item_survey_label, null, false);
                    viewHolder = new LabelViewHolder(convertView);

                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (LabelViewHolder) convertView.getTag();
                }
                viewHolder.txtContent.setText(surveyQuestion.getContent() + (surveyQuestion.isRequired() ? "(*)" : ""));
            }
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            relativeLayout.setBackgroundColor(Color.BLACK);

            viewHolder.ll_survey.addView(convertView);
//            viewHolder.ll_survey.addView(relativeLayout);

            questionViews.add(questionView);

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

    private class LabelViewHolder {
        TextView txtContent;

        LabelViewHolder(View convertView) {
            txtContent = (TextView) convertView.findViewById(R.id.txt_label_content);
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

    private class ScoreViewholder {
        TextView txtContent;
        LinearLayout llGroupScore;

        ScoreViewholder(View convertView) {
            txtContent = (TextView) convertView.findViewById(R.id.txt_score_content);
            llGroupScore = (LinearLayout) convertView.findViewById(R.id.ll_group_score);
        }
    }

    private class ViewHolder {
        LinearLayout ll_survey;
        Button btnSubmit;
    }

    private SurveyQuestionSetting getSetting(String json) {
        json = json.replace("\\", "");
        try {
            JSONObject jsonObject = new JSONObject(json);
            Gson gson = new Gson();
            return gson.fromJson(jsonObject.toString(), SurveyQuestionSetting.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    private class QuestionView {
        SurveyQuestion surveyQuestion;
        List<View> views;

        public SurveyQuestion getSurveyQuestion() {
            return surveyQuestion;
        }

        public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
            this.surveyQuestion = surveyQuestion;
        }

        public List<View> getViews() {
            return views;
        }

        public void setViews(List<View> views) {
            this.views = views;
        }
    }
}
