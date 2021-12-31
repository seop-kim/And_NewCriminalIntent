package android.seop.newcriminalintent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.util.UUID;

public class CrimeFragment extends Fragment {

    private static final String ARG_CRIME_ID = "crime_id";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateBtn;
    private CheckBox mSolverCheckBox;

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 범죄 상세내역에 들어왔을때 전달된 crimeId가 있는지 확인.
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 호스팅 액티비티 레이아웃에 붙일 프래그먼트 view 객체를 가져와 레이아웃에 붙인다.
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        // 제목 입력 줄
        mTitleField = view.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override // 사용하지 않음
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString()); // Char 형태로 받아온 데이터를 String으로 변환 후 Crime 클래스의 title에 저장한다.
            }

            @Override // 사용하지 않음
            public void afterTextChanged(Editable s) {

            }
        });

        // 데이터 포맷 객체 생성
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        /**
         * SimpleDateFormat Class 를 이용하는 방법도 있다.
         */

        // 데이터 버튼
        mDateBtn = view.findViewById(R.id.crime_date);
        mDateBtn.setText(dateFormat.format(mCrime.getDate())); // 데이터 포맷을 이용하여 날짜 값을 생성한 객체에 맞춰 변환 이때 format은 리턴을 String으로 준다.
        mDateBtn.setEnabled(false);


        // 범죄 해결 여부
        mSolverCheckBox = view.findViewById(R.id.crime_solved);
        mSolverCheckBox.setChecked(mCrime.isSolved());
        mSolverCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);

                String snaCkMsg = "범죄 해결 여부 : " + isChecked;
                Snackbar.make(view, snaCkMsg, Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
