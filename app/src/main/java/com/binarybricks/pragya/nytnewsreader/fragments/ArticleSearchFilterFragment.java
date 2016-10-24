package com.binarybricks.pragya.nytnewsreader.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.binarybricks.pragya.nytnewsreader.R;
import com.binarybricks.pragya.nytnewsreader.model.ArticleFilterModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link DialogFragment} subclass.
 */

public class ArticleSearchFilterFragment extends DialogFragment {


    public static final String NEWEST = "newest";
    @BindView(R.id.tvBeginDate) TextView tvBeginDate;
    @BindView(R.id.spinnerSortOrder) Spinner spinnerSortOrder;
    @BindView(R.id.cbArts) CheckBox cbArts;
    @BindView(R.id.cbFashion) CheckBox cbFashion;
    @BindView(R.id.cbSports) CheckBox cbSports;

    private OnCompleteListener mListener;

    public static final String ARTICLE_FILTER = "article_filter";
    public static final String YYYY_MM_DD = "yyyyMMdd";
    private static final String MMM_DD_YYYY = "MMM dd,yyyy";
    private SimpleDateFormat simpleDateFormat;
    private GregorianCalendar calendar;
    private ArticleFilterModel articleFilterModel;
    private int startYear;
    private int startMonth;
    private int startDay;

    public ArticleSearchFilterFragment() {
        // Required empty public constructor
    }

    public static interface OnCompleteListener {
        public abstract void onComplete(ArticleFilterModel articleFilterModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.mListener = (OnCompleteListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(getString(R.string.exception_implement_oncomplete_listener));
        }
    }

    public static ArticleSearchFilterFragment newInstance(ArticleFilterModel articleFilterModel) {
        ArticleSearchFilterFragment fragment = new ArticleSearchFilterFragment();
        if (articleFilterModel != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(ARTICLE_FILTER, articleFilterModel);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article_search_filter, container, false);
        ButterKnife.bind(this,view);

        if (getArguments() != null) {
            articleFilterModel = getArguments().getParcelable(ARTICLE_FILTER);
        }

        SimpleDateFormat sdf= new SimpleDateFormat(MMM_DD_YYYY);
        simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
        String currentDate = sdf.format(new Date());
        Calendar c = Calendar.getInstance();
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        startDay = c.get(Calendar.DAY_OF_MONTH);
        calendar = new GregorianCalendar(startYear,startMonth,startDay);
        tvBeginDate.setText(currentDate);

        //Restore the last saved filters
        if (articleFilterModel != null){
            try {
                tvBeginDate.setText(sdf.format(simpleDateFormat.parse(articleFilterModel.getBeginDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(articleFilterModel.getSortOrder().equalsIgnoreCase(NEWEST)){
                spinnerSortOrder.setSelection(1);
            }
            if (articleFilterModel.getNewsDeskValue() != null){
                for (String s : articleFilterModel.getNewsDeskValue()) {
                    if (s.equalsIgnoreCase(getString(R.string.arts))){
                        cbArts.setChecked(true);
                    }else if (s.equalsIgnoreCase(getString(R.string.fashion_amp_style))){
                        cbFashion.setChecked(true);
                    }else if (s.equalsIgnoreCase(getString(R.string.sports))){
                        cbSports.setChecked(true);
                    }
                }
            }
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.tvBeginDate)
    void setBeginDate(View view){


        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //you will get date here
                calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(MMM_DD_YYYY);
                sdf.setCalendar(calendar);
                String formatedDate = sdf.format(calendar.getTime());
                tvBeginDate.setText(formatedDate);
            }
        }, startYear, startMonth, startDay);
        dialog.show();

    }

    @OnClick(R.id.btnSave)
    void saveFilter(View view){
        articleFilterModel = new ArticleFilterModel();
        articleFilterModel.setBeginDate(simpleDateFormat.format(calendar.getTime()));
        articleFilterModel.setSortOrder(spinnerSortOrder.getSelectedItem().toString());
        ArrayList<String> newsDeskList = new ArrayList<>();
        if (cbArts.isChecked()){
            newsDeskList.add(getString(R.string.arts));
        }
        if (cbFashion.isChecked()){
            newsDeskList.add(getString(R.string.fashion_amp_style));
        }
        if (cbSports.isChecked()){
            newsDeskList.add(getString(R.string.sports));
        }
        articleFilterModel.setNewsDeskValue(newsDeskList);

        mListener.onComplete(articleFilterModel);
        dismiss();
    }
}
