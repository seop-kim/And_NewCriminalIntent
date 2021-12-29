package android.seop.newcriminalintent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI(); // UI 업데이트

        return view;
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Crime mCrime;

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);

            // CrimeAdapter 에서 사용할 itemView 의 레이아웃을 전달 받아 해당 레이아웃 안에 있는 리소스를 적용시킨다.
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_check_box);

        }

        //        boolean beforeSolved = mCrime.isSolved();
//                    mCrime.setSolved(isChecked);
//                    Snackbar.make(view, mCrime.getTitle() + "의 범죄 현황이 변경되었습니다.", Snackbar.LENGTH_SHORT).show();
//                    Log.i("CrimeListFragment", mCrime.getTitle() + " is Solved Change // " + beforeSolved + " > " + mCrime.isSolved());
        public void bindCrime(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());

            // DateFormat 을 이용하여 날짜 값을 가독성 좋게 만들어준다.
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            mDateTextView.setText(dateFormat.format(mCrime.getDate()));
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(view, mCrime.getTitle() + "을 선택하였습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            // position 은 몇번째 뷰홀더인지를 알려준다.
            // position 값으로 배열안에 있는 crime 값을 순차적으로 가져온다.
            Crime crime = mCrimes.get(position);
            holder.bindCrime(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    private void updateUI() {
        // updateUI 에서는 crimeLab 객체를 생성한다.
        // CrimeLab 의 get 은 CrimeLab 자체를 반환해준다.
        // CrimeLab 안에는 Crime 배열 데이터가 저장되어있다.
        CrimeLab crimeLab = CrimeLab.get(getActivity());

        // getCrimes 는 배열 데이터를 가져오는 메소드이다.
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }
}
