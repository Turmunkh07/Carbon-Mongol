package com.example.carbonmongol.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> selectedCategory = new MutableLiveData<>();
    private final MutableLiveData<HomeFragment.EcoAction> selectedAction = new MutableLiveData<>();
    private final MutableLiveData<Double> quantity = new MutableLiveData<>();

    public LiveData<String> getSelectedCategory() { return selectedCategory; }
    public LiveData<HomeFragment.EcoAction> getSelectedAction() { return selectedAction; }
    public LiveData<Double> getQuantity() { return quantity; }

    public void setSelectedCategory(String category) { selectedCategory.setValue(category); }
    public void setSelectedAction(HomeFragment.EcoAction action) { selectedAction.setValue(action); }
    public void setQuantity(Double qty) { quantity.setValue(qty); }

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public double getEcoPoints() {
        if (selectedAction.getValue() == null || quantity.getValue() == null) return 0;
        double co2Saved = selectedAction.getValue().getCo2PerUnit() * quantity.getValue();
        return co2Saved * 10;
    }

}
