package android.seop.newcriminalintent;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateBtn;
    private CheckBox mSolverCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 호스팅 액티비티 레이아웃에 붙일 프래그먼트 view 객체를 가져와 레이아웃에 붙인다.
        View view = inflater.inflate(R.layout.fragment_crime, container, false);

        // 제목 입력 줄
        mTitleField = view.findViewById(R.id.crime_title);
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

        // 데이터 버튼
        mDateBtn = view.findViewById(R.id.crime_date);
        mDateBtn.setText(mCrime.getDate().toString());
        mDateBtn.setEnabled(false);


        // 범죄 해결 여부
        mSolverCheckBox = view.findViewById(R.id.crime_solved);
        mSolverCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);

                String snakMsg = "범죄 해결 여부 : " + isChecked;
                Snackbar.make(view, snakMsg, Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
