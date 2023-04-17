package com.example.criminalintent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 使用自定义的布局xml文件，通过布局管理器创建新的视图View
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        // 通过ID创建一个RecycleView，并且新建一个线性布局管理器
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 更新UI界面
        updateUI();
        return view;
    }

    private void updateUI() {
        //新建一个CrimeLab对象
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        //设置ReCycleView所对应的Adapter，将crimeLab中的存储crime的List复制过去
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTextView;   // 单个View所显示的标题文字
        private final TextView mDateTextView;    // 日期文字
        private final CheckBox mSolvedCheckBox;  // 勾选框
        private Crime mCrime;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
        }

        public void bindCrime(Crime crime) {
            if (crime != null) {
                mCrime = crime;
            }
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(DateFormat.getDateInstance().format(mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), mCrime.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter implements com.example.criminalintent.CrimeAdapter {
        private List<Crime> mCrimes; // 创建一个List，用于存储列表中的Crime类

        public CrimeAdapter(List<Crime> crimes) {    // 初始化List
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            // 从父Activity中加载一个新的布局服务
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            // 使用标准库中的列表xml, 在父类（fragment）中创建一个新的视图View
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            //Log.d("RecyclerView","onBindViewHolder position: "+ position);
            //得到需要刷新的视图的位置，然后进行设置
            Crime crime = mCrimes.get(position);
            ((CrimeHolder) holder).bindCrime(crime);
        }


        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}