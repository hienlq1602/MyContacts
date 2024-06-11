package vn.nb.mycontacts.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.adapter.TopAdapter;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.FragmentHomeBinding;
import vn.nb.mycontacts.entity.ReportTypeCount;
import vn.nb.mycontacts.ui.Constant;


public class ReportFragment extends Fragment {
    private FragmentHomeBinding binding;

    private DataManager dataManager;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataManager = new DataManager(requireActivity());

        Calendar calendar = Calendar.getInstance();
        int m = calendar.get(Calendar.MONTH) + 1;
        if (m < 4) {
            type = 1;
            binding.tvReport.setText("Quarter 1 Report 2024");
        } else if (m < 7) {
            type = 2;
            binding.tvReport.setText("Quarter 2 Report 2024");
        } else if (m < 10) {
            type = 3;
            binding.tvReport.setText("Quarter 3 Report 2024");
        } else {
            type = 4;
            binding.tvReport.setText("Quarter 4 Report 2024");
        }

        loadData();

        binding.tvReport.setOnClickListener(v -> showMenu());


    }

    long start;
    long end;
    int type;

    @SuppressLint("SetTextI18n")
    private void loadData() {

        switch (type) {
            case 1: {
                start = Constant.START_1;
                end = Constant.END_1;
                break;
            }
            case 2: {
                start = Constant.START_2;
                end = Constant.END_2;
                break;
            }
            case 3: {
                start = Constant.START_3;
                end = Constant.END_3;
                break;
            }
            default: {
                start = Constant.START_4;
                end = Constant.END_4;
                break;
            }
        }

        Log.d("__log", "loadData: " + start + " " + end);

        int totalsContact = dataManager.totalsContact();
        int totalsContactRang = dataManager.totalsContactAdd(start, end);
        int totalsBookingRang = dataManager.totalsBookingRang(start, end);
        int totalsReport = dataManager.totalsReport(start, end);

        List<ReportTypeCount> reportTypeCounts = dataManager.reportTypeCounts(start, end);


        //List<ContactWithTotalBookings> contactWithTotalBookings = dataManager.getContactWithTotalBookings(start, end);


        binding.tvTotalContact.setText("Total number of contacts: " + totalsContact);
        binding.tvTotalContactAdd.setText("The number of contacts added: " + totalsContactRang);
        binding.tvTotalBooking.setText("The total number of appointments: " + totalsBookingRang);

        TopAdapter topAdapter = new TopAdapter();
        TopAdapter bottomAdapter = new TopAdapter();

        binding.recyclerviewTop.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerviewBottom.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));

        binding.recyclerviewTop.setHasFixedSize(true);
        binding.recyclerviewBottom.setHasFixedSize(true);

        binding.recyclerviewTop.setAdapter(topAdapter);
        binding.recyclerviewBottom.setAdapter(bottomAdapter);


        dataManager.contactWithInteractionCountsDESC(start, end).observe(requireActivity(), topAdapter::setItem);
        dataManager.contactWithInteractionCountsASC(start, end).observe(requireActivity(), bottomAdapter::setItem);
        // nó chỉ hiển thị những contract đã từng thao tác thôi
//        còn những cái chưa từng thì nó k hiện


        List<PieEntry> entries = new ArrayList<>();

        float call = 0f;
        float email = 0f;
        float booking = 0f;

        for (ReportTypeCount reportTypeCount : reportTypeCounts) {
            switch (reportTypeCount.getType()) {
                case 0: {
                    call = ((float) reportTypeCount.getTotalReports() / totalsReport) * 100;
                    break;
                }
                case 1: {
                    email = ((float) reportTypeCount.getTotalReports() / totalsReport) * 100;
                    break;
                }
                case 2: {
                    booking = ((float) reportTypeCount.getTotalReports() / totalsReport) * 100;
                    break;
                }
            }
        }

        if (call > 0) {
            entries.add(new PieEntry(call, "Call"));
        }
        if (email > 0) {
            entries.add(new PieEntry(email, "Email"));
        }

        if (booking > 0) {
            entries.add(new PieEntry(booking, "Booking"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        binding.pieChart.setData(pieData);

        binding.pieChart.getDescription().setText("Interactive reports");
        binding.pieChart.setEntryLabelColor(Color.BLACK);
        binding.pieChart.animateY(500);
        binding.pieChart.invalidate();
    }


    @SuppressLint("SetTextI18n")
    private void showMenu() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireActivity());
        dialog.setContentView(R.layout.layout_bottom_report);
        dialog.setCancelable(true);
        dialog.show();

        View report1 = dialog.findViewById(R.id.report1);
        View report2 = dialog.findViewById(R.id.report2);
        View report3 = dialog.findViewById(R.id.report3);
        View report4 = dialog.findViewById(R.id.report4);

        View btnCancel = dialog.findViewById(R.id.cancel);

        Objects.requireNonNull(report1).setOnClickListener(v -> {
            binding.tvReport.setText("Quarter 1 Report 2024");
            type = 1;
            dialog.dismiss();
            loadData();

        });
        Objects.requireNonNull(report2).setOnClickListener(v -> {
            binding.tvReport.setText("Quarter 2 Report 2024");
            type = 2;
            dialog.dismiss();
            loadData();
        });
        Objects.requireNonNull(report3).setOnClickListener(v -> {
            type = 3;
            binding.tvReport.setText("Quarter 3 Report 2024");
            dialog.dismiss();
            loadData();
        });
        Objects.requireNonNull(report4).setOnClickListener(v -> {
            type = 4;
            binding.tvReport.setText("Quarter 4 Report 2024");
            dialog.dismiss();
            loadData();
        });

        Objects.requireNonNull(btnCancel).setOnClickListener(v -> dialog.dismiss());
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}