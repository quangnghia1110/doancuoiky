package hcmute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hcmute.model.Feedback;
import hcmute.security.UserPrincipal;
import hcmute.service.IFeedbackService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/feedback")
@Slf4j
public class FeedBackController {
	@Autowired
	IFeedbackService feedbackService;
	@PostMapping
	public ModelAndView sendFeedBack(
			ModelAndView mav, 
			@ModelAttribute("feedback") Feedback feedback,
			@RequestParam("laptopId") String laptopId
			) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		feedbackService.saveFeedback(feedback, userPrincipal.getId(), Long.parseLong(laptopId));
		mav.setViewName("redirect:/shop-detail?laptopId=" +laptopId);
		return mav;
	}
	
	@PostMapping("/delete")
	public ModelAndView deleteFeedBack(
			ModelAndView mav, 
			@RequestParam("feedbackId") String feedbackId,
			@RequestParam("laptopId") String laptopId ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal(); 
		Feedback feedback = feedbackService.getById(Long.parseLong(feedbackId));
		boolean hasUserRole = authentication.getAuthorities().stream()
		          .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
		if(userPrincipal.getId()==feedback.getUser().getId() || hasUserRole == true) {
			feedbackService.deleteById(Long.parseLong(feedbackId));
		}
		else {
			throw new AccessDeniedException("Bạn không có quyền truy cập");
		}
		mav.setViewName("redirect:/shop-detail?laptopId=" +laptopId);
		return mav;
	}
}
