package com.example.carbonmongol.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carbonmongol.R;
import com.example.carbonmongol.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public class EcoAction {
        private String category;
        private String name;
        private double co2PerUnit;

        public EcoAction(String category, String name, double co2PerUnit) {
            this.category = category;
            this.name = name;
            this.co2PerUnit = co2PerUnit;
        }

        public String getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public double getCo2PerUnit() {
            return co2PerUnit;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        Spinner categorySpinner = view.findViewById(R.id.categorySpinner);
        Spinner actionSpinner = view.findViewById(R.id.actionSpinner);
        EditText quantityInput = view.findViewById(R.id.quantityInput);
        Button calculateBtn = view.findViewById(R.id.calculateButton);
        TextView resultText = view.findViewById(R.id.resultText);

        List<String> categories = Arrays.asList(getString(R.string.transportation), getString(R.string.energy), getString(R.string.waste), getString(R.string.consumption));
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

        // Define all actions
        List<EcoAction> actions = new ArrayList<>();
        actions.add(new EcoAction(getString(R.string.transportation), getString(R.string.walked_or_biked), 0.21));
        actions.add(new EcoAction(getString(R.string.transportation), getString(R.string.bus_ride), 0.09));
        actions.add(new EcoAction(getString(R.string.energy), getString(R.string.ac_used_time), 0.3));
        actions.add(new EcoAction(getString(R.string.energy), "Switched to LED", 1.0));
        actions.add(new EcoAction("Waste", "Used reusable bag", 0.1));
        actions.add(new EcoAction("Consumption", "Ate veg meal", 1.0));
        // Add the rest...

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCat = categories.get(position);
                viewModel.setSelectedCategory(selectedCat);

                List<String> actionNames = new ArrayList<>();
                for (EcoAction act : actions) {
                    if (act.getCategory().equals(selectedCat)) {
                        actionNames.add(act.getName());
                    }
                }

                ArrayAdapter<String> actionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, actionNames);
                actionSpinner.setAdapter(actionAdapter);
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        actionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedActionName = (String) parent.getItemAtPosition(position);
                for (EcoAction act : actions) {
                    if (act.getName().equals(selectedActionName)) {
                        viewModel.setSelectedAction(act);
                        break;
                    }
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        calculateBtn.setOnClickListener(v -> {
            String qtyStr = quantityInput.getText().toString();
            if (qtyStr.isEmpty()) return;
            double qty = Double.parseDouble(qtyStr);
            viewModel.setQuantity(qty);

            double ecoPts = viewModel.getEcoPoints();
            resultText.setText("You saved " + (ecoPts / 10) + "kg CO₂ = " + ecoPts + " EcoPoints!");
        });
    }

}