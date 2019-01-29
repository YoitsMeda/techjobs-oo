package org.launchcode.controllers;

import org.launchcode.models.forms.JobForm;
import org.launchcode.models.CoreCompetency;
import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.Location;
import org.launchcode.models.PositionType;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController
{

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
    	Job result = new Job();
    	result = JobData.findById(id);
    	model.addAttribute("job",result);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {


    	if (errors.hasErrors())
    	{
    		/*model.addAttribute(JobForm);*/
            return "new-job";
    	}
    	else
    	{
    		Job job = new Job();

    		Employer employer = jobData.getEmployers().findById(jobForm.getEmployerId());
    		if(jobForm.getEmployers() != null) job.setEmployer(employer);

    		Location location = jobData.getLocations().findById(jobForm.getLocationId());
    		if(jobForm.getLocations() != null) job.setLocation(location);

    		if(jobForm.getPositionTypes() != null)

    		{
	    		PositionType positionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
	    		job.setPositionType(positionType);
    		}
    		CoreCompetency coreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
    		if(jobForm.getCoreCompetencies() != null)job.setCoreCompetency(coreCompetency);

    		if(jobForm.getName() != null) job.setName(jobForm.getName());

    		jobData.add(job);
    		String view = "redirect:/job?id=" + job.getId();
			return view;
    	}
        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

    }
}
