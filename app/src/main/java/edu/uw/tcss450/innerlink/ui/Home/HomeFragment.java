package edu.uw.tcss450.innerlink.ui.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.uw.tcss450.innerlink.MainActivity;
import edu.uw.tcss450.innerlink.R;
import edu.uw.tcss450.innerlink.SettingsActivity;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentContactsRequestListBinding;
import edu.uw.tcss450.innerlink.databinding.FragmentHomeBinding;
import edu.uw.tcss450.innerlink.model.UserInfoViewModel;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsRequestRecyclerViewAdapter;
import edu.uw.tcss450.innerlink.ui.Contacts.ContactsViewModel;
import edu.uw.tcss450.innerlink.ui.Forecasts.ForecastCurrentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Represents the Home screen.
 */
public class HomeFragment extends Fragment {
    private ContactsViewModel mContactViewModel;
    private ForecastCurrentViewModel mForecastCurrentViewModel;
    private UserInfoViewModel mUserModel;
    private FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactViewModel = new ViewModelProvider(getActivity()).get(ContactsViewModel.class);
        UserInfoViewModel mUserModel = new ViewModelProvider(getActivity()).get(UserInfoViewModel.class);
        mContactViewModel.setUserInfoViewModel(mUserModel);
        mContactViewModel.getRequests();
        mForecastCurrentViewModel = new ViewModelProvider(getActivity()).get(ForecastCurrentViewModel.class);
         //mForecastCurrentViewModel.getCurrConditions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater);
        if (mContactViewModel.getRequestFrom().size() == 0) {
            binding.textView2.setText("No Incoming Friend Requests");
        } else if (mContactViewModel.getRequestFrom().size() == 1) {
            binding.textView2.setText(mContactViewModel.getRequestFrom().size() + " Incoming Friend Request");
        } else {
            binding.textView2.setText(mContactViewModel.getRequestFrom().size() + " Incoming Friend Requests");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactViewModel.addRequestsListObserver(getViewLifecycleOwner(), contactList -> {
            if (!contactList.isEmpty()) {
                binding.listRootContactsRequests.setAdapter(
                        new ContactsRequestRecyclerViewAdapter(contactList, this.mContactViewModel));
            }
        });
    }
}