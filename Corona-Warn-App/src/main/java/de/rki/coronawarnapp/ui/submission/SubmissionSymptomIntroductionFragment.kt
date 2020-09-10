package de.rki.coronawarnapp.ui.submission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.databinding.FragmentSubmissionSymptomIntroBinding
import de.rki.coronawarnapp.ui.doNavigate
import de.rki.coronawarnapp.ui.main.MainActivity
import de.rki.coronawarnapp.ui.viewmodel.SubmissionViewModel
import kotlinx.android.synthetic.main.fragment_submission_symptom_intro.view.*

class SubmissionSymptomIntroductionFragment : Fragment() {

    private var _binding: FragmentSubmissionSymptomIntroBinding? = null
    private val binding: FragmentSubmissionSymptomIntroBinding get() = _binding!!
    private val submissionViewModel: SubmissionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubmissionSymptomIntroBinding.inflate(inflater)
        binding.submissionViewModel = submissionViewModel
        binding.lifecycleOwner = this
        binding.symptomChoiceSelection.verifyState = "verify"
        binding.symptomChoiceSelection.applyState = "apply"
        binding.symptomChoiceSelection.rejectState = "reject"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonOnClickListener()

        submissionViewModel.symptomRouteToScreen.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SymptomIntroductionEvent.NavigateToSymptomCalendar -> navigateToSymptomCalendar()
                is SymptomIntroductionEvent.NavigateToPreviousScreen -> navigateToPreviousScreen()
            }
        })
    }

    private fun navigateToSymptomCalendar() {
        // TODO: Place here the route to the next fragment
    }

    private fun navigateToPreviousScreen() {
        findNavController().doNavigate(
            SubmissionSymptomIntroductionFragmentDirections
                .actionSubmissionSymptomIntroductionFragmentToSubmissionResultFragment()
        )
    }

    private fun setButtonOnClickListener() {

        binding.symptomChoiceSelection.targetButtonVerify.setOnClickListener {
            onClickButtonVerifyHandler()
        }

        binding.symptomChoiceSelection.targetButtonApply.setOnClickListener {
            onClickButtonApplyHandler()
        }

        binding.symptomChoiceSelection.targetButtonReject.setOnClickListener {
            onClickButtonRejectHandler()
        }

        binding
            .submissionSymptomHeader.headerButtonBack.buttonIcon
            .setOnClickListener { submissionViewModel.navigateToPreviousScreen() }

        binding
            .symptomButtonNext
            .setOnClickListener { submissionViewModel.navigateToSymptomCalendar() }

    }

    private fun onChangeCurrentButtonSelected(state: String?) {
        if (submissionViewModel.currentButtonSelected.value.toString() !== state) {
            submissionViewModel.setCurrentButtonSelected(state.toString())
        } else {
            submissionViewModel.setCurrentButtonSelected("")
        }
    }

    private fun onClickButtonVerifyHandler() =
        onChangeCurrentButtonSelected(binding.symptomChoiceSelection.verifyState)

    private fun onClickButtonApplyHandler() =
        onChangeCurrentButtonSelected(binding.symptomChoiceSelection.applyState)

    private fun onClickButtonRejectHandler() =
        onChangeCurrentButtonSelected(binding.symptomChoiceSelection.rejectState)

}
